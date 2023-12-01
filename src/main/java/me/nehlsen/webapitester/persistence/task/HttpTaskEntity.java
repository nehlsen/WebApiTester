package me.nehlsen.webapitester.persistence.task;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class HttpTaskEntity extends TaskEntity {

    URI uri;

    @ElementCollection
    @JoinTable(name="task_headers", joinColumns=@JoinColumn(name="task_uuid"))
    @MapKeyColumn(name="header_name")
    @Column(name="header_values")
    Map<String, List<String>> headers;
}
