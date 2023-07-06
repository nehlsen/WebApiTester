package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.run.dto.PlanDto;
import org.springframework.stereotype.Component;

@Component
public class PlanExecutionContextFactory {
    public PlanExecutionContext createContext(PlanDto plan) {
        return new PlanExecutionContext(plan);
    }
}
