package com.example.login.repository;
import com.example.login.dao.UserRowMapper;
import com.example.login.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM usuario2 WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username},  new UserRowMapper()).stream().findFirst();
    }

    public void update(User user) {
        String sql = "UPDATE usuario2 SET intentos_fallidos = ? WHERE username = ?";
        jdbcTemplate.update(sql, user.getIntentosFallidos(), user.getUsername());
    }
    public void updateLock(User user) {
        String sql = "UPDATE usuario2 SET bloqueado = ? WHERE username = ?";
        jdbcTemplate.update(sql, user.getBloqueado(), user.getUsername());
    }
}
