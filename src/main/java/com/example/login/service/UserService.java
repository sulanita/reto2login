package com.example.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.login.repository.UserRepository;
import com.example.login.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public enum EstadoAutenticacion {
        ok,
        fail,
        bloqueado,

    }

    @Autowired
    private UserRepository userRepository;


    public EstadoAutenticacion validarLogin(String username, String password) {
        Optional<User> opUser = userRepository.findByUsername(username);
        User user;
        if(!opUser.isPresent()){
            return EstadoAutenticacion.fail;
        }
        else {
            user=opUser.get();
            if(user.getBloqueado()==1){
                return EstadoAutenticacion.bloqueado;
            }
            else if (!user.getPassword().equals(password)) {
                user.incrementarIntentosFallidos();
                userRepository.update(user);
                validarBloqueo(username, password);
                return EstadoAutenticacion.fail;
            }else{
                user.resetIntentosFallidos();
                userRepository.update(user);
                user.resetBloqueo();
                userRepository.updateLock(user);
                return EstadoAutenticacion.ok;
            }
        }

    }

    public boolean validarBloqueo(String username, String password) {
        Optional<User> opUser = userRepository.findByUsername(username);
        User user;
        if(!opUser.isPresent()){
            return false;
        }
        user=opUser.get();
        if (user.getBloqueado() == 1) {
            return true;
        }
        if (user.getIntentosFallidos() >= 3) {
            user.isBloqueado();
            userRepository.updateLock(user);
        }
        return false;

    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}