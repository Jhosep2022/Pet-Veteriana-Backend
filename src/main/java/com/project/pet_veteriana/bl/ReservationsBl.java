package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ReservationsDto;
import com.project.pet_veteriana.entity.Reservations;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.ReservationsRepository;
import com.project.pet_veteriana.repository.ServicesRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationsBl {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    public ReservationsDto createReservation(ReservationsDto dto) {
        Optional<Users> userOptional = usersRepository.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Optional<Services> serviceOptional = servicesRepository.findById(dto.getServiceId());
        if (serviceOptional.isEmpty()) {
            throw new IllegalArgumentException("Servicio no encontrado");
        }

        Reservations reservation = new Reservations();
        reservation.setUser(userOptional.get());
        reservation.setService(serviceOptional.get());
        reservation.setDate(dto.getDate());
        reservation.setStatus(dto.getStatus());
        reservation.setCreatedAt(LocalDateTime.now());

        Reservations savedReservation = reservationsRepository.save(reservation);
        return convertToDto(savedReservation);
    }

    public List<ReservationsDto> getAllReservations() {
        List<Reservations> reservations = reservationsRepository.findAll();
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ReservationsDto getReservationById(Integer id) {
        Optional<Reservations> reservationOptional = reservationsRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("Reservación no encontrada");
        }
        return convertToDto(reservationOptional.get());
    }

    public ReservationsDto updateReservation(Integer id, ReservationsDto dto) {
        Optional<Reservations> reservationOptional = reservationsRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("Reservación no encontrada");
        }

        Reservations reservation = reservationOptional.get();

        Optional<Users> userOptional = usersRepository.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Optional<Services> serviceOptional = servicesRepository.findById(dto.getServiceId());
        if (serviceOptional.isEmpty()) {
            throw new IllegalArgumentException("Servicio no encontrado");
        }

        reservation.setUser(userOptional.get());
        reservation.setService(serviceOptional.get());
        reservation.setDate(dto.getDate());
        reservation.setStatus(dto.getStatus());

        Reservations updatedReservation = reservationsRepository.save(reservation);
        return convertToDto(updatedReservation);
    }

    public boolean deleteReservation(Integer id) {
        if (reservationsRepository.existsById(id)) {
            reservationsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ReservationsDto convertToDto(Reservations reservation) {
        return new ReservationsDto(
                reservation.getReservationId(),
                reservation.getUser().getUserId(),
                reservation.getService().getServiceId(),
                reservation.getDate(),
                reservation.getStatus(),
                reservation.getCreatedAt()
        );
    }
}
