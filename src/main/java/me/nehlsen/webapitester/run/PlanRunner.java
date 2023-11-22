package me.nehlsen.webapitester.run;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.PlanExecutionContextFactory;
import me.nehlsen.webapitester.run.dto.PlanDto;
import me.nehlsen.webapitester.run.factory.TaskRunnerFactory;
import org.springframework.stereotype.Component;

@Component @Log4j2
public class PlanRunner {
    private final PlanExecutionContextFactory planExecutionContextFactory;
    private final TaskRunnerFactory taskRunnerFactory;

    public PlanRunner(PlanExecutionContextFactory planExecutionContextFactory, TaskRunnerFactory taskRunnerFactory) {
        this.planExecutionContextFactory = planExecutionContextFactory;
        this.taskRunnerFactory = taskRunnerFactory;
    }

    public void execute(PlanDto plan) {
        log.info("Executing Plan \"{}\"", plan.getName());

        final PlanExecutionContext context = planExecutionContextFactory.createContext(plan);

        TaskRunner taskRunner = taskRunnerFactory.create(context);

        plan.getTasks().forEach(task -> taskRunner.execute(task));
    }
}
