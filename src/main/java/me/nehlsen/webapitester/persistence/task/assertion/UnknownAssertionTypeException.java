package me.nehlsen.webapitester.persistence.task.assertion;

public class UnknownAssertionTypeException extends RuntimeException {
    public UnknownAssertionTypeException(String message) {
        super(message);
    }

    public static UnknownAssertionTypeException ofTypeString(String assertionType) {
        return new UnknownAssertionTypeException(String.format(
                "Assertion Type \"%s\" not supported",
                assertionType
        ));
    }

    public static UnknownAssertionTypeException ofTypeEntity(AssertionEntity assertion) {
        return new UnknownAssertionTypeException(String.format(
                "Assertion Type not supported (\"%s\")",
                assertion.getClass()
        ));
    }
}
