package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.OffersDto;
import com.project.pet_veteriana.entity.Offers;
import com.project.pet_veteriana.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffersBl {

    @Autowired
    private OffersRepository offersRepository;

    // Crear una nueva oferta
    public OffersDto createOffer(OffersDto offersDto) {
        Offers offer = new Offers();
        offer.setName(offersDto.getName());
        offer.setDescription(offersDto.getDescription());
        offer.setDiscountType(offersDto.getDiscountType());
        offer.setDiscountValue(offersDto.getDiscountValue());
        offer.setActive(offersDto.getActive());
        offer.setStartDate(offersDto.getStartDate());
        offer.setEndDate(offersDto.getEndDate());

        Offers savedOffer = offersRepository.save(offer);
        return convertToDto(savedOffer);
    }

    // Obtener todas las ofertas
    public List<OffersDto> getAllOffers() {
        List<Offers> offers = offersRepository.findAll();
        return offers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener una oferta por ID
    public OffersDto getOfferById(Integer id) {
        Optional<Offers> offerOptional = offersRepository.findById(id);
        if (offerOptional.isEmpty()) {
            throw new IllegalArgumentException("Oferta no encontrada");
        }
        return convertToDto(offerOptional.get());
    }

    // Actualizar una oferta
    public OffersDto updateOffer(Integer id, OffersDto offersDto) {
        Optional<Offers> offerOptional = offersRepository.findById(id);
        if (offerOptional.isEmpty()) {
            throw new IllegalArgumentException("Oferta no encontrada");
        }

        Offers offer = offerOptional.get();
        offer.setName(offersDto.getName());
        offer.setDescription(offersDto.getDescription());
        offer.setDiscountType(offersDto.getDiscountType());
        offer.setDiscountValue(offersDto.getDiscountValue());
        offer.setActive(offersDto.getActive());
        offer.setStartDate(offersDto.getStartDate());
        offer.setEndDate(offersDto.getEndDate());

        Offers updatedOffer = offersRepository.save(offer);
        return convertToDto(updatedOffer);
    }

    // Eliminar una oferta
    public boolean deleteOffer(Integer id) {
        if (offersRepository.existsById(id)) {
            offersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private OffersDto convertToDto(Offers offer) {
        return new OffersDto(
                offer.getOfferId(),
                offer.getName(),
                offer.getDescription(),
                offer.getDiscountType(),
                offer.getDiscountValue(),
                offer.getActive(),
                offer.getStartDate(),
                offer.getEndDate()
        );
    }
}
