package me.nehlsen.webapitester.persistence.record;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "task_execution_record_response")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    int statusCode;

    @ElementCollection
    @JoinTable(name="task_execution_record_response_headers", joinColumns=@JoinColumn(name="task_execution_record_response_uuid"))
    @MapKeyColumn(name="header_name")
    @Column(name="header_value")
    Map<String, List<String>> headers;

    @Column(length=Length.LONG)
    String body;

    long responseTimeMillis;

    @CreationTimestamp
    Date created;

    @UpdateTimestamp
    Date updated;
}
