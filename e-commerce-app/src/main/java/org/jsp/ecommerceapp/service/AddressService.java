package org.jsp.ecommerceapp.service;

import java.util.List;
import java.util.Optional;

import org.jsp.ecommerceapp.dao.AddressDao;
import org.jsp.ecommerceapp.dao.UserDao;
import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.jsp.ecommerceapp.exception.AddressNotFoundException;
import org.jsp.ecommerceapp.exception.InvalidCredentialException;
import org.jsp.ecommerceapp.exception.UserNotFoundException;
import org.jsp.ecommerceapp.model.Address;
import org.jsp.ecommerceapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private UserDao userDao;

	public ResponseEntity<ResponseStructure<Address>> saveAddress(Address address, int user_id) {
		Optional<User> recUser = userDao.findById(user_id);
		ResponseStructure<Address> structure = new ResponseStructure<>();
		if (recUser.isPresent()) {
			User user = recUser.get();
			user.getAddresses().add(address);
			address.setUser(user);
			structure.setData(addressDao.saveAddress(address));
			structure.setMessage("Address Added Successfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Address>>(structure, HttpStatus.CREATED);
		}
		throw new UserNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Address>> updateAddress(Address address) {
		ResponseStructure<Address> structure = new ResponseStructure<>();
		Optional<Address> recAddress = addressDao.findById(address.getId());
		if (recAddress.isPresent()) {
			throw new AddressNotFoundException("Address is invaild.. Try again");
		
		}
			Address dbAddress = recAddress.get();
			dbAddress.setId(address.getId());
			dbAddress.setLandmark(address.getLandmark());
			dbAddress.setBuildingname(address.getBuildingname());
			dbAddress.setCity(address.getCity());
			dbAddress.setCountry(address.getCountry());
			dbAddress.setHousenumber(address.getHousenumber());
			dbAddress.setPincode(address.getPincode());
			dbAddress.setState(address.getState());
			structure.setMessage("Address Updated Successfully");
			structure.setData(addressDao.saveAddress(dbAddress));
			structure.setStatuscode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Address>>(structure, HttpStatus.ACCEPTED);
		}
		

	public ResponseEntity<ResponseStructure<List<Address>>> findByUserId(int user_id) {
		ResponseStructure<List<Address>> structure = new ResponseStructure<>();
		List<Address> recAddresss = addressDao.findByUserId(user_id);
		if (recAddresss.isEmpty()) {
			throw new AddressNotFoundException("No address found, please check user id");
		}
			structure.setData(recAddresss);
			structure.setMessage("Address Found by user Id");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Address>>>(structure, HttpStatus.OK);
		}
		

	public ResponseEntity<ResponseStructure<List<Address>>> findByCountry(String country) {
		List<Address> recAddress = addressDao.findByCountry(country);
		ResponseStructure<List<Address>> structure = new ResponseStructure<>();
		if (recAddress.isEmpty()) {
			throw new InvalidCredentialException();
		}
		structure.setMessage("Adress Found through Country");
		structure.setData(recAddress);
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Address>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Address>>> findByPincode(long pincode) {
		List<Address> recAddress = addressDao.findByPincode(pincode);
		ResponseStructure<List<Address>> structure = new ResponseStructure<>();
		if (recAddress.isEmpty()) {
			throw new InvalidCredentialException();
		}
		structure.setMessage("Adress Found");
		structure.setData(recAddress);
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Address>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Address>>> findByBuildingName(String buildingname) {
		List<Address> recAddress = addressDao.findByBuildingname(buildingname);
		ResponseStructure<List<Address>> structure = new ResponseStructure<>();
		if (recAddress.isEmpty()) {
			throw new InvalidCredentialException();

		}
		structure.setMessage("building Name Found");
		structure.setData(recAddress);
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Address>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Address>>> findByLandmark(String landmark) {
		List<Address> recAddress = addressDao.findByLandmark(landmark);
		ResponseStructure<List<Address>> structure = new ResponseStructure<>();
		if (recAddress.isEmpty()) {
			throw new InvalidCredentialException();

		}
		structure.setMessage("Landmark Found");
		structure.setData(recAddress);
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Address>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Address>> findById(int id) {
		ResponseStructure<Address> structure = new ResponseStructure<>();
		Optional<Address> recAddress = addressDao.findById(id);
		if (recAddress.isEmpty()) {
			throw new AddressNotFoundException("Invalid Address Id");
		}
		structure.setData(recAddress.get());
		structure.setMessage("Address Found");
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Address>>(structure, HttpStatus.OK);
	}
}
