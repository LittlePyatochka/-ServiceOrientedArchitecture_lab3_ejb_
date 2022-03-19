package kamysh.exceptions;

public class SpaceMarineOnBoardException extends Throwable{
    private Long id;


    public SpaceMarineOnBoardException(Long id) {
        super(ErrorMessage.SPACE_MARINE_ON_BOARD);
        this.id = id;
    }

    public SpaceMarineOnBoardException() {
        super(ErrorMessage.SPACE_MARINE_ON_BOARD);
    }
}
