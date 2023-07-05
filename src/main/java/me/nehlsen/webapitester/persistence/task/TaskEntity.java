package me.nehlsen.webapitester.persistence.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.nehlsen.webapitester.persistence.task.assertion.AssertionEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Entity
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

    @OneToMany
    @Cascade(CascadeType.ALL)
    List<AssertionEntity> assertions;
}
