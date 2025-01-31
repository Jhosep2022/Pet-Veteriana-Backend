package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.repository.ProvidersRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.bl.ImagesS3Bl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ProvidersDto getProviderById(Integer id) {
        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            return mapToDto(provider.get());
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

    // Eliminar Provider (Borrado lógico)
    public void deleteProvider(Integer id) {
        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            Providers prov = provider.get();
            prov.setStatus(false); // Cambiar el status a false en lugar de eliminar físicamente
            providersRepository.save(prov);
            logger.info("Provider marked as deleted for ID: {}", id);
        } else {
            throw new RuntimeException("Provider not found");
        }
    }

    public boolean existsByUserId(Integer userId) {
        return providersRepository.existsByUser_UserId(userId);
    }


    // Mapear entidad a DTO
    private ProvidersDto mapToDto(Providers provider) {
        String imageUrl = null;
        if (provider.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(provider.getImage().getFileName());
        }

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
                provider.getStatus()
        );
    }
}
