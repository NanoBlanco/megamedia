package com.desarrollo.services;

import java.util.List;

import com.desarrollo.entities.Cliente;

public interface IClientService {
	
	Cliente saveClient(Cliente client);
	List<Cliente> getAllClients();
	void deleteClient(String rut);
	Cliente getClientByRut(String rut);

}
