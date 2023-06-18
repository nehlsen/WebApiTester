package me.nehlsen.webapitester.plan;

import me.nehlsen.webapitester.api.CreatePlanRequestData;
import me.nehlsen.webapitester.api.PlanController;
import me.nehlsen.webapitester.api.PlanDto;
import me.nehlsen.webapitester.api.PlanDtoFactory;
import me.nehlsen.webapitester.api.TaskDto;
import me.nehlsen.webapitester.api.TaskDtoFactory;
import me.nehlsen.webapitester.task.TaskFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    public void a_created_plan_has_requested_name_and_random_uuid() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("created in unit test", List.of());
        PlanDto createdPlanDto = planController.createPlan(requestPlanDto).getBody();

        assertThat(createdPlanDto.getUuid()).isNotNull();
        assertThat(createdPlanDto.getUuid().toString()).isNotEmpty();
        assertThat(createdPlanDto.getName()).isEqualTo(requestPlanDto.getName());
    }

    @Test
    public void a_created_plan_has_requested_number_of_tasks() {
        CreatePlanRequestData requestPlanDto = new CreatePlanRequestData("my plan", List.of(new TaskDto()));
        PlanDto createdPlanDto = planController.createPlan(requestPlanDto).getBody();

        assertThat(createdPlanDto.getTasks()).hasSize(1);
    }
}