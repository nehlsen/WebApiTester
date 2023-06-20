package me.nehlsen.webapitester.plan;

import me.nehlsen.webapitester.api.CreatePlanRequestData;
import me.nehlsen.webapitester.api.PlanController;
import me.nehlsen.webapitester.api.PlanDto;
import me.nehlsen.webapitester.api.PlanDtoFactory;
import me.nehlsen.webapitester.api.TaskDto;
import me.nehlsen.webapitester.api.TaskDtoFactory;
import me.nehlsen.webapitester.task.TaskFactory;
import me.nehlsen.webapitester.task.UnknownTaskTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlanControllerTest {

    private PlanController planController;

    @BeforeEach
    void setUp() {
        TaskFactory taskFactory = new TaskFactory();
        PlanFactory planFactory = new PlanFactory(taskFactory);

        TaskDtoFactory taskDtoFactory = new TaskDtoFactory();
        PlanDtoFactory planDtoFactory = new PlanDtoFactory(taskDtoFactory);

        PlanRepository planRepository = new PlanRepository();

        planController = new PlanController(planFactory, planRepository, planDtoFactory);
    }

    @Test
    public void create_plan_returns_the_new_plan_on_success() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("created in unit test", List.of());
        PlanDto createdPlanDto = planController.createPlan(requestPlanDto).getBody();

        assertThat(createdPlanDto).isNotNull();
    }

    @Test
    public void a_created_plan_has_requested_name_and_some_uuid() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("created in unit test", List.of());
        PlanDto createdPlanDto = planController.createPlan(requestPlanDto).getBody();

        assertThat(createdPlanDto).isNotNull();
        assertThat(createdPlanDto.getUuid()).isNotNull();
        assertThat(createdPlanDto.getUuid()).isNotEmpty();
        assertThat(createdPlanDto.getName()).isEqualTo(requestPlanDto.getName());
    }

    @Test
    public void create_plan_with_null_task_type_throws_exception() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("my plan", List.of(new TaskDto(null, null, null)));

        assertThatThrownBy(() -> planController.createPlan(requestPlanDto).getBody())
                .isInstanceOf(UnknownTaskTypeException.class)
                .hasMessage("Task Type \"\" not found");
    }

    @Test
    public void create_plan_with_unknown_task_type_throws_exception() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("my plan", List.of(new TaskDto("booBaa", null, null)));

        assertThatThrownBy(() -> planController.createPlan(requestPlanDto).getBody())
                .isInstanceOf(UnknownTaskTypeException.class)
                .hasMessage("Task Type \"booBaa\" not found");
    }

    @Test
    public void a_created_plan_has_requested_task_with_void_type() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("my plan", List.of(new TaskDto("void", null, null)));
        PlanDto createdPlanDto = planController.createPlan(requestPlanDto).getBody();

        assertThat(createdPlanDto).isNotNull();
        assertThat(createdPlanDto.getTasks()).isNotEmpty();
        assertThat(createdPlanDto.getTasks().get(0)).isInstanceOf(TaskDto.class);
        assertThat(createdPlanDto.getTasks().get(0).getType()).isEqualTo("void");
    }

    @Test
    public void a_created_plan_has_requested_task_with_http_get_type_and_properties() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("my plan", List.of(new TaskDto("http_get", "my task name", "http://the-url.com")));
        PlanDto createdPlanDto = planController.createPlan(requestPlanDto).getBody();

        assertThat(createdPlanDto).isNotNull();
        assertThat(createdPlanDto.getTasks()).isNotEmpty();
        assertThat(createdPlanDto.getTasks().get(0)).isInstanceOf(TaskDto.class);
        assertThat(createdPlanDto.getTasks().get(0).getType()).isEqualTo("http_get");
        assertThat(createdPlanDto.getTasks().get(0).getName()).isEqualTo("my task name");
        assertThat(createdPlanDto.getTasks().get(0).getUri()).isEqualTo("http://the-url.com");
    }

    @Test
    public void get_plan() {
        final PlanDto planDto = planController.getPlan("af70b1d3-ad41-4d9f-b44a-f3cc7524d142").getBody();

        assertThat(planDto).isNotNull();
        assertThat(planDto.getUuid()).isEqualTo("af70b1d3-ad41-4d9f-b44a-f3cc7524d142");
        assertThat(planDto.getName()).isEqualTo("some Plan from repo");
        assertThat(planDto.getTasks()).isNotEmpty();
        assertThat(planDto.getTasks().get(0)).isInstanceOf(TaskDto.class);
        assertThat(planDto.getTasks().get(0).getType()).isEqualTo("http_get");
        assertThat(planDto.getTasks().get(0).getName()).isEqualTo("task from repo");
        assertThat(planDto.getTasks().get(0).getUri()).isEqualTo("scheme://host/path");
    }
}
