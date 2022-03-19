package kamysh.service;

import kamysh.dto.StarshipDTO;
import kamysh.entity.LoadStarship;
import kamysh.entity.Starship;
import kamysh.exceptions.EntryNotFound;
import kamysh.exceptions.ErrorMessage;
import kamysh.exceptions.SpaceMarineOnBoardException;
import kamysh.exceptions.StorageServiceRequestException;
import kamysh.repository.StarshipRepository;
import kamysh.util.ClientFactoryBuilder;
import net.sf.corn.converter.json.JsTypeComplex;
import net.sf.corn.converter.json.JsonStringParser;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Remote(StarshipService.class)
public class StarshipServiceImpl implements StarshipService {
    private final Client client;
    private String storageServiceUrl;
    private final StarshipRepository starshipRepository;
    private final String targetId = "l1";

    public StarshipServiceImpl() {
        this.client = ClientFactoryBuilder.getClient();
//        this.storageServiceUrl = ClientFactoryBuilder.getStorageServiceUrl();
        this.starshipRepository = new StarshipRepository();
        initServices();


    }

    @Override
    public StarshipDTO setParatroopers(Long starshipId, Long spaceMarineId) throws EntryNotFound, SpaceMarineOnBoardException, StorageServiceRequestException {
        initServices();

        Starship starship = starshipRepository.findById(starshipId);
        if (starship == null) {
            throw new EntryNotFound(starshipId, ErrorMessage.STARSHIP_NOT_FOUND);
        }
        checkSpaceMarineOnBoard(spaceMarineId, starshipId);
        LoadStarship loadStarship = new LoadStarship();
        loadStarship.setStarship(starship);
        loadStarship.setSpaceMarineId(spaceMarineId);

        if (checkById(spaceMarineId)) {
            starshipRepository.save(loadStarship);

            StarshipDTO starshipDTO = StarshipDTO.builder()
                    .name(starship.getName())
                    .totalParatroopers(starshipRepository.getCountSpaceMarineInStarship(starshipId))
                    .build();

            return starshipDTO;
        }
        return new StarshipDTO();
    }

    @Override
    public StarshipDTO landAllParatroopers(Long starshipId) throws EntryNotFound {
        initServices();

        Starship starship = starshipRepository.findById(starshipId);
        if (starship == null) {
            throw new EntryNotFound(starshipId, ErrorMessage.STARSHIP_NOT_FOUND);
        }

        starshipRepository.delete(starshipId);
        StarshipDTO starshipDTO = StarshipDTO.builder()
                .name(starshipRepository.findById(starshipId).getName())
                .totalParatroopers(starshipRepository.getCountSpaceMarineInStarship(starshipId))
                .build();
        return starshipDTO;
    }

    @Override
    public boolean checkById(Long id) throws StorageServiceRequestException, EntryNotFound {
        initServices();

        String url = storageServiceUrl + "api/space-marine/" + id;
        System.out.println(url);
        checkServerState();
        Response response;
        try {
            response = client.target(url)
                    .request(MediaType.APPLICATION_XML + "; charset=UTF-8")
                    .get();
        } catch (Exception e) {
            throw new StorageServiceRequestException(ErrorMessage.STORAGE_SERVICE_REQUEST_FAILED);
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new EntryNotFound(id, ErrorMessage.SPACEMARINE_NOT_FOUND);
        }
        if (response.getStatus() > 300) throw new StorageServiceRequestException();
        return response.getStatus() == 200;


    }

    @Override
    public void checkServerState() throws StorageServiceRequestException {
        initServices();

        String url = storageServiceUrl + "api/state";
        Response response;
        try {
            response = client.target(url).request().get();
        } catch (Exception e) {
            throw new StorageServiceRequestException(ErrorMessage.SERVER_NOT_AVAILABLE);
        }
        if (response.getStatus() != 200) {
            throw new StorageServiceRequestException(ErrorMessage.SERVER_NOT_AVAILABLE);
        }
    }

    @Override
    public void checkSpaceMarineOnBoard(final long spaceMarineId, final long currentStarshipId) throws SpaceMarineOnBoardException {
        initServices();

        try {
            Long starshipId = starshipRepository.getStarshipIdBySpaceMarine(spaceMarineId);
            if (currentStarshipId == starshipId) {
                throw new SpaceMarineOnBoardException();
            } else {
                throw new SpaceMarineOnBoardException(starshipId);
            }
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void landParatrooper(Long spaceMatine) {
        initServices();
        starshipRepository.deleteParatrooper(spaceMatine);
    }

    private boolean initServices() {
        String sdUrl = System.getenv("CONSUL_URL");
        String url = sdUrl + "/v1/agent/service/" + targetId;
        try {
            Response response = client.target(url).request().get();
            if (response.getStatus() != 200){
                String jsonString = response.getEntity().toString();
                JsTypeComplex jsonResponse = (JsTypeComplex) JsonStringParser.parseJsonString(jsonString);

                storageServiceUrl = "https://" + jsonResponse.get("Address").toString().replace("\"", "") + ":" + jsonResponse.get("Port");
            }

        } catch (Exception e) {

            return false;
        }
        return true;
    }
}
