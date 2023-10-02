package com.flaviopessini.springbootapigateway.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private final static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        final var list = new ArrayList<D>();
        for (O o : origin) {
            list.add(mapper.map(o, destination));
        }
        return list;
    }
}
