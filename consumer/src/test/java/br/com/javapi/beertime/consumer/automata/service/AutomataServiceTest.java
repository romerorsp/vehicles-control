package br.com.javapi.beertime.consumer.automata.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.hazelcast.core.IMap;

import br.com.javapi.beertime.vehicles.common.bean.Field;
import br.com.javapi.beertime.vehicles.common.bean.StateType;
import br.com.javapi.beertime.vehicles.common.bean.Transitions;
import br.com.javapi.beertime.vehicles.common.bean.Vehicle;
import br.com.javapi.beertime.vehicles.common.dto.VehicleStateDTO;
import br.com.javapi.beertime.vehicles.consumer.automata.Automata;
import br.com.javapi.beertime.vehicles.consumer.automata.service.AutomataService;
import br.com.javapi.beertime.vehicles.consumer.exception.MalFormedAutomataPatternException;

@RunWith(MockitoJUnitRunner.class)
public class AutomataServiceTest {
    
    @Mock(name="fieldsMap")
    private IMap<String, Field> fields;

    @Mock(name="vehiclesMap")
    private IMap<String, Vehicle> vehicles;

    @InjectMocks
    private AutomataService service;
    
    private Automata automata = parseAutomata();
    
    @Before
    public void setup() {
        this.service.setFields(fields);
        this.service.setVehicles(vehicles);
        this.service.setAutomata(automata);
    }
    
    private Automata parseAutomata() {
        try {
            return Automata.parse(Arrays.asList("initial->CREATE:created,PAUSE:initial,FINISH:initial,MOVE_UP:initial,MOVE_DOWN:initial,MOVE_LEFT:initial,MOVE_RIGHT:initial",
                                                "created->PAUSE:created,CREATE:created,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                                "Moving_Up->PAUSE:created,CREATE:Moving_Up,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                                "Moving_Down->PAUSE:created,CREATE:Moving_Down,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                                "Moving_Left->PAUSE:created,CREATE:Moving_Left,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                                "Moving_Right->PAUSE:created,CREATE:Moving_Right,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                                "End->"));
        } catch (MalFormedAutomataPatternException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }

    @Test
    public void testService() {
        Assert.assertEquals(StateType.INITIAL, automata.getInitial().getType());
        Mockito.when(fields.containsKey(Mockito.any())).thenReturn(true);
        VehicleStateDTO state = new VehicleStateDTO("any", "any", 0, 0, Transitions.CREATE);
        Vehicle vehicle = new Vehicle(state.getFieldId(), state.getVehicleId(), automata.getInitial().nextFor(state.getTransition()));
        Mockito.when(vehicles.getOrDefault(Mockito.anyString(), Mockito.anyObject())).thenReturn(vehicle);
        service.consume(state);
        Mockito.verify(fields).containsKey(Mockito.any());
        Mockito.verify(vehicles).put(Mockito.anyString(), Mockito.any());
    }
}
