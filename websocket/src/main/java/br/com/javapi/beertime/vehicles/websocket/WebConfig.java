package br.com.javapi.beertime.vehicles.websocket;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import br.com.javapi.beertime.vehicles.websocket.rs.v1.VehicleResource;

@Configuration
@ApplicationPath("vehicles")
public class WebConfig extends ResourceConfig {

   public WebConfig() {
       register(VehicleResource.class);
   }
}