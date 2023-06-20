package me.nehlsen.webapitester.plan;

import me.nehlsen.webapitester.task.HttpGetTask;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Component
public class PlanRepository {
    public void save(Plan plan) {

    }

    public Plan findByUuid(String uuid) {
        if (uuid.equals("af70b1d3-ad41-4d9f-b44a-f3cc7524d142")) {
            return new Plan(
                    UUID.fromString("af70b1d3-ad41-4d9f-b44a-f3cc7524d142"),
                    "some Plan from repo",
                    List.of(new HttpGetTask("task from repo", URI.create("scheme://host/path"), List.of()))
            );
        }

        throw PlanNotFoundException.byUuid(uuid);
    }
}
