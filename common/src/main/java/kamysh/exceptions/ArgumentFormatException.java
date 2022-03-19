package kamysh.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArgumentFormatException extends Exception {
    private final String argument;

    public ArgumentFormatException(String argument, String message) {
        super(message);
        this.argument = argument;
    }
}
