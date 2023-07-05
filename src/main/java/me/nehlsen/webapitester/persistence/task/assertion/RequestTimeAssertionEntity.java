package me.nehlsen.webapitester.persistence.task.assertion;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("request_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTimeAssertionEntity extends AssertionEntity {
    long maximumRequestTimeMillis;
}
