package com.edsonalexandre.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edsonalexandre.cursomc.security.JWTUtil;
import com.edsonalexandre.cursomc.security.UserSS;
import com.edsonalexandre.cursomc.services.UserService;

@RestController(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@GetMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}
