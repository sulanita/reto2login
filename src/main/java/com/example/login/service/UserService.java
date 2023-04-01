package com.example.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.login.repository.UserRepository;
import com.example.login.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean validarLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        if (user.getBloqueado()==1){
            return false;
        }
        else {
            if (!user.getPassword().equals(password)) {
                user.incrementarIntentosFallidos();
                userRepository.update(user);
                if (user.getIntentosFallidos() >= 3) {
                    user.isBloqueado();
                    userRepository.updateLock(user);
                }
                return true;
            }
        }


        user.resetIntentosFallidos();
        userRepository.update(user);
        user.resetBloqueo();
        userRepository.updateLock(user);
        return true;
    }

    private ResponseEntity<String> validarBloqueo(User user) {
        if (user.getBloqueado()==1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("usuario bloqueado");
        }
        return null;
    }
}
