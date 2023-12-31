package me.nehlsen.webapitester.persistence.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plan_execution_record")
@Getter
@Setter
@NoArgsConstructor
public class PlanExecutionRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @OneToMany(mappedBy = "planExecutionRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskExecutionRecordEntity> taskExecutionRecords;

    @ManyToOne
    PlanEntity plan;

    long startTimeEpochMillis;
    long endTimeEpochMillis;
    private boolean resultPositive;

    @CreationTimestamp
    Date created;

    @UpdateTimestamp
    Date updated;
}
