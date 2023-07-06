package me.nehlsen.webapitester.fixture;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;

import java.util.List;
import java.util.UUID;

public class PlanEntityFixture {
    public static PlanEntity planWithoutTasks() {
        PlanEntity planEntity = new PlanEntity();
        planEntity.setUuid(UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"));
        planEntity.setName("a test plan");
        planEntity.setTasks(List.of());

        return planEntity;
    }

    public static PlanEntity planWithTasks() {
        PlanEntity planEntity = planWithoutTasks();

        planEntity.setTasks(createTaskEntities());

        return planEntity;
    }

    private static List<TaskEntity> createTaskEntities() {
        final VoidTaskEntity voidTaskEntity = new VoidTaskEntity();
        final HttpGetTaskEntity httpGetTaskEntity = new HttpGetTaskEntity();

        return List.of(voidTaskEntity, httpGetTaskEntity);
    }
}
