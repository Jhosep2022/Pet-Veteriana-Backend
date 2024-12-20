package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.UsersBl;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersBl usersBl;

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<ResponseDto<UsersDto>> createUser(@RequestBody UsersDto usersDto) {
        logger.info("Creating a new user: {}", usersDto.getName());
        try {
            UsersDto createdUser = usersBl.createUser(usersDto);
            ResponseDto<UsersDto> response = ResponseDto.success(createdUser, "User created successfully");
            logger.info("User created successfully: {}", createdUser.getName());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            ResponseDto<UsersDto> response = ResponseDto.error("Error creating user", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<ResponseDto<List<UsersDto>>> getAllUsers() {
        logger.info("Fetching all users");
        List<UsersDto> users = usersBl.getAllUsers();
        ResponseDto<List<UsersDto>> response = ResponseDto.success(users, "Users fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UsersDto>> getUserById(@PathVariable("id") Integer id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<UsersDto> usersDto = usersBl.getUserById(id);
        if (usersDto.isPresent()) {
            ResponseDto<UsersDto> response = ResponseDto.success(usersDto.get(), "User found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.warn("User with ID: {} not found", id);
            ResponseDto<UsersDto> response = ResponseDto.error("User not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UsersDto>> updateUser(@PathVariable("id") Integer id, @RequestBody UsersDto usersDto) {
        logger.info("Updating user with ID: {}", id);
        Optional<UsersDto> updatedUser = usersBl.updateUser(id, usersDto);
        if (updatedUser.isPresent()) {
            ResponseDto<UsersDto> response = ResponseDto.success(updatedUser.get(), "User updated successfully");
            logger.info("User updated successfully: {}", updatedUser.get().getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.warn("User with ID: {} not found for update", id);
            ResponseDto<UsersDto> response = ResponseDto.error("User not found for update", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable("id") Integer id) {
        logger.info("Deleting user with ID: {}", id);
        boolean deleted = usersBl.deleteUser(id);
        if (deleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "User deleted successfully");
            logger.info("User with ID: {} deleted successfully", id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            logger.warn("User with ID: {} not found for deletion", id);
            ResponseDto<Void> response = ResponseDto.error("User not found for deletion", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
