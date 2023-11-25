package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.api.plan.PlanExecutionRecordDto;
import me.nehlsen.webapitester.api.plan.PlanExecutionRecordMapper;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "plans/{uuid}/execution-records")
@CrossOrigin(origins = "${webapitester.cors.origins:localhost}")
public class PlanExecutionRecordController {

    private final DataAccess dataAccess;
    private final PlanExecutionRecordMapper mapper;

    public PlanExecutionRecordController(DataAccess dataAccess, PlanExecutionRecordMapper mapper) {
        this.dataAccess = dataAccess;
        this.mapper = mapper;
    }

    @GetMapping("/latest")
    public PlanExecutionRecordDto latest(@PathVariable String uuid) {
        final PlanExecutionRecordEntity executionContextEntity = dataAccess.findLatestExecutionRecord(uuid);
        return mapper.planExecutionRecordEntityToDto(executionContextEntity);
    }

    @GetMapping("/")
    public List<PlanExecutionRecordDto> all(@PathVariable String uuid, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        final List<PlanExecutionRecordEntity> executionRecords = dataAccess.findExecutionRecords(uuid, page, pageSize);
        return mapper.planExecutionRecordsEntityToDtos(executionRecords);
    }
}
