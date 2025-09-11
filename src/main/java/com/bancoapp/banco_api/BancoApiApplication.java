package com.bancoapp.banco_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class BancoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoApiApplication.class, args);
	}
	@Bean
	CommandLineRunner testDatabaseConnection(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				System.out.println("Conexión a la base de datos exitosa!");
				System.out.println("Base de datos: " + connection.getMetaData().getDatabaseProductName());
				System.out.println("Versión: " + connection.getMetaData().getDatabaseProductVersion());
			} catch (SQLException e) {
				System.err.println("Error al conectar a la base de datos");
				e.printStackTrace();
			}};}

}
