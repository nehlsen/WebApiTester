package me.nehlsen.webapitester.persistence.task.assertion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "assertion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AssertionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;
}
