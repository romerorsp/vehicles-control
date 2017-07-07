package br.com.javapi.beertime.vehicles.consumer.automata.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.hazelcast.core.IMap;

import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.common.bean.StateType;
import br.com.javapi.beertime.vehicles.common.bean.Transitions;
import br.com.javapi.beertime.vehicles.common.bean.Vehicle;
import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import br.com.javapi.beertime.vehicles.consumer.automata.Automata;

@Component
public class AutomataService {
    

    @Resource(name="fieldsMap")
    private IMap<String, Field> fields;

    @Resource(name="vehiclesMap")
    private IMap<String, Vehicle> vehicles;
    
    @Autowired
    private Automata automata;

    @ServiceActivator(inputChannel="vehiclesInputChannel")
    public void consume(VehicleStateDTO state) {
        if(!fields.containsKey(state.getFieldId())) {
            throw new RuntimeException("There was an attempt to record vehicle for unexisting field");
        }
        final Vehicle vehicle = new Vehicle(state.getFieldId(), state.getVehicleId(), automata.getInitial().nextFor(state.getTransition()));
        if(state.getTransition() == Transitions.CREATE) {
            if(vehicles.values()
                       .stream()
                       .filter(v -> v.getFieldId().equals(state.getFieldId()))
                       .map(Vehicle::getId)
                       .anyMatch(state.getVehicleId()::equalsIgnoreCase)) {
                throw new RuntimeException("There was an attempt to record a vehicle that already exists");
            }
            vehicle.setPosX(state.getPosX());
            vehicle.setPosY(state.getPosY());
            vehicles.put(vehicle.getId(), vehicle);
        } else {
            if(vehicle.getState().getType() == StateType.END) {
                vehicles.remove(vehicle.getId());
            } else {
                vehicle.setPosX(state.getPosX());
                vehicle.setPosY(state.getPosY());
                vehicles.put(vehicle.getId(), vehicle);
            }
        }
    }
}
