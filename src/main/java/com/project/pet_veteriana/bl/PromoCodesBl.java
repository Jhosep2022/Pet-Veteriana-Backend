package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.PromoCodesDto;
import com.project.pet_veteriana.entity.PromoCodes;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.repository.PromoCodesRepository;
import com.project.pet_veteriana.repository.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromoCodesBl {

    @Autowired
    private PromoCodesRepository promoCodesRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    // Crear un nuevo código promocional
    public PromoCodesDto createPromoCode(PromoCodesDto promoCodesDto) {
        Optional<Providers> providerOptional = providersRepository.findById(promoCodesDto.getProviderId());
        if (providerOptional.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }

        PromoCodes promoCode = new PromoCodes();
        promoCode.setCode(promoCodesDto.getCode());
        promoCode.setDescription(promoCodesDto.getDescription());
        promoCode.setDiscountType(promoCodesDto.getDiscountType());
        promoCode.setDiscountValue(promoCodesDto.getDiscountValue());
        promoCode.setMaxUses(promoCodesDto.getMaxUses());
        promoCode.setCurrentUses(0); // Inicialmente en 0
        promoCode.setStartDate(promoCodesDto.getStartDate());
        promoCode.setEndDate(promoCodesDto.getEndDate());
        promoCode.setActive(promoCodesDto.getActive());
        promoCode.setProvider(providerOptional.get());
        promoCode.setCreatedAt(LocalDateTime.now());

        PromoCodes savedPromoCode = promoCodesRepository.save(promoCode);
        return convertToDto(savedPromoCode);
    }

    // Obtener todos los códigos promocionales
    public List<PromoCodesDto> getAllPromoCodes() {
        List<PromoCodes> promoCodes = promoCodesRepository.findAll();
        return promoCodes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener código promocional por ID
    public PromoCodesDto getPromoCodeById(Integer id) {
        Optional<PromoCodes> promoCodeOptional = promoCodesRepository.findById(id);
        if (promoCodeOptional.isEmpty()) {
            throw new IllegalArgumentException("Código promocional no encontrado");
        }
        return convertToDto(promoCodeOptional.get());
    }

    // Actualizar un código promocional
    public PromoCodesDto updatePromoCode(Integer id, PromoCodesDto promoCodesDto) {
        Optional<PromoCodes> promoCodeOptional = promoCodesRepository.findById(id);
        if (promoCodeOptional.isEmpty()) {
            throw new IllegalArgumentException("Código promocional no encontrado");
        }

        PromoCodes promoCode = promoCodeOptional.get();
        promoCode.setCode(promoCodesDto.getCode());
        promoCode.setDescription(promoCodesDto.getDescription());
        promoCode.setDiscountType(promoCodesDto.getDiscountType());
        promoCode.setDiscountValue(promoCodesDto.getDiscountValue());
        promoCode.setMaxUses(promoCodesDto.getMaxUses());
        promoCode.setCurrentUses(promoCodesDto.getCurrentUses());
        promoCode.setStartDate(promoCodesDto.getStartDate());
        promoCode.setEndDate(promoCodesDto.getEndDate());
        promoCode.setActive(promoCodesDto.getActive());
        promoCode.setCreatedAt(promoCodesDto.getCreatedAt());

        PromoCodes updatedPromoCode = promoCodesRepository.save(promoCode);
        return convertToDto(updatedPromoCode);
    }

    // Eliminar un código promocional
    public boolean deletePromoCode(Integer id) {
        if (promoCodesRepository.existsById(id)) {
            promoCodesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private PromoCodesDto convertToDto(PromoCodes promoCode) {
        return new PromoCodesDto(
                promoCode.getPromoId(),
                promoCode.getCode(),
                promoCode.getDescription(),
                promoCode.getDiscountType(),
                promoCode.getDiscountValue(),
                promoCode.getMaxUses(),
                promoCode.getCurrentUses(),
                promoCode.getStartDate(),
                promoCode.getEndDate(),
                promoCode.getActive(),
                promoCode.getProvider().getProviderId(),
                promoCode.getCreatedAt()
        );
    }
}
