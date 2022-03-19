package kamysh.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StorageServiceRequestException extends Exception{
    public StorageServiceRequestException(String message) {
        super(message);
    }

    public StorageServiceRequestException() {
        super(ErrorMessage.STORAGE_SERVICE_REQUEST_FAILED);
    }
}
