package me.nehlsen.webapitester.persistence.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "task_execution_record")
@Getter
@Setter
@NoArgsConstructor
public class TaskExecutionRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @ManyToOne
    PlanExecutionRecordEntity planExecutionRecord;

    @ManyToOne
    TaskEntity task;

    long startTimeEpochMillis;
    long endTimeEpochMillis;
    private boolean resultPositive;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    Request request;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    Response response;

    @CreationTimestamp
    Date created;

    @UpdateTimestamp
    Date updated;
}
