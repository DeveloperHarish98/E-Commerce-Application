package org.jsp.ecommerceapp.service;

import java.util.List;
import java.util.Optional;


import org.jsp.ecommerceapp.dao.MerchantDao;
import org.jsp.ecommerceapp.dao.ProductDao;
import org.jsp.ecommerceapp.dao.UserDao;
import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.jsp.ecommerceapp.exception.IdNotFoundException;
import org.jsp.ecommerceapp.exception.InvalidCredentialException;
import org.jsp.ecommerceapp.exception.ProductNotFoundException;
import org.jsp.ecommerceapp.model.Merchant;
import org.jsp.ecommerceapp.model.Product;
import org.jsp.ecommerceapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private UserDao userDao;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(Product product, int merchant_id) {
		Optional<Merchant> recMechant = merchantDao.findById(merchant_id);
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recMechant.isPresent()) {
			Merchant merchant = recMechant.get();
			merchant.getProducts().add(product);
			product.setMerchant(merchant);
			structure.setData(productDao.saveProduct(product));
			structure.setMessage("Product Added");
			structure.setStatuscode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.CREATED);
		}
		throw new ProductNotFoundException("We cannot add Product as Merchant Id is Invalid");
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findProductsByMerchantId(int merchant_id) {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		List<Product> products = productDao.findByMerchantId(merchant_id);
		if (products.isEmpty()) {
			throw new ProductNotFoundException("No Products Found for entered Merchant Id");
		}
		structure.setData(products);
		structure.setMessage("List of Products for Given Merchant Id");
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Product>> findById(int id) {
		Optional<Product> recProduct = productDao.findById(id);
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recProduct.isPresent()) {
			structure.setData(recProduct.get());
			structure.setMessage("Product Found");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findAll() {
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		structure.setData(productDao.findAll());
		structure.setMessage("Products Found");
		structure.setStatuscode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findByBrand(String brand) {
		List<Product> recProduct = productDao.findByBrand(brand);
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		if (recProduct.isEmpty()) {
			throw new ProductNotFoundException("No Products Found for entered Brand");
		} else {
			structure.setData(recProduct);
			structure.setMessage("Product Found By Brand");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseStructure<List<Product>>> findByCategory(String category) {
		List<Product> recProduct = productDao.findByCategory(category);
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		if (recProduct.isEmpty()) {
			throw new ProductNotFoundException("No Products Found for entered category");
		} else {
			structure.setData(recProduct);
			structure.setMessage("Product Found By category");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);
		}
	}

	public ResponseEntity<ResponseStructure<Product>> updateProduct(Product product) {
		Optional<Product> recProduct = productDao.findById(product.getId());
		ResponseStructure<Product> structure = new ResponseStructure<>();
		if (recProduct.isPresent()) {
			Product dbproduct = new Product();
			dbproduct.setName(product.getName());
			dbproduct.setBrand(product.getBrand());
			dbproduct.setCategory(product.getCategory());
			dbproduct.setDescription(product.getDescription());
			dbproduct.setCost(product.getCost());
			dbproduct.setImage_url(product.getImage_url());
			product = productDao.saveProduct(product);
		}
		structure.setData(productDao.saveProduct(product));
		structure.setMessage("product Updated");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<Product>>(structure, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<List<Product>>> findByName(String name) {
		List<Product> recProduct = productDao.findByName(name);
		ResponseStructure<List<Product>> structure = new ResponseStructure<>();
		if (recProduct.isEmpty()) {
			throw new ProductNotFoundException("No Products Found for entered Name");
		} else {
			structure.setData(recProduct);
			structure.setMessage("Product Found By Name");
			structure.setStatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Product>>>(structure, HttpStatus.OK);

		}
	}
	public ResponseEntity<ResponseStructure<String>> deleteById(int id) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<Product> recProduct = productDao.findById(id);
		if (recProduct.isEmpty())
			throw new ProductNotFoundException("Invalid Prodcut Id");
		productDao.delete(id);
		structure.setData("Product Found");
		structure.setMessage("Product ddeleted");
		structure.setStatuscode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<String>> addToCart (int user_id,int product_id){
		Optional<Product> recProduct = productDao.findById(product_id);
		Optional<User> recUser = userDao.findById(user_id);
		if(recProduct.isEmpty() || recUser.isEmpty())
			throw new IllegalArgumentException("Invalid User Id or Product Id");
		
		recUser.get().getCart().add(recProduct.get());
		userDao.saveUser(recUser.get());
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("User and Product Found");
		structure.setMessage("Added Product to cart");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<ResponseStructure<String>> addToWishList (int user_id,int product_id){
		Optional<Product> recProduct = productDao.findById(product_id);
		Optional<User> recUser = userDao.findById(user_id);
		if(recProduct.isEmpty() || recUser.isEmpty())
			throw new IllegalArgumentException("Invalid User Id or Product Id");
		
		recUser.get().getWishList().add(recProduct.get());
		userDao.saveUser(recUser.get());
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("User and Product Found");
		structure.setMessage("Added Product to WishList");
		structure.setStatuscode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
	}
}