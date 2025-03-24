package com.example.stream.spring.registry.people.reactive.example.service;

import com.example.stream.spring.registry.people.reactive.example.converter.Converter;
import com.example.stream.spring.registry.people.reactive.example.entity.Registry;
import com.example.stream.spring.registry.people.reactive.example.model.RegistryDto;
import com.example.stream.spring.registry.people.reactive.example.repository.RegistryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
public class RegistryService {
  private final RegistryRepository registryRepository;
private final Converter<RegistryDto, Registry> converter;
  public RegistryService(RegistryRepository registryRepository, Converter<RegistryDto, Registry> converter) {
    this.registryRepository = registryRepository;
    this.converter = converter;
  }
  public Flux<RegistryDto> getAllRegistry(){
    return registryRepository.findAll()
      .map(converter::toDto);
  }
}
