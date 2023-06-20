package me.nehlsen.webapitester.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class TaskAssertionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;
}
