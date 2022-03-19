package kamysh.controllers;

import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class ContextProvider {
    @SneakyThrows
    public Context getContext() {

        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, System.getProperty("INITIAL_CONTEXT_FACTORY"));
        environment.put(Context.PROVIDER_URL,  System.getProperty("PROVIDER_URL"));
        return new InitialContext(environment);
    }
}
