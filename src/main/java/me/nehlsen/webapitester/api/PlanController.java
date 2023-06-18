package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.plan.Plan;
import me.nehlsen.webapitester.plan.PlanFactory;
import me.nehlsen.webapitester.plan.PlanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "plan")
public class PlanController {
    private final PlanFactory planFactory;
    private final PlanRepository planRepository;
    private final PlanDtoFactory planDtoFactory;

    public PlanController(
            PlanFactory planFactory,
            PlanRepository planRepository,
            PlanDtoFactory planDtoFactory
    ) {
        this.planFactory = planFactory;
        this.planRepository = planRepository;
        this.planDtoFactory = planDtoFactory;
    }

    @PostMapping
    public ResponseEntity<PlanDto> createPlan(CreatePlanRequestData planDto) {
        Plan plan = planFactory.createPlanFromDto(planDto);
        planRepository.save(plan);

        return ResponseEntity.ok(planDtoFactory.fromPlan(plan));
    }
}
