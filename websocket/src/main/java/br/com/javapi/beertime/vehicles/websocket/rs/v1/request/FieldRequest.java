package br.com.javapi.beertime.vehicles.websocket.rs.v1.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.javapi.beertime.vehicles.common.bean.BeanProvider;
import br.com.javapi.beertime.vehicles.common.bean.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XmlRootElement
@EqualsAndHashCode(callSuper=false)
public class FieldRequest implements BeanProvider<Field> {
    
    @NotNull(message="The field name is required")
    @NotEmpty(message="The field name is required")
    private String name;
    
    @Min(value=1, message="The width needs to be greather than 0")
    private int width;
    
    @Min(value=1, message="The height needs to be greather than 0")
    private int height;
    
    @Override
    public Field getBean() {
        return new Field(name, width, height);
    }
}
