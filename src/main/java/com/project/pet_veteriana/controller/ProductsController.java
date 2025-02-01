package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.bl.ProductsBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ProductsDto;
import com.project.pet_veteriana.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductsBl productsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<ProductsDto>> createProductWithImage(
            HttpServletRequest request,
            @RequestParam("product") String productDtoJson,
            @RequestParam("file") MultipartFile file) throws Exception {

        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Creating new product with image");
        ObjectMapper objectMapper = new ObjectMapper();
        ProductsDto productsDto = objectMapper.readValue(productDtoJson, ProductsDto.class);

        ProductsDto createdProduct = productsBl.createProductWithImage(productsDto, file);
        ResponseDto<ProductsDto> response = ResponseDto.success(createdProduct, "Product created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductsDto>>> getAllProducts(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching all products");
        List<ProductsDto> products = productsBl.getAllProducts();
        ResponseDto<List<ProductsDto>> response = ResponseDto.success(products, "Products fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductsDto>> getProductById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Integer id) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching product with ID: {}", id);
        Optional<ProductsDto> product = productsBl.getProductById(id);
        if (product.isPresent()) {
            ResponseDto<ProductsDto> response = ResponseDto.success(product.get(), "Product found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDto<ProductsDto> response = ResponseDto.error("Product not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductsDto>> updateProductWithImage(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Integer id,
            @RequestParam("product") String productDtoJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating product with ID: {}", id);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductsDto productsDto = objectMapper.readValue(productDtoJson, ProductsDto.class);

        Optional<ProductsDto> updatedProduct = productsBl.updateProductWithImage(id, productsDto, file);
        if (updatedProduct.isPresent()) {
            ResponseDto<ProductsDto> response = ResponseDto.success(updatedProduct.get(), "Product updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDto<ProductsDto> response = ResponseDto.error("Product not found for update", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Integer id) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting product with ID: {}", id);
        boolean deleted = productsBl.deleteProduct(id);
        if (deleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Product deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseDto<Void> response = ResponseDto.error("Product not found for deletion", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener productos recientes
    @GetMapping("/recent")
    public ResponseEntity<ResponseDto<List<ProductsDto>>> getRecentProducts(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ProductsDto> products = productsBl.getRecentProducts();
        return new ResponseEntity<>(ResponseDto.success(products, "Recent products fetched successfully"), HttpStatus.OK);
    }

    // Obtener productos en oferta
    @GetMapping("/on-offer")
    public ResponseEntity<ResponseDto<List<ProductsDto>>> getProductsOnOffer(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ProductsDto> products = productsBl.getProductsOnOffer();
        return new ResponseEntity<>(ResponseDto.success(products, "Products on offer fetched successfully"), HttpStatus.OK);
    }

    // Obtener productos por proveedor
    @GetMapping("/by-provider/{providerId}")
    public ResponseEntity<ResponseDto<List<ProductsDto>>> getProductsByProvider(
            @PathVariable Integer providerId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ProductsDto> products = productsBl.getProductsByProvider(providerId);
        return new ResponseEntity<>(ResponseDto.success(products, "Products by provider fetched successfully"), HttpStatus.OK);
    }
}
