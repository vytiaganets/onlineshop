package com.example.onlineshop.mapper;

public interface Mapper <Entity, Dto>{
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}
