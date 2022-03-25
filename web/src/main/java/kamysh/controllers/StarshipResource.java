package kamysh.controllers;

import kamysh.dto.StarshipArgumentDTO;
import kamysh.dto.StarshipDTO;
import kamysh.exceptions.*;
import kamysh.service.StarshipService;
import lombok.SneakyThrows;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import static kamysh.utils.Utils.ACTIONS_API_CALCULATION_RESOURCE;

@WebService(
        name = "StarshipResource",
        targetNamespace = ACTIONS_API_CALCULATION_RESOURCE
)
public class StarshipResource {
    private final StarshipService starshipService;


    @SneakyThrows
    public StarshipResource() {
        Context context = new ContextProvider().getContext();
        String moduleName = System.getProperty("SOA_EJB_MODULE_NAME");
        Object refStarshipService = context.lookup("ejb:/" + moduleName + "/StarshipServiceImpl!" + StarshipService.class.getName());
        this.starshipService = (StarshipService) PortableRemoteObject.narrow(refStarshipService, StarshipService.class);
    }

    @WebMethod
    public StarshipDTO putOnStarship(@WebParam(name = "argument") StarshipArgumentDTO argument)
            throws EntryNotFound, SpaceMarineOnBoardException, StorageServiceRequestException {
            return starshipService.setParatroopers(argument.getStarship(), argument.getSpaceMarine());
    }

    @WebMethod
    public StarshipDTO kickOutOfStarship(@WebParam(name ="starship-id") Long starshipId)
            throws EntryNotFound, StorageServiceRequestException {
            return starshipService.landAllParatroopers(starshipId);
    }

}
