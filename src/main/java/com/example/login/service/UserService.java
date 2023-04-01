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

        if (!user.getUsername().equals(username)) {
            return false;
        }

        else {
            if (!user.getPassword().equals(password)) {
                user.incrementarIntentosFallidos();
                userRepository.update(user);
                validarBloqueo(username, password);
                return false;
            }
        }


        user.resetIntentosFallidos();
        userRepository.update(user);
        user.resetBloqueo();
        userRepository.updateLock(user);
        return true;
    }

    public boolean validarBloqueo(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user.getBloqueado() == 1) {
            return true;
        }
        if (user.getIntentosFallidos() >= 3) {
            user.isBloqueado();
            userRepository.updateLock(user);
        }
        return false;

    }
}