package com.edsonalexandre.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edsonalexandre.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository< Cliente, Integer> {
}
