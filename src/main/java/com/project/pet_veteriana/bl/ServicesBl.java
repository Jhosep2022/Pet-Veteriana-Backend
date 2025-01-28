package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.ServicesDto;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.repository.ProvidersRepository;
import com.project.pet_veteriana.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesBl {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl; // Manejo de imágenes en MinIO

    // Crear un servicio con imagen
    public ServicesDto createServiceWithImage(ServicesDto servicesDto, MultipartFile file) throws Exception {
        Providers provider = providersRepository.findById(servicesDto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        // Subir imagen a MinIO y guardar los detalles
        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        Services service = new Services();
        service.setServiceName(servicesDto.getServiceName());
        service.setPrice(servicesDto.getPrice());
        service.setDuration(servicesDto.getDuration());
        service.setDescription(servicesDto.getDescription());
        service.setCreatedAt(LocalDateTime.now());
        service.setStatus(servicesDto.getStatus());
        service.setProvider(provider);
        service.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));

        Services savedService = servicesRepository.save(service);
        return mapToDto(savedService);
    }

    // Obtener todos los servicios
    public List<ServicesDto> getAllServices() {
        return servicesRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtener un servicio por ID
    public Optional<ServicesDto> getServiceById(Integer id) {
        return servicesRepository.findById(id)
                .map(this::mapToDto);
    }

    // Actualizar un servicio con imagen
    public Optional<ServicesDto> updateServiceWithImage(Integer id, ServicesDto servicesDto, MultipartFile file) throws Exception {
        Optional<Services> optionalService = servicesRepository.findById(id);

        if (optionalService.isEmpty()) {
            return Optional.empty();
        }

        Services service = optionalService.get();

        if (file != null && !file.isEmpty()) {
            ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
            service.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        }

        Providers provider = providersRepository.findById(servicesDto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        service.setServiceName(servicesDto.getServiceName());
        service.setPrice(servicesDto.getPrice());
        service.setDuration(servicesDto.getDuration());
        service.setDescription(servicesDto.getDescription());
        service.setStatus(servicesDto.getStatus());
        service.setProvider(provider);

        Services updatedService = servicesRepository.save(service);
        return Optional.of(mapToDto(updatedService));
    }

    // Eliminar un servicio
    public boolean deleteService(Integer id) {
        Optional<Services> optionalService = servicesRepository.findById(id);

        if (optionalService.isPresent()) {
            Services service = optionalService.get();
            if (service.getImage() != null) {
                imagesS3Bl.deleteFile(service.getImage().getFileName());
            }
            servicesRepository.delete(service);
            return true;
        }

        return false;
    }

    // Mapear entidad a DTO
    private ServicesDto mapToDto(Services service) {
        String imageUrl = null;
        if (service.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(service.getImage().getFileName());
        }

        return new ServicesDto(
                service.getServiceId(),
                service.getServiceName(),
                service.getPrice(),
                service.getDuration(),
                service.getDescription(),
                service.getCreatedAt(),
                service.getStatus(),
                service.getProvider().getProviderId(),
                service.getImage() != null ? service.getImage().getImageId() : null,
                imageUrl
        );
    }
}
