package me.nehlsen.webapitester.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
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
    List<TaskAssertionEntity> assertions;
}
