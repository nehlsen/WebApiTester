package me.nehlsen.webapitester.integration;

import me.nehlsen.webapitester.api.ScheduleResponse;
import me.nehlsen.webapitester.api.assertion.AssertionDto;
import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;
import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.api.plan.PlanDto;
import me.nehlsen.webapitester.api.task.TaskDto;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DataAccess dataAccess;

    @Test
    void create_plan_with_name_and_empty_list_of_tasks_is_saved_in_repository() {
        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity(
                "/plan/",
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
        final int sizeBefore = getRepositorySize();

        final HttpEntity<CreatePlanDto> requestBody = new HttpEntity<>(new CreatePlanDto("a new test plan", null));
        final ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
        final ResponseEntity<Map<String, Object>> response = testRestTemplate.exchange(
                "/plan/",
                HttpMethod.POST,
                requestBody,
                responseType
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(getRepositorySize()).isEqualTo(sizeBefore);
    }

    @Test
    void create_plan_with_name_and_tasks_but_no_assertions() {
        final CreateTaskDto taskDto = new CreateTaskDto("void", "empty task", "needs://a-valid.url", List.of());
        final CreatePlanDto planDto = new CreatePlanDto("some plan with task", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plan/", planDto, PlanDto.class);
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
        final CreateTaskDto taskDto = new CreateTaskDto("http_get", "request BadSSL", "https://badssl.com", List.of(assertionDto));
        final CreatePlanDto planDto = new CreatePlanDto("some plan with task and assertion", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plan/", planDto, PlanDto.class);
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
    void create_plan_with_schedule() {
        final CreatePlanDto planDto = new CreatePlanDto("some plan with schedule", List.of(), "@hourly", true);

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plan/", planDto, PlanDto.class);
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
    public void get_plan() {
        final PlanEntity simplePlan = dataAccess.saveNew(new CreatePlanDto("the simplest plan possible", List.of()));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity("/plan/%s".formatted(simplePlanUuid), PlanDto.class);
        assertThat(planResponse.getStatusCode().is2xxSuccessful()).isTrue();

        final PlanDto plan = planResponse.getBody();
        assertThat(plan).isNotNull();
        assertThat(plan.getUuid()).isNotEmpty();
        assertThat(plan.getName()).isEqualTo("the simplest plan possible");
        assertThat(plan.getTasks()).isNotNull().isEmpty();
    }

    @Test
    public void run_plan() {
        final PlanEntity simplePlan = dataAccess.saveNew(new CreatePlanDto("the simplest plan possible", List.of()));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<ScheduleResponse> response = testRestTemplate.postForEntity("/plan/%s/run".formatted(simplePlanUuid), null, ScheduleResponse.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        final ScheduleResponse scheduleResponse = response.getBody();
        assertThat(scheduleResponse).isNotNull();
        assertThat(scheduleResponse.isSuccess()).isTrue();
        assertThat(scheduleResponse.getMessage()).isEqualTo("Plan has been scheduled to run: now");
    }

    private int getRepositorySize() {
        return dataAccess.findAll().size();
    }

    private PlanEntity getPlanFromRepository(String uuid) {
        final PlanEntity plan = dataAccess.findByUuid(uuid);
        assertThat(plan).isNotNull();

        return plan;
    }

    private String extractUuidFromLocation(URI location) {
        final String[] split = location.toString().split("/");
        return split[split.length - 1];
    }
}
