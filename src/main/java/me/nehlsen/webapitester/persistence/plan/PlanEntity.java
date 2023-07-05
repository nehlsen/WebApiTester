package me.nehlsen.webapitester.persistence.plan;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;
import java.util.UUID;

@Entity
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

    // FIXME timestamps created, updated
}
