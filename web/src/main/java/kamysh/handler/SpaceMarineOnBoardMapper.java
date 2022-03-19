package kamysh.handler;

import kamysh.dto.ErrorDTO;
import kamysh.dto.ValidationErrorDTO;
import kamysh.exceptions.EntryNotFound;
import kamysh.exceptions.ErrorMessage;
import kamysh.exceptions.SpaceMarineOnBoardException;
import kamysh.exceptions.StorageServiceRequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SpaceMarineOnBoardMapper implements ExceptionMapper<SpaceMarineOnBoardException> {

    @Override
    public Response toResponse(SpaceMarineOnBoardException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ErrorDTO
                        .builder()
                        .error(ErrorCode.SPACE_MARINE_ALREADY_ON_BOARD.name())
                        .message(ErrorMessage.SPACE_MARINE_ON_BOARD)
                        .build())
                .build();
    }
}
