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
    public void consume(final VehicleStateDTO state) {
        if(state.getFieldId() == null || !fields.containsKey(state.getFieldId())) {
            throw new RuntimeException("There was an attempt to record vehicle for unexisting field");
        }
        final Vehicle vehicle = vehicles.getOrDefault(state.getVehicleId(),
                                                      new Vehicle(state.getFieldId(), state.getVehicleId(), automata.getInitial().nextFor(state.getTransition())));
        if(state.getTransition() == Transitions.CREATE) {
            if(vehicles.values()
                       .stream()
                       .filter(v -> v.getFieldId() != null && v.getFieldId().equals(state.getFieldId()))
                       .map(Vehicle::getId)
                       .anyMatch(state.getVehicleId()::equalsIgnoreCase)) {
                throw new RuntimeException("There was an attempt to record a vehicle that already exists");
            }
            vehicle.setPosX(state.getPosX());
            vehicle.setPosY(state.getPosY());
            vehicle.setState(vehicle.getState().nextFor(state.getTransition()));
            vehicles.put(vehicle.getId(), vehicle);
        } else {
            if(vehicle.getState().getType() == StateType.END) {
                vehicles.remove(vehicle.getId());
            } else {
                vehicle.setPosX(state.getPosX());
                vehicle.setPosY(state.getPosY());
                vehicle.setState(vehicle.getState().nextFor(state.getTransition()));
                vehicles.put(vehicle.getId(), vehicle);
            }
        }
    }

    public void setFields(IMap<String, Field> fields) {
        this.fields = fields;
    }

    public void setVehicles(IMap<String, Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }
}
