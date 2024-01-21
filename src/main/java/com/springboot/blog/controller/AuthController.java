package com.springboot.blog.controller;

import com.springboot.blog.dto.JWTAuthResponse;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@Tag(name = "REST APIs for Login/Signup Resource")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //Build Login REST API
    @Operation(
            summary = "Login REST API",
            description = "Login REST API is used to Login into Blog APP"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtauthResponse = new JWTAuthResponse();
        jwtauthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtauthResponse);
    }

    //Build register rest api
    @Operation(
            summary = "Signup REST API",
            description = "Signup REST API is used to Signup new user into Blog APP"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.registerUser(registerDto);
        return ResponseEntity.ok(response);
    }
}
