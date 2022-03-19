package kamysh.handler;

import kamysh.dto.ErrorDTO;
import kamysh.dto.ValidationErrorDTO;
import kamysh.exceptions.EntryNotFound;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Provider
public class EntryNotFoundMapper implements ExceptionMapper<EntryNotFound> {
    @Override
    public Response toResponse(EntryNotFound e) {
        if (e.getId() == null || e.getMessage() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorDTO
                            .builder()
                            .error(ErrorCode.ENTRY_NOT_FOUND.name())
                            .message(e.getMessage())
                            .build())
                    .build();
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ValidationErrorDTO
                        .builder()
                        .error(ErrorCode.ENTRY_NOT_FOUND.name())
                        .message(
                                Stream.of(new String[][]{
                                        {e.getId().toString(), e.getMessage()},
                                }).collect(Collectors.toMap(data -> data[0], data -> data[1]))
                        )
                        .build())
                .build();
    }
}
