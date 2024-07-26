package com.desarrollo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desarrollo.entities.Cliente;
import com.desarrollo.exceptions.ClientException;
import com.desarrollo.services.ClientService;
import com.desarrollo.services.IClientService;

import jakarta.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
@RequestMapping("/api")
@Api(value="Controlador de clientes")
public class ClientController {
	
	@Autowired
	private IClientService clientService;
	
	@Autowired
	private ClientService cService;
	
	@ApiOperation(value="Registra a un cliente", response = List.class)
	@PostMapping("/client/register")
	public ResponseEntity<?> registerClient(@Valid @RequestBody Cliente client){
		try {
			cService.saveClient(client);
			return ResponseEntity.ok("Cliente registrado con exito");
		}catch(ClientException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
		
	}
	
	@ApiOperation(value="Genera una lista de todos los clientes", response = List.class)
	@GetMapping("/clients/all")
	public ResponseEntity<List<Cliente>> getAllClients() {
		return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.FOUND);
	}
	
	@ApiOperation(value="Muestra un cliente por rut", response = List.class)
	@GetMapping("/client/{rut}")
	public ResponseEntity<?> getClientByRut(@PathVariable("rut") String rut) {
		try {
			Cliente client = clientService.getClientByRut(rut);
			return ResponseEntity.ok(client); 
		}catch(ClientException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recuperando el cliente");
		}
	}
	
	@ApiOperation(value="Actualiza los datos de un cliente", response = List.class)
	@PutMapping("/client/update/{rut}")
	public ResponseEntity<?> updateClient(@RequestBody Cliente client, String rut){
		try {
			cService.updateClient(rut, client);
			return ResponseEntity.ok("Cliente actualizado...!");
		}catch(ClientException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());	
		}
	}
	
	@ApiOperation(value="Elimina a un cliente", response = List.class)
	@DeleteMapping("/client/del/{rut}")
	public ResponseEntity<String> deleteClient(@PathVariable("rut") String rut) {
		try {
			clientService.deleteClient(rut);
			return ResponseEntity.ok("Cliente eliminado"); 
		}catch(ClientException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recuperando el cliente");
		}
	}

}
