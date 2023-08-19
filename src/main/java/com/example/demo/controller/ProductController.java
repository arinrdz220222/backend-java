package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@RestController
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	private List<Product> data= new ArrayList<Product>();

	@GetMapping("/product")
	public List<Product> getProduct() {
		return productRepository.findAll();
	}
	@GetMapping("/product/{id}")
	public Optional<Product> getProduct(@PathVariable Integer id) {
		Optional<Product> product =productRepository.findById(id);
		return product;
	}
	@GetMapping("/search_text")
	public List<Product> getProduct(@RequestParam String text) {
		List<Product> products = new ArrayList<Product>();
		products.add((Product) productRepository.findByProductNameContaining(text));
		products.add((Product) productRepository.findByProductDetailContaining(text));
		
		return products;
	}

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product body) {
		productRepository.save(body);
		return body;
	}
	
	@PutMapping("/product/{id}")
	public Optional<Product> updatProduct(@PathVariable Integer id,@RequestBody Product body) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			product.get().setProductAmount(body.getProductAmount());
			product.get().setProductDetail(body.getProductDetail());
			product.get().setProductName(body.getProductName());
			product.get().setProductPrice(body.getProductPrice());
			productRepository.save(product.get());
		}else {
			return null;
		}
		return product;
	}
	
	@DeleteMapping("/product/{id}")
	public String deleteProduct(@PathVariable Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return "Deleted!";
		}else {
			return "Not found!";
		}
		
	}

}