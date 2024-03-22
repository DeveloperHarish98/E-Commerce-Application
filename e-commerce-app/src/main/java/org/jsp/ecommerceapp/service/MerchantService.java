package org.jsp.ecommerceapp.service;

import java.util.List;
import java.util.Optional;
import org.jsp.ecommerceapp.dao.MerchantDao;
import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.jsp.ecommerceapp.exception.IdNotFoundException;
import org.jsp.ecommerceapp.exception.InvalidCredentialException;
import org.jsp.ecommerceapp.exception.MerchantNotFoundException;
import org.jsp.ecommerceapp.model.Merchant;
import org.jsp.ecommerceapp.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;

@Service
public class MerchantService {

	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private ECommerceAppEmailService emailService;

	public ResponseEntity<ResponseStructure<Merchant>> saveMerchant(Merchant merchant, HttpServletRequest request) {
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		merchant.setStatus(AccountStatus.IN_ACTIVE.toString());
		merchant.setToken(RandomString.make(45));
		structure.setData(merchantDao.saveMerchant(merchant));
		String message = emailService.sendWelcomeMail(merchant, request);
		structure.setMessage("Merchant saved" + "," + message);
		structure.setStatuscode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Merchant>> updateMerchant(Merchant merchant) {
		Optional<Merchant> recMerchant = merchantDao.findById(merchant.getId());
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		if (recMerchant.isPresent()) {
			Merchant dbmerchant = new Merchant();
			dbmerchant.setName(merchant.getName());
			dbmerchant.setPhone(merchant.getPhone());
			dbmerchant.setGst_number(merchant.getGst_number());
			dbmerchant.setEmail(merchant.getEmail());
			dbmerchant.setPassword(merchant.getPassword());
			merchant = merchantDao.saveMerchant(merchant);
		}
		structure.setData(merchantDao.saveMerchant(merchant));
		structure.setMessage("Merchant Updated");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<Merchant>> findById(int id) {
		Optional<Merchant> recMerchant = merchantDao.findById(id);
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		if (recMerchant.isPresent()) {
			structure.setData(recMerchant.get());
			structure.setMessage("Merchant Found");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<Merchant>> verifyByEmail(String email, String password) {
		Optional<Merchant> recMerchant = merchantDao.verifyByEmail(email, password);
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		if (recMerchant.isPresent()) {
			Merchant m = recMerchant.get();
			if(m.getStatus().equals(AccountStatus.IN_ACTIVE.toString())) {
				throw new IllegalStateException("This account is not Activated, please check your mail");
			}
			structure.setData(recMerchant.get());
			structure.setMessage("Merchant email verified");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialException();
	}

	public ResponseEntity<ResponseStructure<Merchant>> verifyByPhone(long phone, String password) {
		Optional<Merchant> recMerchant = merchantDao.verifyByPhone(phone, password);
		ResponseStructure<Merchant> structure = new ResponseStructure<>();
		if (recMerchant.isPresent()) {
			structure.setData(recMerchant.get());
			structure.setMessage("Merchant phone number verified");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Merchant>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialException();

	}

	public ResponseEntity<ResponseStructure<List<Merchant>>> findAll() {
		ResponseStructure<List<Merchant>> structure = new ResponseStructure<>();
		structure.setData(merchantDao.findAll());
		structure.setMessage("Merchants Found");
		structure.setStatuscode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<List<Merchant>>>(structure, HttpStatus.FOUND);
		
	}
	
	public ResponseEntity<ResponseStructure<String>> activate(String token){
		Optional<Merchant> recMerchant = merchantDao.findByToken(token);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if(recMerchant.isPresent()) {
			Merchant merchant = recMerchant.get();
			merchant.setStatus(AccountStatus.ACTIVE.toString());
			merchant.setToken(null);
			merchantDao.saveMerchant(merchant);
			structure.setData("Merchant Found");
			structure.setMessage("Account Verified and Activated");
			structure.setStatuscode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		throw new MerchantNotFoundException();
	}
	}
