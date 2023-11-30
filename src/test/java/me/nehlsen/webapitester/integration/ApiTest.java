package me.nehlsen.webapitester.integration;

import me.nehlsen.webapitester.api.ScheduleResponse;
import me.nehlsen.webapitester.api.assertion.AssertionDto;
import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;
import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.api.plan.PlanExecutionRecordDto;
import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.api.plan.PlanDto;
import me.nehlsen.webapitester.api.task.TaskDto;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DataAccess dataAccess;

    @Test
    void create_plan_with_name_and_empty_list_of_tasks_is_saved_in_repository() {
        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity(
                "/plans/",
                new CreatePlanDto("a new test plan", List.of()),
                PlanDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        final String uuid = extractUuidFromLocation(response.getHeaders().getLocation());

        final PlanEntity plan = getPlanFromRepository(uuid);
        assertThat(plan.getName()).isEqualTo("a new test plan");
    }

    @Test
    void create_plan_with_tasks_list_is_null_results_is_bad_request_and_no_plan_is_saved_in_repository() {
        final long sizeBefore = getRepositorySize();

        final HttpEntity<CreatePlanDto> requestBody = new HttpEntity<>(new CreatePlanDto("a new test plan", null));
        final ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
        final ResponseEntity<Map<String, Object>> response = testRestTemplate.exchange(
                "/plans/",
                HttpMethod.POST,
                requestBody,
                responseType
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(getRepositorySize()).isEqualTo(sizeBefore);
    }

    @Test
    void create_plan_with_name_and_tasks_but_no_assertions() {
        final CreateTaskDto taskDto = new CreateTaskDto("void", "empty task", "needs://a-valid.url");
        final CreatePlanDto planDto = new CreatePlanDto("some plan with task", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plans/", planDto, PlanDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        final String uuid = extractUuidFromLocation(response.getHeaders().getLocation());

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity(response.getHeaders().getLocation(), PlanDto.class);
        assertThat(planResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanDto returnedPlanDto = planResponse.getBody();
        assertThat(returnedPlanDto).isNotNull();
        assertThat(returnedPlanDto.getUuid()).isEqualTo(uuid);
        assertThat(returnedPlanDto.getName()).isEqualTo("some plan with task");
        assertThat(returnedPlanDto.getTasks()).isNotNull().hasSize(1);
        final TaskDto firstTaskDto = returnedPlanDto.getTasks().get(0);
        assertThat(firstTaskDto.getUuid()).isNotEmpty();
        assertThat(firstTaskDto.getType()).isEqualTo("void");
        assertThat(firstTaskDto.getName()).isEqualTo("empty task");
        assertThat(firstTaskDto.getUri()).isEqualTo("needs://a-valid.url");
        assertThat(firstTaskDto.getAssertions()).isNotNull().isEmpty();
    }

    @Test
    void create_plan_with_name_and_tasks_including_assertions() {
        final CreateAssertionDto assertionDto = new CreateAssertionDto("response_status_code", Map.of("expectedStatusCode", "200"));
        final CreateTaskDto taskDto = new CreateTaskDto("http_get", "request BadSSL", "https://badssl.com").withAssertions(List.of(assertionDto));
        final CreatePlanDto planDto = new CreatePlanDto("some plan with task and assertion", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plans/", planDto, PlanDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        final String uuid = extractUuidFromLocation(response.getHeaders().getLocation());

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity(response.getHeaders().getLocation(), PlanDto.class);
        assertThat(planResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanDto returnedPlanDto = planResponse.getBody();
        assertThat(returnedPlanDto).isNotNull();
        assertThat(returnedPlanDto.getUuid()).isEqualTo(uuid);
        assertThat(returnedPlanDto.getName()).isEqualTo("some plan with task and assertion");
        assertThat(returnedPlanDto.getTasks()).isNotNull().hasSize(1);
        final TaskDto firstTaskDto = returnedPlanDto.getTasks().get(0);
        assertThat(firstTaskDto.getUuid()).isNotEmpty();
        assertThat(firstTaskDto.getType()).isEqualTo("http_get");
        assertThat(firstTaskDto.getName()).isEqualTo("request BadSSL");
        assertThat(firstTaskDto.getUri()).isEqualTo("https://badssl.com");
        assertThat(firstTaskDto.getAssertions()).isNotNull().hasSize(1);
        final AssertionDto firstAssertionDto = firstTaskDto.getAssertions().get(0);
        assertThat(firstAssertionDto.getUuid()).isNotEmpty();
        assertThat(firstAssertionDto.getType()).isEqualTo("response_status_code");
        assertThat(firstAssertionDto.getParameters()).isNotNull().hasSize(1);
        assertThat(firstAssertionDto.getParameters()).containsEntry("expectedStatusCode", "200");
    }

    @Test
    void create_plan_with_post_task() {
        final CreateTaskDto taskDto = new CreateTaskDto("http_post", "post some data", "https://somedomain.com").withParameters(Map.of("body", "some data to post"));
        final CreatePlanDto planDto = new CreatePlanDto("some plan with post task", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plans/", planDto, PlanDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity(response.getHeaders().getLocation(), PlanDto.class);
        assertThat(planResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanDto returnedPlanDto = planResponse.getBody();
        assertThat(returnedPlanDto).isNotNull();
        assertThat(returnedPlanDto.getTasks()).isNotNull().hasSize(1);
        final TaskDto firstTaskDto = returnedPlanDto.getTasks().get(0);
        assertThat(firstTaskDto.getUuid()).isNotEmpty();
        assertThat(firstTaskDto.getType()).isEqualTo("http_post");
        assertThat(firstTaskDto.getName()).isEqualTo("post some data");
        assertThat(firstTaskDto.getUri()).isEqualTo("https://somedomain.com");
        assertThat(firstTaskDto.getParameters()).isNotNull().hasSize(1).containsEntry("body", "some data to post");
    }

    @Test
    void create_plan_with_schedule() {
        final CreatePlanDto planDto = new CreatePlanDto("some plan with schedule", List.of(), "@hourly", true);

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plans/", planDto, PlanDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        final String uuid = extractUuidFromLocation(response.getHeaders().getLocation());

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity(response.getHeaders().getLocation(), PlanDto.class);
        assertThat(planResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanDto returnedPlanDto = planResponse.getBody();
        assertThat(returnedPlanDto).isNotNull();
        assertThat(returnedPlanDto.getUuid()).isEqualTo(uuid);
        assertThat(returnedPlanDto.getName()).isEqualTo(planDto.getName());
        assertThat(returnedPlanDto.getTasks()).isNotNull().isEmpty();
        assertThat(returnedPlanDto.getSchedule()).isEqualTo(planDto.getSchedule());
        assertThat(returnedPlanDto.isScheduleActive()).isEqualTo(planDto.isScheduleActive());
    }

    @Test
    public void get_all_plans() {
        dataAccess.save(new CreatePlanDto("empty plan 1", List.of()));
        dataAccess.save(new CreatePlanDto("empty plan 2", List.of()));
        final long repositorySize = getRepositorySize();

        final ParameterizedTypeReference<List<PlanEntity>> listViewResponse = new ParameterizedTypeReference<>() {};
        final ResponseEntity<List<PlanEntity>> planListResponse = testRestTemplate.exchange("/plans/", HttpMethod.GET, null, listViewResponse);
        assertThat(planListResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final List<PlanEntity> planList = planListResponse.getBody();
        assertThat(planList).isNotNull().hasSize((int) repositorySize);
    }

    @Test
    public void get_one_plan() {
        final PlanEntity simplePlan = dataAccess.save(new CreatePlanDto("the simplest plan possible", List.of()));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity("/plans/%s".formatted(simplePlanUuid), PlanDto.class);
        assertThat(planResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanDto plan = planResponse.getBody();
        assertThat(plan).isNotNull();
        assertThat(plan.getUuid()).isNotEmpty();
        assertThat(plan.getName()).isEqualTo("the simplest plan possible");
        assertThat(plan.getTasks()).isNotNull().isEmpty();
    }

    @Test
    public void run_plan() {
        final PlanEntity simplePlan = dataAccess.save(new CreatePlanDto("the simplest plan possible", List.of()));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<ScheduleResponse> response = testRestTemplate.postForEntity("/plans/%s/run".formatted(simplePlanUuid), null, ScheduleResponse.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        final ScheduleResponse scheduleResponse = response.getBody();
        assertThat(scheduleResponse).isNotNull();
        assertThat(scheduleResponse.isSuccess()).isTrue();
        assertThat(scheduleResponse.getMessage()).isEqualTo("Plan has been scheduled to run: now");
    }

    @Test
    public void it_returns_404_for_get_latest_execution_record_if_none_exist() {
        final PlanEntity simplePlan = dataAccess.save(new CreatePlanDto("the simplest plan possible", List.of()));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<PlanExecutionRecordDto> response = testRestTemplate.getForEntity("/plans/%s/execution-records/latest".formatted(simplePlanUuid), PlanExecutionRecordDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void get_latest_execution_record() {
        final PlanEntity simplePlan = dataAccess.save(new CreatePlanDto("the simplest plan possible", List.of()));
        final PlanExecutionRecordEntity executionRecord = dataAccess.save(createExecutionRecordFor(simplePlan));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<PlanExecutionRecordDto> response = testRestTemplate.getForEntity("/plans/%s/execution-records/latest".formatted(simplePlanUuid), PlanExecutionRecordDto.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanExecutionRecordDto recordDto = response.getBody();
        assertThat(recordDto).isNotNull();
        assertThat(recordDto.getUuid()).isEqualTo(executionRecord.getUuid().toString());
        assertThat(recordDto.getRuntimeMillis()).isEqualTo(123);
        assertThat(recordDto.isResultPositive()).isTrue();
    }

    private long getRepositorySize() {
        return dataAccess.countAll();
    }

    private PlanEntity getPlanFromRepository(String uuid) {
        final PlanEntity plan = dataAccess.findPlanByUuid(UUID.fromString(uuid)).orElseThrow();
        assertThat(plan).isNotNull();

        return plan;
    }

    private String extractUuidFromLocation(URI location) {
        final String[] split = location.toString().split("/");
        return split[split.length - 1];
    }

    private PlanExecutionRecordEntity createExecutionRecordFor(PlanEntity plan) {
        final PlanExecutionRecordEntity executionRecord = new PlanExecutionRecordEntity();
        executionRecord.setPlan(plan);
        executionRecord.setStartTimeEpochMillis(123000);
        executionRecord.setEndTimeEpochMillis(123123);
        executionRecord.setResultPositive(true);
        executionRecord.setUpdated(new Date());

        return executionRecord;
    }
}
