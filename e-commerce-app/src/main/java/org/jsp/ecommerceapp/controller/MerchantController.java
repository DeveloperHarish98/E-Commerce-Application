package org.jsp.ecommerceapp.controller;

import java.util.List;

import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.jsp.ecommerceapp.model.Merchant;
import org.jsp.ecommerceapp.service.MerchantService;
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
@RequestMapping("/merchants")
@CrossOrigin
public class MerchantController {

	@Autowired
	public MerchantService merchantService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Merchant>> saveMerchant(@RequestBody Merchant merchant, HttpServletRequest request) {
		return merchantService.saveMerchant(merchant, request);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Merchant>> updateMerchant(@RequestBody Merchant merchant) {
		return merchantService.updateMerchant(merchant);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Merchant>> findById(@PathVariable int id) {
		return merchantService.findById(id);
	}

	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponseStructure<Merchant>> verifyByEmail(@RequestParam String email,
			@RequestParam String password) {
		return merchantService.verifyByEmail(email, password);
	}

	@PostMapping("/verify-by-phone")
	public ResponseEntity<ResponseStructure<Merchant>> verifyByPhone(@RequestParam long phone,
			@RequestParam String password) {
		return merchantService.verifyByPhone(phone, password);
	}
	@GetMapping("/findAll")
	public ResponseEntity<ResponseStructure<List<Merchant>>> findAll() {
		return merchantService.findAll();
	}
	@GetMapping("/activate")
	public ResponseEntity<ResponseStructure<String>> activate(@RequestParam String token){
		return merchantService.activate(token);
	}
}