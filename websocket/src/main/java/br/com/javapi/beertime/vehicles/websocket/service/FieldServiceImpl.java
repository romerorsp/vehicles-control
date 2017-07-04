package br.com.javapi.beertime.vehicles.websocket.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        return Optional.ofNullable(fields)
                                 .map(IMap::values)
                                 .orElseGet(Collections::emptyList)
                                 .stream()
                                 .collect(Collectors.toList());
    }

    @Override
    public boolean addField(Field bean) {
        try {
            return (!fields.containsKey(bean.getId())) && fields.tryPut(bean.getId(), bean, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            return false;
        }
    }
}
