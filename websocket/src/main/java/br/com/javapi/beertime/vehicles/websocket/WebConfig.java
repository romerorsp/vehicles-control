package br.com.javapi.beertime.vehicles.websocket;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("vehicles")
public class WebConfig extends ResourceConfig {

   public WebConfig() {
       //TODO: Configure your resource here
   }
}