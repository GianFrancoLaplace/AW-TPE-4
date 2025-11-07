package com.flota.repository;

import com.flota.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, String> {
}
