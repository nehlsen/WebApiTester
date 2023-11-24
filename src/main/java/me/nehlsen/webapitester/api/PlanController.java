package me.nehlsen.webapitester.api;

import jakarta.validation.Valid;
import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.api.plan.PlanDto;
import me.nehlsen.webapitester.api.plan.PlanDtoFactory;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.PlanNotFoundException;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.plan.PlanListView;
import me.nehlsen.webapitester.run.scheduler.RunScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "plans")
@CrossOrigin(origins = "${webapitester.cors.origins:localhost}")
public class PlanController {

    private final DataAccess dataAccess;
    private final PlanDtoFactory planDtoFactory;
    private final RunScheduler runScheduler;

    public PlanController(
            DataAccess dataAccess,
            PlanDtoFactory planDtoFactory,
            RunScheduler runScheduler
    ) {
        this.dataAccess = dataAccess;
        this.planDtoFactory = planDtoFactory;
        this.runScheduler = runScheduler;
    }

    @PostMapping(path = "/")
    public ResponseEntity<PlanDto> createPlan(@Valid @RequestBody CreatePlanDto planDto) {
        final PlanEntity savedPlan = dataAccess.save(planDto);

        final String createdPlanUri = MvcUriComponentsBuilder.fromMappingName("get_plan").arg(0, savedPlan.getUuid().toString()).build();

        return ResponseEntity.created(URI.create(createdPlanUri)).build();
    }

    @GetMapping(path = "/")
    @ResponseBody
    public List<PlanListView> all() {
        return dataAccess.findAllListView();
    }

    @GetMapping(name = "get_plan", path = "/{uuid}")
    public ResponseEntity<PlanDto> getPlan(@PathVariable String uuid) {
        final PlanEntity planEntity = dataAccess
                .findPlanByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> PlanNotFoundException.byUuid(uuid));

        return ResponseEntity.ok(planDtoFactory.fromEntity(planEntity));
    }

    @PostMapping(path = "/{uuid}/run")
    public ResponseEntity<ScheduleResponse> runPlan(@PathVariable String uuid) {
        final PlanEntity planEntity = dataAccess
                .findPlanByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> PlanNotFoundException.byUuid(uuid));
        runScheduler.scheduleNow(planEntity);

        return ResponseEntity.ok(new ScheduleResponse(true, "Plan has been scheduled to run: now"));
    }
}
