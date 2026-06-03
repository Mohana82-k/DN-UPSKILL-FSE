import java.sql.*;

public class StudentDAO {
    static void insertStudent(String name) throws SQLException {
        String sql = "INSERT INTO students (name) VALUES (?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Inserted: " + name);
        }
    }

    static void updateStudent(int id, String newName) throws SQLException {
        String sql = "UPDATE students SET name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Updated " + rows + " row(s)");
        }
    }

    public static void main(String[] args) throws SQLException {
        insertStudent("Bob");
        updateStudent(1, "Alicia");
    }
}
/*
Inserted: Bob
Updated 1 row(s)
*/