package com.carlos.arqspring.arquiteturaspring;

import com.carlos.arqspring.arquiteturaspring.todos.ToDoRepository;
import com.carlos.arqspring.arquiteturaspring.todos.TodoEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ExemploInjecaoDependencia {

    public static void main(String[] args) {

        try{
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("url");
            dataSource.setUsername("username");
            dataSource.setPassword("password");

            Connection connection = dataSource.getConnection();

            ToDoRepository repository = null;//new SimpleJpaRepository<TodoEntity,Integer>();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
