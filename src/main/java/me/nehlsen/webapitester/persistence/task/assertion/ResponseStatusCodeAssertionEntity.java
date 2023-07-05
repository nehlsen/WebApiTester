package me.nehlsen.webapitester.persistence.task.assertion;

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
public class ResponseStatusCodeAssertionEntity extends AssertionEntity {
    int expectedStatusCode;
}
