package me.nehlsen.webapitester.persistence;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTimeTaskAssertionEntity extends TaskAssertionEntity {
    long maximumRequestTimeMillis;
}
