package kamysh.util;


import lombok.SneakyThrows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientFactoryBuilder {
    private static Client client;

    @SneakyThrows
    public static Client getClient() {
        if (client != null) return client;
        client = ClientBuilder.newBuilder().build();
        return client;
    }

    public static String getStorageServiceUrl() {
        String URL_SERVER = System.getenv("URL_SERVER");
        if (URL_SERVER == null) URL_SERVER = System.getProperty("URL_SERVER");
        return URL_SERVER;
    }
}
