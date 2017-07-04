package br.com.javapi.beertime.vehicles.websocket.endpoint;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.undertow.websockets.jsr.ServerEndpointConfigImpl;

public class CustomSpringConfigurator extends ServerEndpointConfigImpl.Configurator implements ApplicationContextAware {

    /**
     * Spring application context.
     */
    private  static volatile ApplicationContext context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomSpringConfigurator.context = applicationContext;
    }
}