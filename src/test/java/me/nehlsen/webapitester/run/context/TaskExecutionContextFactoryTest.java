package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.run.dto.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class TaskExecutionContextFactoryTest {

    private TaskExecutionContextFactory taskExecutionContextFactory;

    @BeforeEach
    void setUp() {
        taskExecutionContextFactory = new TaskExecutionContextFactory();
    }

    @Test
    public void created_task_context_has_task_and_plan_context() {
        TaskDto task = Mockito.mock(TaskDto.class);
        PlanExecutionContext planExecutionContext = Mockito.mock(PlanExecutionContext.class);

        final TaskExecutionContext taskExecutionContext = taskExecutionContextFactory.createContext(task, planExecutionContext, null);

        assertThat(taskExecutionContext.getTask()).isSameAs(task);
        assertThat(taskExecutionContext.getPlanExecutionContext()).isSameAs(planExecutionContext);
    }

    @Test
    public void created_task_context_is_added_to_plan_context() {
        TaskDto task = Mockito.mock(TaskDto.class);
        PlanExecutionContext planExecutionContext = Mockito.mock(PlanExecutionContext.class);

        taskExecutionContextFactory.createContext(task, planExecutionContext, null);

        Mockito.verify(planExecutionContext, Mockito.atLeastOnce()).addTaskExecutionContext(Mockito.any());
    }
}
