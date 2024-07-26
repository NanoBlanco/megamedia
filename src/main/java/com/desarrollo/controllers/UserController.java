package com.desarrollo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desarrollo.entities.User;
import com.desarrollo.exceptions.UserException;
import com.desarrollo.services.IUserService;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private IUserService IuserService;
	
	@GetMapping("/users/all")
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<>(IuserService.getUsers(),HttpStatus.FOUND);
	}
	
	@GetMapping("/user/{correo}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("correo") String correo) {
		try {
			User user = IuserService.getUserByCorreo(correo);
			return ResponseEntity.ok(user);
		}catch(UserException ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recuperando el usuario");
		}
	}
		
	@DeleteMapping("/user/del/{correo}")
	public ResponseEntity<String> deleteUser(@PathVariable String correo) {
		try {
			IuserService.deleteUser(correo);
			return ResponseEntity.ok("Usuario Eliminado con exito");
		}catch(UserException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error eliminando el usuario");
		}
	}
	
}
