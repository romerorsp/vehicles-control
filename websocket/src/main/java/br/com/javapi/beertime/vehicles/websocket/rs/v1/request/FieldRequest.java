package br.com.javapi.beertime.vehicles.websocket.rs.v1.request;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.javapi.beertime.vehicles.common.bean.BeanProvider;
import br.com.javapi.beertime.vehicles.common.bean.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XmlRootElement
@EqualsAndHashCode(callSuper=false)
public class FieldRequest implements BeanProvider<Field> {
    
    @Override
    public Field getBean() {
        //TODO: Return the appropriate Bean
        return null;
    }
}
