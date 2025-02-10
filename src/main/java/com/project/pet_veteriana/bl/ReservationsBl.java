package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ReservationsDto;
import com.project.pet_veteriana.entity.Reservations;
import com.project.pet_veteriana.entity.ServiceAvailability;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.ReservationsRepository;
import com.project.pet_veteriana.repository.ServiceAvailabilityRepository;
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

    @Autowired
    private ServiceAvailabilityRepository availabilityRepository;

    public ReservationsDto createReservation(ReservationsDto dto) {
        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Services service = servicesRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        ServiceAvailability availability = availabilityRepository.findById(dto.getAvailabilityId())
                .orElseThrow(() -> new IllegalArgumentException("Horario no disponible"));

        // Verificar si ya hay una reserva en ese horario
        boolean isReserved = availability.getIsReserved();
        if (isReserved) {
            throw new IllegalArgumentException("Este horario ya está reservado");
        }

        // Marcar la disponibilidad como reservada
        availability.setIsReserved(true);
        availabilityRepository.save(availability);

        Reservations reservation = new Reservations();
        reservation.setUser(user);
        reservation.setService(service);
        reservation.setAvailability(availability);
        reservation.setDate(dto.getDate());
        reservation.setStatus("PENDIENTE"); // Nuevo estado por defecto
        reservation.setCreatedAt(LocalDateTime.now());

        Reservations savedReservation = reservationsRepository.save(reservation);
        return convertToDto(savedReservation);
    }

    public List<ReservationsDto> getAllReservations() {
        List<Reservations> reservations = reservationsRepository.findAll();
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ReservationsDto getReservationById(Integer id) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));
        return convertToDto(reservation);
    }

    public ReservationsDto updateReservation(Integer id, ReservationsDto dto) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));

        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Services service = servicesRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        ServiceAvailability availability = availabilityRepository.findById(dto.getAvailabilityId())
                .orElseThrow(() -> new IllegalArgumentException("Horario no disponible"));

        // Actualizar los datos de la reservación
        reservation.setUser(user);
        reservation.setService(service);
        reservation.setAvailability(availability);
        reservation.setDate(dto.getDate());
        reservation.setStatus(dto.getStatus());

        Reservations updatedReservation = reservationsRepository.save(reservation);
        return convertToDto(updatedReservation);
    }

    public boolean deleteReservation(Integer id) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));

        if (!"PENDIENTE".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("Solo se pueden eliminar reservaciones pendientes");
        }

        // Liberar el horario reservado
        ServiceAvailability availability = reservation.getAvailability();
        availability.setIsReserved(false);
        availabilityRepository.save(availability);

        reservationsRepository.delete(reservation);
        return true;
    }

    public ReservationsDto updateReservationStatus(Integer id, String status) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));

        // Validar si el estado es válido
        List<String> validStatuses = List.of("PENDIENTE", "ATENDIDO", "CANCELADO");
        if (!validStatuses.contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Estado no válido. Los valores permitidos son: PENDIENTE, ATENDIDO, CANCELADO");
        }

        reservation.setStatus(status.toUpperCase());
        Reservations updatedReservation = reservationsRepository.save(reservation);
        return convertToDto(updatedReservation);
    }


    private ReservationsDto convertToDto(Reservations reservation) {
        return new ReservationsDto(
                reservation.getReservationId(),
                reservation.getUser().getUserId(),
                reservation.getService().getServiceId(),
                reservation.getAvailability().getAvailabilityId(),
                reservation.getDate(),
                reservation.getStatus(),
                reservation.getCreatedAt()
        );
    }
}
