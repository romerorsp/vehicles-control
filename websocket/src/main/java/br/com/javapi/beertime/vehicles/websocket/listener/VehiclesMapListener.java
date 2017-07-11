package br.com.javapi.beertime.vehicles.websocket.listener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryMergedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

import br.com.javapi.beertime.vehicles.common.bean.Vehicle;
import br.com.javapi.beertime.vehicles.websocket.endpoint.SupervisorWebSocket;

@Component
public class VehiclesMapListener implements EntryAddedListener<String, Vehicle>,
                                            EntryMergedListener<String, Vehicle>,
                                            EntryUpdatedListener<String, Vehicle>,
                                            EntryRemovedListener<String, Vehicle> {

    @Resource(name="vehiclesMap")
    private IMap<String, Vehicle> vehicles;
    
    @Autowired
    private SupervisorWebSocket supervisor;
    
    @PostConstruct
    public void setup() {
        vehicles.addEntryListener(this, true);
    }

    @Override
    public void entryUpdated(EntryEvent<String, Vehicle> entry) {
        supervisor.notifyVehicleState(entry.getValue().toDTO());
    }

    @Override
    public void entryMerged(EntryEvent<String, Vehicle> entry) {
        supervisor.notifyVehicleState(entry.getValue().toDTO());
    }

    @Override
    public void entryAdded(EntryEvent<String, Vehicle> entry) {
        supervisor.notifyVehicleState(entry.getValue().toDTO());
    }

    @Override
    public void entryRemoved(EntryEvent<String, Vehicle> entry) {
        supervisor.notifyVehicleRemoved(entry.getValue().toDTO());
    }
}
