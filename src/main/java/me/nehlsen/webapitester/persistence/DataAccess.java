package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.persistence.event.AfterCreatePlanEvent;
import me.nehlsen.webapitester.persistence.event.BeforeCreatePlanEvent;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.plan.PlanEntityFactory;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.plan.PlanListView;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DataAccess {

    private final PlanEntityFactory planEntityFactory;
    private final PlanRepository planRepository;
    private final TaskRepository taskRepository;
    private final PlanExecutionRecordRepository planExecutionRecordRepository;
    private final TaskExecutionRecordRepository taskExecutionRecordRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DataAccess(
            PlanEntityFactory planEntityFactory,
            PlanRepository planRepository,
            TaskRepository taskRepository,
            PlanExecutionRecordRepository planExecutionRecordRepository,
            TaskExecutionRecordRepository taskExecutionRecordRepository,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.planEntityFactory = planEntityFactory;
        this.planRepository = planRepository;
        this.taskRepository = taskRepository;
        this.planExecutionRecordRepository = planExecutionRecordRepository;
        this.taskExecutionRecordRepository = taskExecutionRecordRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public long countAll() {
        return planRepository.count();
    }

    public List<PlanEntity> findAll() {
        return planRepository.findAll();
    }
    public List<PlanListView> findAllListView() {
        return planRepository.findAllListViewBy();
    }

    public Optional<PlanEntity> findPlanByUuid(UUID uuid) {
        return planRepository.findById(uuid);
    }

    public Optional<TaskEntity> findTaskByUuid(UUID uuid) {
        return taskRepository.findById(uuid);
    }

    public PlanEntity save(CreatePlanDto planDto) {
        applicationEventPublisher.publishEvent(new BeforeCreatePlanEvent(this, planDto));
        final PlanEntity planEntity = planEntityFactory.newPlan(planDto);
        final PlanEntity savedEntity = planRepository.save(planEntity);
        applicationEventPublisher.publishEvent(new AfterCreatePlanEvent(this, savedEntity));
        return savedEntity;
    }

    public Optional<PlanExecutionRecordEntity> findPlanExecutionRecordByUuid(UUID uuid) {
        return planExecutionRecordRepository.findById(uuid);
    }

    public Optional<TaskExecutionRecordEntity> findTaskExecutionRecordByUuid(UUID uuid) {
        return taskExecutionRecordRepository.findById(uuid);
    }

    public PlanExecutionRecordEntity findLatestExecutionRecord(String uuid) {
        return planExecutionRecordRepository
                .findFirstByPlan_UuidOrderByCreatedDesc(UUID.fromString(uuid))
                .orElseThrow(() -> new PlanExecutionRecordNotFoundException("No Plan Execution Record found"));
    }

    public List<PlanExecutionRecordEntity> findExecutionRecords(String uuid, int page, int pageSize) {
        return planExecutionRecordRepository
                .findByPlan_UuidOrderByCreatedDesc(UUID.fromString(uuid), Pageable.ofSize(pageSize).withPage(page));
    }

    public PlanExecutionRecordEntity save(PlanExecutionRecordEntity executionRecord) {
        return planExecutionRecordRepository.save(executionRecord);
    }

    public TaskExecutionRecordEntity save(TaskExecutionRecordEntity executionRecord) {
        return taskExecutionRecordRepository.save(executionRecord);
    }
}
