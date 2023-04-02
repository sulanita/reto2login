package com.example.login.controller;

import com.example.login.entity.User;
import com.example.login.service.UserService;
import java.util.Optional;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Tracer tracer = GlobalOpenTelemetry.getTracer("ATpos", "1.0.0");

    @PostMapping("/login")

    public ResponseEntity<String> login(@RequestBody User user) {
        Span span = tracer.spanBuilder("Login").startSpan();
        try (Scope ss=span.makeCurrent()) {
            Optional<User> opUsr = userService.findByUsername(user.getUsername());
            if (!opUsr.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            User usr = opUsr.get();
            int intentos=usr.getIntentosFallidos();
            UserService.EstadoAutenticacion auth=userService.validarLogin(user.getUsername(), user.getPassword());

            switch (auth){
                case ok:
                    if (usr.getIntentosFallidos() > 0) {
                        Attributes att = Attributes.builder()
                                .put("usuario", user.getUsername())
                                .put("intentos", intentos)
                                .build();
                        span.addEvent("NotificarUsuario", att);
                    }
                    return ResponseEntity.ok("Ingreso exitoso");

                case bloqueado:
                    Attributes att = Attributes.builder()
                            .put("usuario", user.getUsername()).build();
                    span.addEvent("UsuarioBloqueado", att);

                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario bloqueado");

                case fail:
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");

                default:
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problemas autenticando");
            }
        } catch (Exception e) {
            span.recordException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } finally {
            span.end();
        }
    }


}
