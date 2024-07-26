package com.desarrollo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.desarrollo.entities.Cliente;
import com.desarrollo.repositories.IClientRepository;
import com.desarrollo.exceptions.ClientException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {
	
	private final IClientRepository clientRepository;

	@Override
	public Cliente saveClient(Cliente client) {
		if(clientRepository.existsByRut(client.getRut())) {
			throw new ClientException(client.getRut()+" Ya está registrado");
		}
		return clientRepository.save(client);
	}

	@Override
	public List<Cliente> getAllClients() {
		return clientRepository.findAll();
	}
	
	public Cliente updateClient(String rut, Cliente client) {
		if(clientRepository.existsByRut(rut)) {
			Cliente nuevoClient = clientRepository.findByRut(rut).orElse(null);
			nuevoClient.setNombre(client.getNombre());
			nuevoClient.setApellido(client.getApellido());
			nuevoClient.setCorreo(client.getCorreo());
			nuevoClient.setDireccion(client.getDireccion());
			nuevoClient.setTelefono(client.getTelefono());
			return clientRepository.save(nuevoClient);
		}
		return null;
	}

	@Override
	public void deleteClient(String rut) {
		Cliente client = getClientByRut(rut);
		if(client != null) {
			clientRepository.delete(client);
		}
	}

	@Override
	public Cliente getClientByRut(String rut) {
		return clientRepository.findByRut(rut).orElseThrow(()->new ClientException("Cliente no encontrado....!"));
	}

}
