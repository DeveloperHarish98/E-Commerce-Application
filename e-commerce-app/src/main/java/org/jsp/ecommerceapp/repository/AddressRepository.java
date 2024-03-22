package org.jsp.ecommerceapp.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.ecommerceapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	@Query("Select a from Address a where a.user.id=?1")
	public List<Address> findByUserId(int id);
	
	@Query("Select a from Address a where a.country=?1")
	public List<Address> findByCountry(String country);

	public List<Address> findByPincode(long pincode);
	
	public List<Address> findByBuildingname(String buildingname);
	
	public List<Address> findByLandmark(String landmark);
	
}
