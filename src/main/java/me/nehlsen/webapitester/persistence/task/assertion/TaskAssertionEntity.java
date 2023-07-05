package me.nehlsen.webapitester.persistence.task.assertion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
abstract class TaskAssertionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;
}
