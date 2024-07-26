package com.desarrollo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desarrollo.entities.Cliente;

public interface IClientRepository extends JpaRepository<Cliente, Long>{
	
	Optional<Cliente> findByRut(String rut);
	boolean existsByRut(String rut);
}
