package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.bl.ProvidersBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProvidersController {

    @Autowired
    private ProvidersBl providersBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo Provider
    @PostMapping
    public ResponseDto<ProvidersDto> createProvider(HttpServletRequest request, @RequestBody ProvidersDto providersDto) {
        String token = request.getHeader("Authorization");

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        ProvidersDto createdProvider = providersBl.createProvider(extractedToken, providersDto);
        return ResponseDto.success(createdProvider, "Provider created successfully");
    }

    // Obtener todos los Providers
    @GetMapping
    public ResponseDto<List<ProvidersDto>> getAllProviders(@RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        List<ProvidersDto> providers = providersBl.getAllProviders();
        return ResponseDto.success(providers, "Providers fetched successfully");
    }

    // Obtener Provider por ID
    @GetMapping("/{id}")
    public ResponseDto<ProvidersDto> getProviderById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        ProvidersDto provider = providersBl.getProviderById(id);
        return ResponseDto.success(provider, "Provider fetched successfully");
    }

    // Actualizar Provider
    @PutMapping("/{id}")
    public ResponseDto<ProvidersDto> updateProvider(@PathVariable Integer id, @RequestBody ProvidersDto providersDto, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        ProvidersDto updatedProvider = providersBl.updateProvider(id, providersDto);
        return ResponseDto.success(updatedProvider, "Provider updated successfully");
    }

    // Eliminar Provider
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteProvider(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        providersBl.deleteProvider(id);
        return ResponseDto.success("Deleted", "Provider deleted successfully");
    }
}
