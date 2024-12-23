package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.ProvidersRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import com.project.pet_veteriana.config.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Crear un nuevo Provider
    public ProvidersDto createProvider(String token, ProvidersDto providersDto) {
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            throw new RuntimeException("Unauthorized access");
        }

        Optional<Users> user = usersRepository.findById(providersDto.getUserId());

        if (user.isPresent()) {
            Providers provider = new Providers();
            provider.setUser(user.get());
            provider.setRating(providersDto.getRating());
            provider.setCreatedAt(LocalDateTime.now());
            provider.setStatus(providersDto.getStatus());  // No hay conversión, usamos el Boolean directamente

            provider = providersRepository.save(provider);

            logger.info("Provider created for user: {}", user.get().getEmail());

            return new ProvidersDto(
                    provider.getProviderId(),
                    provider.getUser().getUserId(),
                    provider.getRating(),
                    provider.getCreatedAt(),
                    provider.getStatus()  // Usamos el Boolean directamente
            );
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Obtener todos los Providers activos
    public List<ProvidersDto> getAllProviders() {
        // Usamos el nuevo método findByStatusTrue para obtener solo los proveedores activos
        List<Providers> providers = providersRepository.findByStatusTrue();
        return providers.stream().map(provider -> new ProvidersDto(
                provider.getProviderId(),
                provider.getUser().getUserId(),
                provider.getRating(),
                provider.getCreatedAt(),
                provider.getStatus()  // Usamos el Boolean directamente
        )).toList();
    }

    // Obtener Provider por ID
    public ProvidersDto getProviderById(Integer id) {
        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            Providers prov = provider.get();
            return new ProvidersDto(
                    prov.getProviderId(),
                    prov.getUser().getUserId(),
                    prov.getRating(),
                    prov.getCreatedAt(),
                    prov.getStatus()  // Usamos el Boolean directamente
            );
        } else {
            throw new RuntimeException("Provider not found");
        }
    }

    // Actualizar Provider
    public ProvidersDto updateProvider(Integer id, ProvidersDto providersDto) {
        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            Providers prov = provider.get();
            prov.setRating(providersDto.getRating());
            prov.setStatus(providersDto.getStatus());  // Usamos el Boolean directamente

            prov = providersRepository.save(prov);

            logger.info("Provider updated for ID: {}", id);

            return new ProvidersDto(
                    prov.getProviderId(),
                    prov.getUser().getUserId(),
                    prov.getRating(),
                    prov.getCreatedAt(),
                    prov.getStatus()  // Usamos el Boolean directamente
            );
        } else {
            throw new RuntimeException("Provider not found");
        }
    }

    // Eliminar Provider (Borrado lógico)
    public void deleteProvider(Integer id) {
        Optional<Providers> provider = providersRepository.findById(id);
        if (provider.isPresent()) {
            Providers prov = provider.get();
            prov.setStatus(false);  // Cambiar el status a false en lugar de eliminar físicamente

            providersRepository.save(prov);
            logger.info("Provider marked as deleted for ID: {}", id);
        } else {
            throw new RuntimeException("Provider not found");
        }
    }
}
