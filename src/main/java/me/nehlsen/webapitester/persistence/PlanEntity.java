package me.nehlsen.webapitester.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class PlanEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    String name;

    @OneToMany
    List<TaskEntity> tasks;
}
