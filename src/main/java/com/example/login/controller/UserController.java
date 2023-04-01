package com.example.login.controller;

import com.example.login.entity.User;
import com.example.login.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")

    public ResponseEntity<String> login(@RequestBody User user) {
        if (userService.validarLogin(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("Ingreso exitoso");
                    }
        else if(userService.validarBloqueo(user.getUsername(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario bloqueado");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
        }

    }

}
