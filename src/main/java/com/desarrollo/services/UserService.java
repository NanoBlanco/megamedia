package com.desarrollo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.desarrollo.entities.Role;
import com.desarrollo.entities.User;
import com.desarrollo.exceptions.UserException;
import com.desarrollo.repositories.IUserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	
	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		if(userRepository.existsByCorreo(user.getCorreo())) {
			throw new UserException(user.getCorreo()+" ya está registrado!");
		}
		List<User> users = userRepository.findAll();
		if(users.isEmpty()) {
			Role role = Role.ADMIN;
			user.setRole(role);
		}else {
			Role role = Role.USER;
			user.setRole(role);
		}
		user.setClave(passwordEncoder.encode(user.getClave()));
		user.setCreadoEn(LocalDateTime.now());
		return userRepository.save(user);
	}
	
	public User updateUser(String correo, User user) {
		if (userRepository.existsByCorreo(correo)) {
			User u = userRepository.findByCorreo(correo).orElse(null);
			u.setNombre(user.getNombre());
			u.setApellido(user.getApellido());
			u.setCorreo(user.getCorreo());
			return userRepository.save(u);
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteUser(String correo) {
		User user = getUserByCorreo(correo);
		if(user!=null) {
			userRepository.deleteByCorreo(correo);
		}
		
	}

	@Override
	public User getUserByCorreo(String correo) {
		return userRepository.findByCorreo(correo).orElseThrow();
	}

}
