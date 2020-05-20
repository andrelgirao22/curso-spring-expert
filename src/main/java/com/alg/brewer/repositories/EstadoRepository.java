package com.alg.brewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
