package org.jsp.ecommerceapp.controller;

import java.util.List;

import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.jsp.ecommerceapp.model.Address;
import org.jsp.ecommerceapp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {
	@Autowired
	public AddressService addressService;

	@PostMapping("/{user_id}")
	public ResponseEntity<ResponseStructure<Address>> saveAdress(@RequestBody Address address,
			@PathVariable int user_id) {
		return addressService.saveAddress(address, user_id);
	}
	
	@GetMapping("/{user_id}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByUserId(@PathVariable int user_id) {
		return addressService.findByUserId(user_id);
	}
	
	@GetMapping("/find-by-id/{id}")
	public ResponseEntity<ResponseStructure<Address>> findById(@PathVariable int id) {
		return addressService.findById(id);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Address>> updateAddress(@RequestBody Address address) {
		return addressService.updateAddress(address);
	}
	
	
	@GetMapping("/by-country/{country}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByCountry(@PathVariable String country) {
		return addressService.findByCountry(country);
	}

	@GetMapping("/by-pincode/{pincode}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByPincode(@PathVariable long pincode) {
		return addressService.findByPincode(pincode);
	}

	@GetMapping("/by-landmark/{landmark}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByLandmark(@PathVariable String landmark) {
		return addressService.findByLandmark(landmark);
	}

	@GetMapping("/by-buildingname/{buildingname}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByBuildingName(@PathVariable String buildingname) {
		return addressService.findByBuildingName(buildingname);
	}
}
