package kamysh.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpaceMarineOnBoardException extends RemoteServiceException{
    private Long id;


    public SpaceMarineOnBoardException(Long id) {
        super(ErrorMessage.SPACE_MARINE_ON_BOARD);
        this.id = id;
    }

    public SpaceMarineOnBoardException() {
        super(ErrorMessage.SPACE_MARINE_ON_BOARD);
    }
}
