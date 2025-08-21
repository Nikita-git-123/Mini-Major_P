package com.example.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ProductCategoryDto;
import com.example.dto.ProductDto;
import com.example.response.ApiResponse;
import com.example.service.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<ProductCategoryDto>>> productCategories() {

		List<ProductCategoryDto> allCategories = productService.findAllCategories();

		ApiResponse<List<ProductCategoryDto>> response = new ApiResponse<>();

		if (!allCategories.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetched categories successfully.");
			response.setData(allCategories);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed to fetch categories.");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/products/{categoryId}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> products(@PathVariable Long categoryId) {

		List<ProductDto> products = productService.findProductsByCategoryId(categoryId);

		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		if (!products.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetched products successfully.");
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed to fetch products.");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/productsByName/{name}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> products(@PathVariable String name) {

		List<ProductDto> products = productService.findByProductName(name);

		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		if (!products.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetched products successfully.");
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed to fetch products.");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> product(@PathVariable Long productId) {

		ProductDto product = productService.findByProductId(productId);

		ApiResponse<ProductDto> response = new ApiResponse<>();

		if (product != null) {
			response.setStatus(200);
			response.setMessage("Fetched product successfully.");
			response.setData(product);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed to fetch product.");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
