package kamysh.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntryNotFound extends Exception {
    private Long id;

    public EntryNotFound() {
        super(ErrorMessage.NOT_FOUND);
    }

    public EntryNotFound(String m) {
        super(m);
    }

    public EntryNotFound(Long id) {
        super(ErrorMessage.NOT_FOUND);
        this.id = id;
    }

    public EntryNotFound(Long id, String m) {
        super(m);
        this.id = id;
    }
}
