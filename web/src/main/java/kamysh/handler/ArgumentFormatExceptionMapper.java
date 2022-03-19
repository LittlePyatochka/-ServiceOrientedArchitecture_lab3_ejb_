package kamysh.handler;

import kamysh.dto.ValidationErrorDTO;
import kamysh.exceptions.ArgumentFormatException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Provider
public class ArgumentFormatExceptionMapper implements ExceptionMapper<ArgumentFormatException> {
    @Override
    public Response toResponse(ArgumentFormatException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ValidationErrorDTO
                        .builder()
                        .error(ErrorCode.INCORRECT_ARGUMENT_FORMAT.name())
                        .message(
                                Stream.of(new String[][]{
                                        {e.getArgument(), e.getMessage()},
                                }).collect(Collectors.toMap(data -> data[0], data -> data[1]))
                        )
                        .build())
                .build();
    }
}
