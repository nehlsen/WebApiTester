package me.nehlsen.webapitester.run;

import lombok.extern.log4j.Log4j2;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.PlanExecutionContextFactory;
import me.nehlsen.webapitester.run.dto.PlanDto;
import me.nehlsen.webapitester.run.event.AfterRunPlanEvent;
import me.nehlsen.webapitester.run.event.BeforeRunPlanEvent;
import me.nehlsen.webapitester.run.factory.TaskRunnerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PlanRunner {
    private final PlanExecutionContextFactory planExecutionContextFactory;
    private final TaskRunnerFactory taskRunnerFactory;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PlanRunner(
            PlanExecutionContextFactory planExecutionContextFactory,
            TaskRunnerFactory taskRunnerFactory,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.planExecutionContextFactory = planExecutionContextFactory;
        this.taskRunnerFactory = taskRunnerFactory;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(PlanDto plan) {
        log.info("Executing Plan \"{}\"", plan.getName());

        final PlanExecutionContext context = planExecutionContextFactory.createContext(plan);
        TaskRunner taskRunner = taskRunnerFactory.create(context);

        applicationEventPublisher.publishEvent(new BeforeRunPlanEvent(this, context));
        taskRunner.execute(plan.getTasks());
        applicationEventPublisher.publishEvent(new AfterRunPlanEvent(this, context));
    }
}
