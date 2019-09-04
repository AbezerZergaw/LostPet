package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface PetRepo extends CrudRepository<Pet, Long> {
}
