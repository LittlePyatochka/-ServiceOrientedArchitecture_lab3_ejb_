package kamysh.controllers;

import kamysh.dto.StarshipDTO;
import kamysh.exceptions.ArgumentFormatException;
import kamysh.exceptions.ErrorMessage;
import kamysh.handler.RemoteExceptionsHandler;
import kamysh.service.StarshipService;
import lombok.SneakyThrows;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/starship")
public class StarshipResource {
    private final StarshipService starshipService;

    @SneakyThrows
    public StarshipResource() {
        Context context = new ContextProvider().getContext();
        String moduleName = System.getProperty("SOA_EJB_MODULE_NAME");
        Object refStarshipService = context.lookup("java:global/" + moduleName + "/StarshipServiceImpl!" + StarshipService.class.getName());
        this.starshipService = (StarshipService) PortableRemoteObject.narrow(refStarshipService, StarshipService.class);
    }

    @POST
    @Path("/{starship-id}/load/{space-marine-id}")
    @Produces(value = MediaType.APPLICATION_XML + "; charset=UTF-8")
    @SneakyThrows
    public StarshipDTO putOnStarship(
            @PathParam("starship-id") String starshipId,
            @PathParam("space-marine-id") String spaceMarineId
    ) {
        Long starship, spaceMarine;
        try {
            starship = Long.parseLong(starshipId);
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException("starshipId", ErrorMessage.IS_NOT_INTEGER);
        }
        try {
            spaceMarine = Long.parseLong(spaceMarineId);
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException("spaceMarineId", ErrorMessage.IS_NOT_INTEGER);
        }
        try {
            return starshipService.setParatroopers(starship, spaceMarine);
        } catch (Throwable e) {
            throw RemoteExceptionsHandler.handleRemoteException(e);
        }
    }

    @POST
    @Path("/{starship-id}/unload-all")
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    @SneakyThrows
    public StarshipDTO kickOutOfStarship(
            @PathParam("starship-id") String starshipId
    ) {
        Long starship;
        try {
            starship = Long.parseLong(starshipId);
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException("starshipId", ErrorMessage.IS_NOT_INTEGER);
        }
        try {
            return starshipService.landAllParatroopers(starship);
        } catch (Throwable e) {
            throw RemoteExceptionsHandler.handleRemoteException(e);
        }
    }

    @POST
    @Path("/{space-marine-id}")
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    @SneakyThrows
    public void kickOutOfStarshipCurrentSpaceMatine(
            @PathParam("space-marine-id") String spaceMarineId
    ) {
        Long spaceMarine;
        try {
            spaceMarine = Long.parseLong(spaceMarineId);
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException("spaceMarine", ErrorMessage.IS_NOT_INTEGER);
        }
        try {
            starshipService.landParatrooper(spaceMarine);
        } catch (Throwable e) {
            throw RemoteExceptionsHandler.handleRemoteException(e);
        }
    }
}
