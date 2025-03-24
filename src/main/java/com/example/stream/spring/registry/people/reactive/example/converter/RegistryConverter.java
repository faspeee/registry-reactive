package com.example.stream.spring.registry.people.reactive.example.converter;

import com.example.stream.spring.registry.people.reactive.example.entity.Registry;
import com.example.stream.spring.registry.people.reactive.example.model.RegistryDto;
import org.springframework.stereotype.Component;

@Component
public class RegistryConverter implements Converter<RegistryDto, Registry> {
  @Override
  public RegistryDto toDto(Registry entity) {
    return new RegistryDto();
  }

  @Override
  public Registry toEntity(RegistryDto dto) {
    Registry registry = new Registry();
    return registry;
  }
}
