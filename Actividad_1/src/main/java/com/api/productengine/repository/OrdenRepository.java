package com.api.productengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.productengine.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    
}
