package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.LoginRequestDto;
import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthBl {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequestDto loginRequest) {
        // Buscar usuario en la base de datos
        Users user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Verificar si la contraseña es correcta
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Crear un objeto UsersDto con todos los datos necesarios
        UsersDto userDto = new UsersDto();
        userDto.setUserId(user.getUserId()); // Asumiendo que este es el campo en tu entidad Users
        userDto.setRolId(user.getRol().getRolId());     // Asumiendo que tienes un campo rolId en tu entidad Users
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());       // Asumiendo que tienes un campo name
        userDto.setPreferredLanguage(user.getPreferredLanguage()); // Asumiendo que tienes este campo

        // Generar el token con todos los datos
        return jwtTokenProvider.generateToken(userDto);
    }

}