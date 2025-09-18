package nixdocs.model;

import java.util.UUID;

public record User(
    Id id,
    Email email,
    Name name,
    Password password
) {
    public record Id(UUID value) {
        public static Id of(UUID value) {
            return new Id(value);
        }
    }

    public record Email(String value) {
        public static Email of(String value) {
            return new Email(value);
        }
    }

    public record Name(String value) {
        public static Name of(String value) {
            return new Name(value);
        }
    }

    public record Password(String value) {
        public static Password of(String value) {
            return new Password(value);
        }
    }
}
