package org.jsp.ecommerceapp.controller;

import java.util.List;
import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.jsp.ecommerceapp.model.User;
import org.jsp.ecommerceapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	public UserService userService;

	@PostMapping
	public ResponseEntity<ResponseStructure<User>> saveMerchant(@RequestBody User user, HttpServletRequest request) {
		return userService.saveUser(user, request);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<User>> findById(@PathVariable int id) {
		return userService.findById(id);
	}

	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructure<User>> verifyByEmail(@RequestParam String email,
			@RequestParam String password) {
		return userService.verifyByEmail(email, password);
	}

	@PostMapping("/verfiy-by-phone")
	public ResponseEntity<ResponseStructure<User>> verifyByPhone(@RequestParam long phone,
			@RequestParam String password) {
		return userService.verifyByPhone(phone, password);
	}
	@GetMapping("/findAll")
	public ResponseEntity<ResponseStructure<List<User>>> findAll() {
		return userService.findAll();
	}
	@GetMapping("/activate")
	public ResponseEntity<ResponseStructure<String>> activate(@RequestParam String token){
		return userService.activate(token);
	}
}
