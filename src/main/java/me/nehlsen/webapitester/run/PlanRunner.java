package me.nehlsen.webapitester.run;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.run.factory.TaskRunnerFactory;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.PlanExecutionContextFactory;
import org.springframework.stereotype.Component;

@Component
public class PlanRunner {
    private final PlanExecutionContextFactory planExecutionContextFactory;
    private final TaskRunnerFactory taskRunnerFactory;

    public PlanRunner(PlanExecutionContextFactory planExecutionContextFactory, TaskRunnerFactory taskRunnerFactory) {
        this.planExecutionContextFactory = planExecutionContextFactory;
        this.taskRunnerFactory = taskRunnerFactory;
    }

    public void execute(PlanEntity plan) {
        final PlanExecutionContext context = planExecutionContextFactory.createContext(plan);

        TaskRunner taskRunner = taskRunnerFactory.create(context);

        plan.getTasks().forEach(task -> taskRunner.execute(task));
    }
}
