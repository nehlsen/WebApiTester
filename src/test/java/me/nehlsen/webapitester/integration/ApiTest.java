package me.nehlsen.webapitester.integration;

import me.nehlsen.webapitester.api.assertion.AssertionDto;
import me.nehlsen.webapitester.api.assertion.CreateAssertionDto;
import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.api.plan.PlanDto;
import me.nehlsen.webapitester.api.task.TaskDto;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void create_plan_with_name_and_no_tasks_is_returned_and_has_a_uuid_assigned() {
        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity(
                "/plan/",
                new CreatePlanDto("a new test plan", List.of()),
                PlanDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUuid()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo("a new test plan");
        assertThat(response.getBody().getTasks()).isEmpty();
    }

    @Test
    void create_plan_with_name_and_tasks_but_no_assertions() {
        final CreateTaskDto taskDto = new CreateTaskDto("void", "empty task", "needs://a-valid.url", List.of());
        final CreatePlanDto planDto = new CreatePlanDto("some plan with task", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plan/", planDto, PlanDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUuid()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo("some plan with task");
        assertThat(response.getBody().getTasks()).hasSize(1);

        final TaskDto firstTaskDto = response.getBody().getTasks().get(0);
        assertThat(firstTaskDto.getUuid()).isNotEmpty();
        assertThat(firstTaskDto.getType()).isEqualTo("void");
        assertThat(firstTaskDto.getName()).isEqualTo("empty task");
        assertThat(firstTaskDto.getUri()).isEqualTo("needs://a-valid.url");
    }

    @Test
    void create_plan_with_name_and_tasks_including_assertions() {
        final CreateAssertionDto assertionDto = new CreateAssertionDto("response_status_code", Map.of("expectedStatusCode", "200"));
        final CreateTaskDto taskDto = new CreateTaskDto("http_get", "request BadSSL", "https://badssl.com", List.of(assertionDto));
        final CreatePlanDto planDto = new CreatePlanDto("some plan with task", List.of(taskDto));

        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity("/plan/", planDto, PlanDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTasks()).hasSize(1);

        final TaskDto firstTaskDto = response.getBody().getTasks().get(0);
        assertThat(firstTaskDto.getAssertions()).hasSize(1);

        final AssertionDto firstAssertion = firstTaskDto.getAssertions().get(0);
        assertThat(firstAssertion.getUuid()).isNotEmpty();
        assertThat(firstAssertion.getType()).isEqualTo("response_status_code");
        assertThat(firstAssertion.getParameters()).isNotEmpty();
        assertThat(firstAssertion.getParameters()).contains(entry("expectedStatusCode", "200"));
    }

    @Test
    public void get_plan() {
        final PlanEntity simplePlan = dataAccess.saveNew(new CreatePlanDto("the simplest plan possible", List.of()));
        final String simplePlanUuid = simplePlan.getUuid().toString();

        final ResponseEntity<PlanDto> planResponse = testRestTemplate.getForEntity("/plan/%s".formatted(simplePlanUuid), PlanDto.class);
        final PlanDto plan = planResponse.getBody();
        assertThat(plan).isNotNull();
        assertThat(plan.getUuid()).isNotEmpty();
        assertThat(plan.getName()).isEqualTo("the simplest plan possible");
        assertThat(plan.getTasks()).isNotNull().isEmpty();
    }
}
