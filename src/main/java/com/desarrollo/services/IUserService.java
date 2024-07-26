package com.desarrollo.services;

import java.util.List;

import com.desarrollo.entities.User;

public interface IUserService {
	
	User saveUser(User user);
	
	List<User> getUsers();
	
	void deleteUser(String correo);
	
	User getUserByCorreo(String correo); 

}
