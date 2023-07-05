package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.PlanEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "plan")
public class PlanController {


    private final DataAccess dataAccess;
    private final PlanDtoFactory planDtoFactory;

    public PlanController(
            DataAccess dataAccess,
            PlanDtoFactory planDtoFactory
    ) {
        this.dataAccess = dataAccess;
        this.planDtoFactory = planDtoFactory;
    }

    @PostMapping(path = "/")
    public ResponseEntity<PlanDto> createPlan(@RequestBody CreatePlanDto planDto) {
        final PlanEntity savedPlan = dataAccess.saveNew(planDto);

        return ResponseEntity.ok(planDtoFactory.fromEntity(savedPlan));
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<PlanDto> getPlan(@PathVariable String uuid) {
        final PlanEntity planEntity = dataAccess.findByUuid(uuid);

        return ResponseEntity.ok(planDtoFactory.fromEntity(planEntity));
    }
}
