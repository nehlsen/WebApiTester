package me.nehlsen.webapitester.persistence.task;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.nehlsen.webapitester.persistence.assertion.AssertionEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String name;

    URI uri;

    @ElementCollection
    @JoinTable(name="task_parameters", joinColumns=@JoinColumn(name="task_uuid"))
    @MapKeyColumn(name="parameter_name")
    @Column(name="parameter_value")
    Map<String, String> parameters = new HashMap<>();

    @OneToMany
    @Cascade(CascadeType.ALL)
    List<AssertionEntity> assertions;

    @CreationTimestamp
    Date created;

    @UpdateTimestamp
    Date updated;
}
