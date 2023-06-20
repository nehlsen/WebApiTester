package me.nehlsen.webapitester.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Entity
abstract public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String name;

    URI uri;

    @OneToMany
    List<TaskAssertionEntity> assertions;
}
