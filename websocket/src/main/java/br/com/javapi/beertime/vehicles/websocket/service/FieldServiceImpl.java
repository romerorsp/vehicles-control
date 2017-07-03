package br.com.javapi.beertime.vehicles.websocket.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hazelcast.core.IMap;

import br.com.javapi.beertime.vehicles.common.bean.Field;

@Service
class FieldServiceImpl implements FieldService {

    @Resource(name="fieldsMap")
    private IMap<String, Field> fields;

    @Override
    public List<Field> getFieldList() {
        //TODO: replace with correct implementation;
        final Field field = new Field();
        field.setName("Field From Server");
        field.setWidth(1600);
        field.setHeight(900);

        final Field field2 = new Field();
        field2.setName("Another Field From Server");
        field2.setWidth(800);
        field2.setHeight(600);

        return Arrays.asList(field, field2);
    }
}
