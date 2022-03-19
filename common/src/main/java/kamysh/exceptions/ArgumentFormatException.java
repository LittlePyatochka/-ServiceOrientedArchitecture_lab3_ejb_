package kamysh.exceptions;

import lombok.Getter;

@Getter
public class ArgumentFormatException extends Exception {
    private final String argument;

    public ArgumentFormatException(String argument, String message) {
        super(message);
        this.argument = argument;
    }
}
