package open_ended;
import java.sql.*;

public class HospitalManagementSystem {

    // Define the database connection parameters
    static final String DB_URL = "jdbc:mysql://localhost/hospital";
    static final String USER = "root";
    static final String PASS = "hithesh@mysql";

    // Establish the database connection
    static Connection conn = null;
    static Statement stmt = null;

    public static void main(String[] args) {
        try {
            // Open a connection to the database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL queries
            stmt = conn.createStatement();

            // Create tables if not exists
            createTables();

            // Perform some basic operations
            addPatient("John Smith", "Male", "123 Main St, Anytown USA", "jsmith@email.com");
            addDoctor("Dr. Jane Doe", "Pediatrician", "janedoe@email.com");
            addAppointment(1, 1, "2023-04-01", "10:00 AM", "New patient");
            printAppointments();

            // Clean up resources
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    // Define a method to create tables if not exists
    public static void createTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS patients " +
                     "(id INT NOT NULL AUTO_INCREMENT, " +
                     " name VARCHAR(255), " +
                     " gender VARCHAR(10), " +
                     " address VARCHAR(255), " +
                     " email VARCHAR(255), " +
                     " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS doctors " +
              "(id INT NOT NULL AUTO_INCREMENT, " +
              " name VARCHAR(255), " +
              " specialization VARCHAR(255), " +
              " email VARCHAR(255), " +
              " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS appointments " +
              "(id INT NOT NULL AUTO_INCREMENT, " +
              " patient_id INT, " +
              " doctor_id INT, " +
              " appointment_date DATE, " +
              " appointment_time TIME, " +
              " description VARCHAR(255), " +
              " PRIMARY KEY ( id ), " +
              " FOREIGN KEY ( patient_id ) REFERENCES patients ( id ), " +
              " FOREIGN KEY ( doctor_id ) REFERENCES doctors ( id ))";
        stmt.executeUpdate(sql);
    }

    // Define a method to add a patient
    public static void addPatient(String name, String gender, String address, String email) throws SQLException {
        String sql = "INSERT INTO patients (name, gender, address, email) " +
                     "VALUES ('" + name + "', '" + gender + "', '" + address + "', '" + email + "')";
        stmt.executeUpdate(sql);
    }

    // Define a method// to add a doctor
    public static void addDoctor(String name, String specialization, String email) throws SQLException {
        String sql = "INSERT INTO doctors (name, specialization, email) " +
                     "VALUES ('" + name + "', '" + specialization + "', '" + email + "')";
        stmt.executeUpdate(sql);
    }

    // Define a method to add an appointment
    public static void addAppointment(int patientId, int doctorId, String date, String time, String description) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, description) " +
                     "VALUES (" + patientId + ", " + doctorId + ", '" + date + "', '" + time + "', '" + description + "')";
        stmt.executeUpdate(sql);
    }

    // Define a method to print all appointments
    public static void printAppointments() throws SQLException {
        String sql = "SELECT appointments.id, patients.name, doctors.name, appointments.appointment_date, appointments.appointment_time, appointments.description " +
                     "FROM appointments " +
                     "INNER JOIN patients ON appointments.patient_id = patients.id " +
                     "INNER JOIN doctors ON appointments.doctor_id = doctors.id";
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            int id = rs.getInt("id");
            String patientName = rs.getString("patients.name");
            String doctorName = rs.getString("doctors.name");
            String appointmentDate = rs.getString("appointment_date");
            String appointmentTime = rs.getString("appointment_time");
            String description = rs.getString("description");
            System.out.println("Appointment #" + id + " - " + patientName + " with " + doctorName + " on " + appointmentDate + " at " + appointmentTime + " - " + description);
        }

        rs.close();
    }
}

