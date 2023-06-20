package me.nehlsen.webapitester.plan;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String message) {
        super(message);
    }

    public static PlanNotFoundException byUuid(String uuid) {
        return new PlanNotFoundException(String.format("Plan by UUID \"%s\" not found", uuid));
    }
}
