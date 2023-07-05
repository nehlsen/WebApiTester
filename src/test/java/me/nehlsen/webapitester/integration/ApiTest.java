package me.nehlsen.webapitester.integration;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.api.task.CreateTaskDto;
import me.nehlsen.webapitester.api.plan.PlanDto;
import me.nehlsen.webapitester.api.task.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

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
    void create_plan_with_name_and_tasks() {
        final ResponseEntity<PlanDto> response = testRestTemplate.postForEntity(
                "/plan/",
                new CreatePlanDto("some plan with task", List.of(new CreateTaskDto("void", "empty task", "needs://a-valid.url"))),
                PlanDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUuid()).isNotEmpty();
        assertThat(response.getBody().getName()).isEqualTo("some plan with task");
        assertThat(response.getBody().getTasks()).hasSize(1);
        final TaskDto firstTaskDto = response.getBody().getTasks().get(0);
        assertThat(firstTaskDto.getType()).isEqualTo("void");
        assertThat(firstTaskDto.getName()).isEqualTo("empty task");
        assertThat(firstTaskDto.getUri()).isEqualTo("needs://a-valid.url");
    }
}
