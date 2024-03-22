package org.jsp.ecommerceapp.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.ecommerceapp.model.Merchant;
import org.jsp.ecommerceapp.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantDao {

	@Autowired
	private MerchantRepository merchantRepository;

	public Merchant saveMerchant(Merchant merchant) {
		return merchantRepository.save(merchant);
	}

	public Optional<Merchant> findById(int id) {
		return merchantRepository.findById(id);
	}

	public Optional<Merchant> verifyByEmail(String email, String password) {
		return merchantRepository.verifyByEmail(email, password);
	}

	public Optional<Merchant> verifyByPhone(long phone, String password) {
		return merchantRepository.verifyByPhone(phone, password);
	}


	public List<Merchant> findAll() {
		return merchantRepository.findAll();
	}
	
	public Optional<Merchant> findByToken(String token){
		return merchantRepository.findByToken(token);
	}

}
