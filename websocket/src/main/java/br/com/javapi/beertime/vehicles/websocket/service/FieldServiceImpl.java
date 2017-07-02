package br.com.javapi.beertime.vehicles.websocket.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hazelcast.core.IMap;

import br.com.javapi.beertime.vehicles.common.bean.Field;

@Service
class FieldServiceImpl implements FieldService {

    @Resource(name="fieldsMap")
    private IMap<String, Field> fields;
}
