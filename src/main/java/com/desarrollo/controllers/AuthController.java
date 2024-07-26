package com.desarrollo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import com.desarrollo.entities.User;
import com.desarrollo.exceptions.UserException;
import com.desarrollo.request.LoginRequest;
import com.desarrollo.response.AuthResponse;
import com.desarrollo.security.jwt.JwtUtils;
import com.desarrollo.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(value="Controlador de acceso")
public class AuthController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@ApiOperation(value="Valida el acceso a los usuarios", response = List.class)
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
		
		Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		String role = userDetails.getAuthorities().toString();
		
		AuthResponse authResponse = new AuthResponse(userDetails.getUsername(), jwt, role);

        return ResponseEntity.ok(authResponse);
	}

	
	@ApiOperation(value="Registra a los usuarios", response = List.class)
	@PostMapping("/register")
	public ResponseEntity<?> registroUsuario(@Valid @RequestBody User user){
		try {
			userService.saveUser(user);
			return ResponseEntity.ok("Usuario registrado con exito");
		}catch(UserException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}		
	}
	
}
