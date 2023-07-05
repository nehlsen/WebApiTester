package me.nehlsen.webapitester.api.assertion;

public class AssertionParameterMissingException extends RuntimeException {

    public AssertionParameterMissingException(String message) {
        super(message);
    }

    public static AssertionParameterMissingException byName(String name) {
        return new AssertionParameterMissingException(String.format(
                "Assertion Parameter \"%s\" missing",
                name
        ));
    }
}
