package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.entity.*;
import com.project.pet_veteriana.repository.*;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.bl.ImagesS3Bl;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvidersBl {

    private static final Logger logger = LoggerFactory.getLogger(ProvidersBl.class);

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private PromoCodesRepository promoCodesRepository;

    @Autowired
    private SprecialityProvidersRepository sprecialityProvidersRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    // Crear un nuevo Provider con imagen
    public ProvidersDto createProvider(String token, ProvidersDto providersDto, MultipartFile file) throws Exception {
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            throw new RuntimeException("Unauthorized access");
        }

        Optional<Users> user = usersRepository.findById(providersDto.getUserId());

        if (user.isPresent()) {
            // Subir imagen a MinIO y asociarla al proveedor
            ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

            Providers provider = new Providers();
            provider.setUser(user.get());
            provider.setName(providersDto.getName());
            provider.setDescription(providersDto.getDescription());
            provider.setAddress(providersDto.getAddress());
            provider.setRating(providersDto.getRating());
            provider.setCreatedAt(LocalDateTime.now());
            provider.setUpdatedAt(LocalDateTime.now());
            provider.setStatus(providersDto.getStatus());
            provider.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));

            provider = providersRepository.save(provider);

            logger.info("Provider created for user: {}", user.get().getEmail());

            return mapToDto(provider);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Obtener todos los Providers activos
    public List<ProvidersDto> getAllProviders() {
        List<Providers> providers = providersRepository.findByStatusTrue();
        return providers.stream().map(this::mapToDto).toList();
    }

    // Obtener Provider por ID
    public ProvidersDto getProviderById(Integer id, String token) {
        String username = jwtTokenProvider.extractUsername(token);
        Users authenticatedUser = usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            Providers prov = provider.get();

            // Verificar si el usuario autenticado es el dueño del perfil
            if (!prov.getUser().getUserId().equals(authenticatedUser.getUserId())) {
                prov.setReviews(prov.getReviews() + 1); // Solo incrementa si no es el dueño
                providersRepository.save(prov);
            }

            return mapToDto(prov);
        } else {
            throw new RuntimeException("Provider not found");
        }
    }

    // Actualizar Provider con imagen
    public ProvidersDto updateProvider(Integer id, ProvidersDto providersDto, MultipartFile file) throws Exception {
        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            Providers prov = provider.get();

            // Actualizar imagen si se proporciona
            if (file != null && !file.isEmpty()) {
                ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
                prov.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
            }

            prov.setName(providersDto.getName());
            prov.setDescription(providersDto.getDescription());
            prov.setAddress(providersDto.getAddress());
            prov.setRating(providersDto.getRating());
            prov.setUpdatedAt(LocalDateTime.now());
            prov.setStatus(providersDto.getStatus());

            prov = providersRepository.save(prov);

            logger.info("Provider updated for ID: {}", id);

            return mapToDto(prov);
        } else {
            throw new RuntimeException("Provider not found");
        }
    }

    @Transactional
    public void deleteProvider(Integer id) {
        Optional<Providers> providerOptional = providersRepository.findById(id);
        if (providerOptional.isPresent()) {
            Providers provider = providerOptional.get();

            // Eliminar todas las relaciones en la tabla `spreciality_providers`
            sprecialityProvidersRepository.deleteByProvider(provider);

            // Eliminar todos los códigos promocionales asociados a este proveedor
            List<PromoCodes> promoCodes = promoCodesRepository.findByProvider(provider);
            for (PromoCodes promo : promoCodes) {
                promoCodesRepository.delete(promo);
            }

            // Eliminar el proveedor de la base de datos
            providersRepository.delete(provider);

            logger.info("Provider deleted successfully for ID: {}", id);
        } else {
            throw new RuntimeException("Provider not found");
        }
    }


    public boolean existsByUserId(Integer userId) {
        return providersRepository.existsByUser_UserId(userId);
    }

    // Obtener los proveedores con mejor rating (TOP 5)
    public List<ProvidersDto> getTopProviders() {
        return providersRepository.findTop5ByStatusTrueOrderByRatingDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtener los 10 proveedores más recientes
    public List<ProvidersDto> getRecentProviders() {
        return providersRepository.findTop10ByStatusTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtener un proveedor por ID para mostrar su tienda
    public ProvidersDto getProviderStoreById(Integer providerId) {
        Optional<Providers> provider = providersRepository.findById(providerId);
        if (provider.isPresent() && provider.get().getStatus()) {
            return mapToDto(provider.get());
        } else {
            throw new RuntimeException("Provider not found or inactive");
        }
    }

    public ProvidersDto identifyProvider(Integer productId, Integer serviceId) {
        Providers provider = null;

        if (productId != null) {
            Optional<Products> product = productsRepository.findById(productId);
            if (product.isPresent()) {
                provider = product.get().getProvider();
            }
        }

        if (serviceId != null && provider == null) {
            Optional<Services> service = servicesRepository.findById(serviceId);
            if (service.isPresent()) {
                provider = service.get().getProvider();
            }
        }

        if (provider == null) {
            throw new RuntimeException("Provider not found for given product or service ID");
        }

        return new ProvidersDto(provider.getProviderId(), provider.getUser().getUserId(), provider.getName(),
                provider.getDescription(), provider.getAddress(), null, provider.getRating(),
                provider.getCreatedAt(), provider.getUpdatedAt(), provider.getStatus(), 0, 0, 0);
    }


    // Mapear entidad a DTO
    private ProvidersDto mapToDto(Providers provider) {
        String imageUrl = null;
        if (provider.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(provider.getImage().getFileName());
        }

        // Contar productos y servicios del proveedor
        int productCount = productsRepository.countByProvider(provider);
        int serviceCount = servicesRepository.countByProvider(provider);

        return new ProvidersDto(
                provider.getProviderId(),
                provider.getUser().getUserId(),
                provider.getName(),
                provider.getDescription(),
                provider.getAddress(),
                imageUrl,
                provider.getRating(),
                provider.getCreatedAt(),
                provider.getUpdatedAt(),
                provider.getStatus(),
                productCount,
                serviceCount,
                provider.getReviews()
        );
    }

}
