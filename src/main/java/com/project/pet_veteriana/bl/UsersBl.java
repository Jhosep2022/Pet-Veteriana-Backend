package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersBl {

    @Autowired
    private UsersRepository usersRepository;

    // Crear un nuevo usuario
    public UsersDto createUser(UsersDto usersDto) {
        Users user = new Users();
        user.setName(usersDto.getName());
        user.setEmail(usersDto.getEmail());
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setPassword(usersDto.getPassword()); // Solo en POST
        user.setLocation(usersDto.getLocation());
        user.setPreferredLanguage(usersDto.getPreferredLanguage());
        user.setLastLogin(usersDto.getLastLogin());
        user.setStatus(usersDto.getStatus());
        // No necesitamos setear createdAt, ya que se gestionará automáticamente en la entidad

        Users savedUser = usersRepository.save(user);
        return mapToDto(savedUser);
    }

    // Obtener todos los usuarios
    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Obtener un usuario por su ID
    public Optional<UsersDto> getUserById(Integer userId) {
        Optional<Users> user = usersRepository.findById(userId);
        return user.map(this::mapToDto);
    }

    // Actualizar un usuario
    public Optional<UsersDto> updateUser(Integer userId, UsersDto usersDto) {
        Optional<Users> existingUser = usersRepository.findById(userId);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            user.setName(usersDto.getName());
            user.setEmail(usersDto.getEmail());
            user.setPhoneNumber(usersDto.getPhoneNumber());
            user.setPassword(usersDto.getPassword()); // Solo en PUT si se requiere cambiar la contraseña
            user.setLocation(usersDto.getLocation());
            user.setPreferredLanguage(usersDto.getPreferredLanguage());
            user.setLastLogin(usersDto.getLastLogin());
            user.setStatus(usersDto.getStatus());
            // No es necesario actualizar createdAt, ya que no cambia

            Users updatedUser = usersRepository.save(user);
            return Optional.of(mapToDto(updatedUser));
        }
        return Optional.empty();
    }

    // Eliminar un usuario
    public boolean deleteUser(Integer userId) {
        Optional<Users> existingUser = usersRepository.findById(userId);
        if (existingUser.isPresent()) {
            usersRepository.delete(existingUser.get());
            return true;
        }
        return false;
    }

    // Mapeo de Users a UsersDto
    private UsersDto mapToDto(Users user) {
        // No incluimos la contraseña en el DTO cuando se retorna en GET
        return new UsersDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                null,  // No incluir la contraseña en la respuesta
                user.getPhoneNumber(),
                user.getLocation(),
                user.getPreferredLanguage(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getStatus(),
                user.getRol().getRolId(),
                user.getImage().getImageId());
    }
}
