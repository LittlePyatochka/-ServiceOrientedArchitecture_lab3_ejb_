package kamysh.handler;

import lombok.SneakyThrows;

import javax.naming.NamingException;
import java.lang.reflect.UndeclaredThrowableException;

public class RemoteExceptionsHandler {
    @SneakyThrows
    public static Throwable handleRemoteException(Throwable e) {
        if (
                e.getClass() == UndeclaredThrowableException.class &&
                e.getCause() != null && e.getCause().getClass() == NamingException.class &&
                e.getCause().getCause() != null
        ) {
            return e.getCause().getCause();
        }
        return e;
    }
}
