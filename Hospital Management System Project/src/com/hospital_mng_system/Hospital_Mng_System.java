package com.hospital_mng_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospital_Mng_System {

	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";
	private static final String password = "krushna";

	public static void main(String[] args) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Scanner sc = new Scanner(System.in);
		Connection connection = DriverManager.getConnection(url, username, password);
		Patient patient = new Patient(connection, sc);
		Doctor doctor = new Doctor(connection, sc);
		while (true) {
			System.out.println("HOSPITAL MANAGEMENT SYSTEM");
			System.out.println("1. Add Patient");
			System.out.println("2. View Patients");
			System.out.println("3. View Doctors");
			System.out.println("4. Book Appointment");
			System.out.println("5. Exit");
			System.out.println("Enter your choice");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Add patient
				patient.addPatient();
				System.out.println();
				break;
			case 2:
				// View patient
				patient.viewPatients();
				System.out.println();
				break;
			case 3:
				// View Doctors
				doctor.viewDoctors();
				System.out.println();
				break;
			case 4:
				// Book Appointment
				bookAppointment(patient, doctor, connection, sc);
				System.out.println();
				break;

			case 5:
				System.err.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
				return;
			default:
				System.out.println("Enter valid choice !!!");
				break;
			}
		}
	}

	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) throws Exception{
		System.out.println("Enter patient Id: ");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor Id:  ");
		int doctorId = scanner.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD) ");
		String appointmentDate = scanner.next();
		if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
				String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
				try {
				PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
				preparedStatement.setInt(1, patientId);
				preparedStatement.setInt(2, doctorId);
				preparedStatement.setString(3, appointmentDate);
				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Appointment Book");
				} else {
					System.err.println("Failed to appointment Book !!");
				}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Doctor not available on this date!!");
			}
		} else {
			System.err.println("Either doctor or patient doesn't exist !!!");
		}

	}

	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection)
			throws Exception {
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id= ? AND appointment_date= ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, doctorId);
		preparedStatement.setString(2, appointmentDate);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			int count = rs.getInt(1);
			if (count == 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
