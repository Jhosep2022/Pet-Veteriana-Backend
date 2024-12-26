package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.ProductsDto;
import com.project.pet_veteriana.entity.*;
import com.project.pet_veteriana.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsBl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    // Crear un nuevo producto con imagen
    public ProductsDto createProductWithImage(ProductsDto productsDto, MultipartFile file) throws Exception {
        // Validar proveedor
        Optional<Providers> providerOptional = providersRepository.findById(productsDto.getProviderId());
        if (providerOptional.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }

        // Validar categoría
        Optional<Category> categoryOptional = categoryRepository.findById(productsDto.getCategoryId());
        if (categoryOptional.isEmpty()) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }

        // Subir imagen a MinIO
        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        // Crear objeto producto
        Products product = new Products();
        product.setPrice(productsDto.getPrice());
        product.setStock(productsDto.getStock());
        product.setCreatedAt(productsDto.getCreatedAt());
        product.setStatus(productsDto.getStatus());
        product.setProvider(providerOptional.get());
        product.setCategory(categoryOptional.get());
        product.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));

        Products savedProduct = productsRepository.save(product);
        return convertToDto(savedProduct);
    }

    // Obtener todos los productos
    public List<ProductsDto> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener producto por ID
    public Optional<ProductsDto> getProductById(Integer productId) {
        Optional<Products> product = productsRepository.findById(productId);
        return product.map(this::convertToDto);
    }

    // Actualizar un producto con imagen
    public Optional<ProductsDto> updateProductWithImage(Integer productId, ProductsDto productsDto, MultipartFile file) throws Exception {
        Optional<Products> existingProduct = productsRepository.findById(productId);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        Products product = existingProduct.get();

        // Subir nueva imagen si se proporciona
        if (file != null && !file.isEmpty()) {
            ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
            product.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        }

        product.setPrice(productsDto.getPrice());
        product.setStock(productsDto.getStock());
        product.setCreatedAt(productsDto.getCreatedAt());
        product.setStatus(productsDto.getStatus());

        Products updatedProduct = productsRepository.save(product);
        return Optional.of(convertToDto(updatedProduct));
    }

    // Eliminar un producto
    public boolean deleteProduct(Integer productId) {
        Optional<Products> productOptional = productsRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            if (product.getImage() != null) {
                try {
                    imagesS3Bl.deleteFile(product.getImage().getFileName());
                } catch (Exception e) {
                    throw new RuntimeException("Error al eliminar la imagen asociada", e);
                }
            }
            productsRepository.delete(product);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private ProductsDto convertToDto(Products product) {
        return new ProductsDto(
                product.getProductId(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getStatus(),
                product.getProvider().getProviderId(),
                product.getCategory().getCategoryId(),
                product.getImage() != null ? product.getImage().getImageId() : null
        );
    }
}
