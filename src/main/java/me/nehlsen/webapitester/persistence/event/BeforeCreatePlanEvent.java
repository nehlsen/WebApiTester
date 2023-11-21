package me.nehlsen.webapitester.persistence.event;

import lombok.Getter;
import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import org.springframework.context.ApplicationEvent;

@Getter
public class BeforeCreatePlanEvent extends ApplicationEvent {
    private final CreatePlanDto createPlanDto;

    public BeforeCreatePlanEvent(Object source, CreatePlanDto createPlanDto) {
        super(source);
        this.createPlanDto = createPlanDto;
    }
}
