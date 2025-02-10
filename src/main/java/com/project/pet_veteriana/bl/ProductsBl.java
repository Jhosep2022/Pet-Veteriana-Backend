package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.ProductsDto;
import com.project.pet_veteriana.entity.*;
import com.project.pet_veteriana.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    private OffersProductsRepository offersProductsRepository;

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
        product.setName(productsDto.getName());
        product.setDescription(productsDto.getDescription());
        product.setPrice(productsDto.getPrice());
        product.setStock(productsDto.getStock());
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

        product.setName(productsDto.getName());
        product.setDescription(productsDto.getDescription());
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

    // Obtener productos recientes (últimos 10 creados)
    public List<ProductsDto> getRecentProducts() {
        return productsRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener productos en oferta
    public List<ProductsDto> getProductsOnOffer() {
        LocalDateTime now = LocalDateTime.now();
        return offersProductsRepository.findByOffer_StartDateBeforeAndOffer_EndDateAfterAndOffer_IsActiveTrue(now, now)
                .stream()
                .map(offersProduct -> convertToDto(offersProduct.getProduct()))
                .collect(Collectors.toList());
    }

    // Obtener productos por proveedor
    public List<ProductsDto> getProductsByProvider(Integer providerId) {
        Providers provider = providersRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        return productsRepository.findByProvider(provider)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener productos por usuario (buscando el proveedor correspondiente)
    public List<ProductsDto> getProductsByUserId(Integer userId) {
        // Buscar proveedor asociado al usuario
        Providers provider = providersRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("No provider found for userId: " + userId));

        // Obtener los productos de ese proveedor
        return productsRepository.findByProvider(provider)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener productos por categoría
    public List<ProductsDto> getProductsByCategory(Integer categoryId) {
        // Buscar la categoría en la base de datos
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        // Buscar productos de la categoría
        return productsRepository.findByCategory(category)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }




    // Convertir entidad a DTO
    private ProductsDto convertToDto(Products product) {
        String imageUrl = null;
        if (product.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(product.getImage().getFileName());
        }

        return new ProductsDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getStatus(),
                product.getProvider().getProviderId(),
                product.getCategory().getCategoryId(),
                product.getImage() != null ? product.getImage().getImageId() : null,
                imageUrl
        );
    }
}
