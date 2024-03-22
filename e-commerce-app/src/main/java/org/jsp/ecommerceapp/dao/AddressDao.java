package org.jsp.ecommerceapp.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.ecommerceapp.model.Address;
import org.jsp.ecommerceapp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AddressDao {
	@Autowired
	private AddressRepository addressRepository;
	
	public Address saveAddress(Address address) {
		return addressRepository.save(address);
	}
	
	public Optional<Address> findById(int id) {
		return addressRepository.findById(id);
	}
	
	public List<Address> findByUserId(int id) {
		return addressRepository.findByUserId(id);
	}
	
	
	public List<Address> findByCountry(String country) {
		return addressRepository.findByCountry(country);
	}
	public List<Address> findByPincode(long pincode) {
		return addressRepository.findByPincode(pincode);
	}
	public List<Address> findByBuildingname(String buildingname) {
		return addressRepository.findByBuildingname(buildingname);
	}
	public List<Address> findByLandmark(String landmark) {
		return addressRepository.findByLandmark(landmark);
	}
	
}
