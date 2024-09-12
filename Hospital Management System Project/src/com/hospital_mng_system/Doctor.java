package com.hospital_mng_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	private Connection connection;

	public Doctor(Connection connection, Scanner scanner) {
		this.connection = connection;
	}

	public void viewDoctors() {
		String query = "select * from doctors";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.err.println("Doctors Details  :");
			while (resultset.next()) {
		System.out.println(resultset.getInt("id") +" - "+ resultset.getString("name") +" - "+ resultset.getString("specialization"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getDoctorById(int id) {
		String query = "select * from doctors where id = ?";
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
