package com.desarrollo.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.desarrollo.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	
	boolean existsByCorreo(String correo);
	
	Optional<User> findByCorreo(String correo);
	
	void deleteByCorreo(String correo);
}
