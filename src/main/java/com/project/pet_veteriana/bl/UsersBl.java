package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.entity.Rol;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.RolRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UsersBl {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    @Autowired
    private RolRepository rolRepository;

    // Crear usuario con imagen
    public UsersDto createUserWithImage(UsersDto usersDto, MultipartFile file) throws Exception {
        // Obtener el rol correspondiente usando el rolId
        Rol rol = rolRepository.findById(usersDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Encriptar la contraseña antes de guardarla
        String encryptedPassword = BCrypt.hashpw(usersDto.getPassword(), BCrypt.gensalt());

        // Subir la imagen a MinIO y guardar en la base de datos
        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        // Crear un nuevo usuario con el ID de la imagen y el rol asociado
        Users user = new Users();
        user.setName(usersDto.getName());
        user.setEmail(usersDto.getEmail());
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setPassword(encryptedPassword); // Guardar la contraseña encriptada
        user.setLocation(usersDto.getLocation());
        user.setPreferredLanguage(usersDto.getPreferredLanguage());
        user.setStatus(usersDto.getStatus());
        user.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        user.setRol(rol); // Asignar el rol al usuario

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

    // Actualizar un usuario con imagen
    public Optional<UsersDto> updateUserWithImage(Integer userId, UsersDto usersDto, MultipartFile file) throws Exception {
        Optional<Users> existingUser = usersRepository.findById(userId);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();

            // Subir la nueva imagen si se proporciona
            if (file != null && !file.isEmpty()) {
                ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
                user.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
            }

            user.setName(usersDto.getName());
            user.setEmail(usersDto.getEmail());
            user.setPhoneNumber(usersDto.getPhoneNumber());
            user.setPassword(usersDto.getPassword()); // Encriptar si es necesario
            user.setLocation(usersDto.getLocation());
            user.setPreferredLanguage(usersDto.getPreferredLanguage());
            user.setLastLogin(usersDto.getLastLogin());
            user.setStatus(usersDto.getStatus());

            Users updatedUser = usersRepository.save(user);
            return Optional.of(mapToDto(updatedUser));
        }
        return Optional.empty();
    }

    // Eliminar un usuario
    public boolean deleteUser(Integer userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            // Eliminar la imagen asociada, si existe
            if (user.getImage() != null) {
                try {
                    imagesS3Bl.deleteFile(user.getImage().getFileName()); // Asumiendo que existe esta función
                } catch (Exception e) {
                    throw new RuntimeException("Error deleting associated image from MinIO", e);
                }
            }
            usersRepository.delete(user);
            return true;
        }
        return false;
    }

    // Cambiar contraseña
    public void changePassword(String email, String oldPassword, String newPassword) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verificar si la contraseña antigua es correcta
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Encriptar la nueva contraseña
        String encryptedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Actualizar la contraseña
        user.setPassword(encryptedNewPassword);  // Aquí se guarda la nueva contraseña encriptada
        usersRepository.save(user);
    }

    // Mapeo de Users a UsersDto
    private UsersDto mapToDto(Users user) {
        String imageUrl = null;
        if (user.getImage() != null) {
            // Generar el enlace de la imagen desde MinIO
            imageUrl = imagesS3Bl.generateFileUrl(user.getImage().getFileName());
        }

        return new UsersDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                null,
                user.getPhoneNumber(),
                user.getLocation(),
                user.getPreferredLanguage(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getStatus(),
                user.getRol() != null ? user.getRol().getRolId() : null,
                user.getImage() != null ? user.getImage().getImageId() : null,
                imageUrl
        );
    }
}
