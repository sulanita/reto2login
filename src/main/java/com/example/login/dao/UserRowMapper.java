package com.example.login.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.login.entity.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setIntentosFallidos(rs.getInt("intentos_fallidos"));
        user.setBloqueado(rs.getInt("bloqueado"));
        return user;
    }

}
