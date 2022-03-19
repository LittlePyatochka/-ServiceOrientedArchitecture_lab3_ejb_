package kamysh.controllers;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthCkeckResource {
    @GET
    public String getStatus() {
        return "OK";
    }
}
