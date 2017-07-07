package br.com.javapi.beertime.vehicles.consumer.automata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import br.com.javapi.beertime.vehicles.common.bean.LinkedState;
import br.com.javapi.beertime.vehicles.common.bean.State;
import br.com.javapi.beertime.vehicles.common.bean.StateType;
import br.com.javapi.beertime.vehicles.common.bean.Transition;
import br.com.javapi.beertime.vehicles.common.bean.Transitions;
import br.com.javapi.beertime.vehicles.consumer.exception.MalFormedAutomataPatternException;
import lombok.ToString;

@ToString
public final class Automata {
    
    private static final String INITIAL_STATE_NAME = "INITIAL";
    private static final Function<String, String[]> ARROW_SPLITTER = value -> value.split("\\-\\>");
    private static final Function<String, String[]> COLON_SPLITTER = value -> value.split("[\\:>]");
    
    private final Map<String, State> states;
    
    private final State initial;
    
    private Automata(final Map<String, State> states, State initial) {
        this.states = states;
        this.initial = initial;
    }
    
    public final static Automata parse(List<String> script) throws MalFormedAutomataPatternException {
        try {
            Map<String, State> states = Optional.ofNullable(script)
                                                                          .orElseGet(Collections::emptyList)
                                                                          .stream()
                                                                          .map(String::toUpperCase)
                                                                          .collect(Collectors.toMap(value -> ARROW_SPLITTER.apply(value)[0], value -> new LinkedState(StateType.fromString(ARROW_SPLITTER.apply(value)[0]))));
            script.stream()
                      .map(String::toUpperCase)
                      .forEach(value -> {
                          final String[] split = ARROW_SPLITTER.apply(value);
                          Automata.link(states.get(split[0]), split.length > 1? split[1]: "", states);
                      });
            return new Automata(states, states.get(INITIAL_STATE_NAME));
        } catch(Exception e) {
            throw new MalFormedAutomataPatternException(e);
        }
    }
    
    private static void link(State state, String keyValueString, Map<String, State> states) {
        Map<Transition, State> keyValues = Arrays.stream(keyValueString.split("[\\,]"))
                                                                                .filter(StringUtils::hasLength)
                                                                                .collect(Collectors.toMap(value -> Transitions.valueOf(COLON_SPLITTER.apply(value)[0]), value -> states.get(COLON_SPLITTER.apply(value)[1])));
        state.setTransitions(keyValues);
    }

    public State getInitial() {
        return initial;
    }
    
    public Optional<State> fromName(String name) {
        if(StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        return Optional.ofNullable(states.get(name.toUpperCase()));
    }
}
