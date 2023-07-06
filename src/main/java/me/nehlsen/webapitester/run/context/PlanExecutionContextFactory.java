package me.nehlsen.webapitester.run.context;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.springframework.stereotype.Component;

@Component
public class PlanExecutionContextFactory {
    public PlanExecutionContext createContext(PlanEntity plan) {
        return new PlanExecutionContext(plan);
    }
}
