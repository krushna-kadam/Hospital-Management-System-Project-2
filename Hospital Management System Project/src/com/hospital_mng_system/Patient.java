package com.hospital_mng_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

	private Connection connection;
	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void addPatient() {
		System.out.println("Enter Patient Name :  ");
		String name = scanner.next();

		System.out.println("Enter Patient Age :  ");
		int age = scanner.nextInt();

		System.out.println("Enter Patient Gender :  ");
		String gender = scanner.next();

		try {

			String query = "INSERT INTO Patients(name,age,gender)VALUES(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3,gender);

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Patient added Successfully");
			} else {
				System.err.println("Failed to add Patient");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.err.println("Patients Details  :");
			while (resultset.next()) {
	System.out.println(resultset.getInt("id") +" - "+ resultset.getString("name") +" - "+ resultset.getInt("age")+" - "+ resultset.getString("gender"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getPatientById(int id) {
		String query = "select * from Patients where id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
