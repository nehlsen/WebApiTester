package me.nehlsen.webapitester.persistence.event;

import lombok.Getter;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.springframework.context.ApplicationEvent;

@Getter
public class AfterCreatePlanEvent extends ApplicationEvent {
    private final PlanEntity planEntity;

    public AfterCreatePlanEvent(Object source, PlanEntity planEntity) {
        super(source);
        this.planEntity = planEntity;
    }
}
