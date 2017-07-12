package br.com.javapi.beertime.consumer.automata;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.javapi.beertime.vehicles.common.bean.State;
import br.com.javapi.beertime.vehicles.common.bean.StateType;
import br.com.javapi.beertime.vehicles.common.bean.Transitions;
import br.com.javapi.beertime.vehicles.consumer.automata.Automata;
import br.com.javapi.beertime.vehicles.consumer.exception.MalFormedAutomataPatternException;

public class AutomataTest {

    private List<String> script;
    
    @Before
    public void setup() {
        this.script = Arrays.asList("initial->CREATE:created,PAUSE:initial,FINISH:initial,MOVE_UP:initial,MOVE_DOWN:initial,MOVE_LEFT:initial,MOVE_RIGHT:initial",
                                    "created->PAUSE:created,CREATE:created,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                    "Moving_Up->PAUSE:created,CREATE:Moving_Up,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                    "Moving_Down->PAUSE:created,CREATE:Moving_Down,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                    "Moving_Left->PAUSE:created,CREATE:Moving_Left,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                    "Moving_Right->PAUSE:created,CREATE:Moving_Right,FINISH:end,MOVE_UP:Moving_Up,MOVE_DOWN:Moving_Down,MOVE_LEFT:Moving_Left,MOVE_RIGHT:Moving_Right",
                                    "End->");
    }
    
    @Test
    public void testParse() throws MalFormedAutomataPatternException {
        Automata result = Automata.parse(script);
        State initial = result.getInitial();
        Assert.assertEquals(StateType.INITIAL, initial.getType());
        Assert.assertEquals(StateType.CREATED, initial.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.INITIAL, initial.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.INITIAL, initial.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.INITIAL, initial.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.INITIAL, initial.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.INITIAL, initial.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.INITIAL, initial.nextFor(Transitions.FINISH).getType());

        State created = initial.nextFor(Transitions.CREATE);
        Assert.assertEquals(StateType.CREATED, created.getType());
        Assert.assertEquals(StateType.CREATED, created.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.MOVING_DOWN, created.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.MOVING_UP, created.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.MOVING_LEFT, created.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, created.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.CREATED, created.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.END, created.nextFor(Transitions.FINISH).getType());

        State movingDown = created.nextFor(Transitions.MOVE_DOWN);
        Assert.assertEquals(StateType.MOVING_DOWN, movingDown.getType());
        Assert.assertEquals(StateType.MOVING_DOWN, movingDown.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.MOVING_DOWN, movingDown.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.MOVING_UP, movingDown.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.MOVING_LEFT, movingDown.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, movingDown.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.CREATED, movingDown.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.END, movingDown.nextFor(Transitions.FINISH).getType());

        State movingUp = movingDown.nextFor(Transitions.MOVE_UP);
        Assert.assertEquals(StateType.MOVING_UP, movingUp.getType());
        Assert.assertEquals(StateType.MOVING_UP, movingUp.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.MOVING_DOWN, movingUp.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.MOVING_UP, movingUp.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.MOVING_LEFT, movingUp.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, movingUp.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.CREATED, movingUp.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.END, movingUp.nextFor(Transitions.FINISH).getType());

        State movingLeft = movingUp.nextFor(Transitions.MOVE_LEFT);
        Assert.assertEquals(StateType.MOVING_LEFT, movingLeft.getType());
        Assert.assertEquals(StateType.MOVING_LEFT, movingLeft.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.MOVING_DOWN, movingLeft.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.MOVING_UP, movingLeft.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.MOVING_LEFT, movingLeft.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, movingLeft.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.CREATED, movingLeft.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.END, movingLeft.nextFor(Transitions.FINISH).getType());

        State movingRight = movingLeft.nextFor(Transitions.MOVE_RIGHT);
        Assert.assertEquals(StateType.MOVING_RIGHT, movingRight.getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, movingRight.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.MOVING_DOWN, movingRight.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.MOVING_UP, movingRight.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.MOVING_LEFT, movingRight.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, movingRight.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.CREATED, movingRight.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.END, movingRight.nextFor(Transitions.FINISH).getType());

        State pause = movingRight.nextFor(Transitions.PAUSE);
        Assert.assertEquals(StateType.CREATED, pause.getType());
        Assert.assertEquals(StateType.CREATED, pause.nextFor(Transitions.CREATE).getType());
        Assert.assertEquals(StateType.MOVING_DOWN, pause.nextFor(Transitions.MOVE_DOWN).getType());
        Assert.assertEquals(StateType.MOVING_UP, pause.nextFor(Transitions.MOVE_UP).getType());
        Assert.assertEquals(StateType.MOVING_LEFT, pause.nextFor(Transitions.MOVE_LEFT).getType());
        Assert.assertEquals(StateType.MOVING_RIGHT, pause.nextFor(Transitions.MOVE_RIGHT).getType());
        Assert.assertEquals(StateType.CREATED, pause.nextFor(Transitions.PAUSE).getType());
        Assert.assertEquals(StateType.END, pause.nextFor(Transitions.FINISH).getType());

        State end = pause.nextFor(Transitions.FINISH);
        Assert.assertEquals(StateType.END, end.getType());
        Assert.assertNull(end.nextFor(Transitions.CREATE));
        Assert.assertNull(end.nextFor(Transitions.MOVE_DOWN));
        Assert.assertNull(end.nextFor(Transitions.MOVE_UP));
        Assert.assertNull(end.nextFor(Transitions.MOVE_LEFT));
        Assert.assertNull(end.nextFor(Transitions.MOVE_RIGHT));
        Assert.assertNull(end.nextFor(Transitions.PAUSE));
        Assert.assertNull(end.nextFor(Transitions.FINISH));
    }
}
