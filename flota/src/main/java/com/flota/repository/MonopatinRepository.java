package com.flota.repository;

import com.flota.entity.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {
}