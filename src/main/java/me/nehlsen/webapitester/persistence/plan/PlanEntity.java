package me.nehlsen.webapitester.persistence.plan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plan")
@Getter
@Setter
@NoArgsConstructor
public class PlanEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String name;

    @OneToMany
    @Cascade(CascadeType.ALL)
    List<TaskEntity> tasks;

    String schedule = "";
    boolean scheduleActive = false;

    @CreationTimestamp
    Date created;

    @UpdateTimestamp
    Date updated;
}
