package br.com.javapi.beertime.vehicles.consumer.automata;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.javapi.beertime.vehicles.consumer.exception.MalFormedAutomataPatternException;

@Configuration
@ConfigurationProperties(prefix = "vehicles.automata")
class AutomataConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomataConfiguration.class);

    private List<String> script = new ArrayList<String>();

    @Bean
    public Automata createParsing() {
        try {
            final Automata automata = Automata.parse(getScript());
            LOGGER.info("PARSED AUTOMATA: [{}]", automata);
            return automata;
        } catch (MalFormedAutomataPatternException e) {
            LOGGER.error("Your Automaton script seems not to be in a good format.", e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getScript() {
        return script;
    }

    public void setScript(final List<String> script) {
        this.script = script;
    }
}
