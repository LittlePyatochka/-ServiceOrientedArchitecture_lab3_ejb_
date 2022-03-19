package kamysh.exceptions;

public class StorageServiceRequestException extends Throwable{
    public StorageServiceRequestException(String message) {
        super(message);
    }

    public StorageServiceRequestException() {
        super(ErrorMessage.STORAGE_SERVICE_REQUEST_FAILED);
    }
}
