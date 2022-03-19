package kamysh.service;

import kamysh.dto.StarshipDTO;
import kamysh.exceptions.EntryNotFound;
import kamysh.exceptions.SpaceMarineOnBoardException;
import kamysh.exceptions.StorageServiceRequestException;

import javax.ejb.Remote;

@Remote
public interface StarshipService {

    StarshipDTO setParatroopers(Long starshipId, Long spaceMarineId) throws EntryNotFound, SpaceMarineOnBoardException, StorageServiceRequestException;
    StarshipDTO landAllParatroopers(Long starshipId) throws EntryNotFound;
    boolean checkById(Long id) throws StorageServiceRequestException, EntryNotFound;
    void checkServerState() throws StorageServiceRequestException;
    void checkSpaceMarineOnBoard(final long spaceMarineId, final long currentStarshipId) throws SpaceMarineOnBoardException;
    void landParatrooper(Long spaceMatine);
}
