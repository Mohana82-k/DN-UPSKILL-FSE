import java.sql.*;

public class TransactionDemo {
    public static void transfer(Connection conn, int fromId, int toId, double amount) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // Debit
            String debit = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(debit)) {
                ps.setDouble(1, amount);
                ps.setInt(2, fromId);
                ps.executeUpdate();
            }
            // Credit
            String credit = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(credit)) {
                ps.setDouble(1, amount);
                ps.setInt(2, toId);
                ps.executeUpdate();
            }
            conn.commit();
            System.out.println("Transfer successful");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Transfer failed, rolled back: " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS accounts (id INTEGER PRIMARY KEY, balance REAL)");
            conn.createStatement().execute("DELETE FROM accounts");
            conn.createStatement().execute("INSERT INTO accounts VALUES (1, 1000)");
            conn.createStatement().execute("INSERT INTO accounts VALUES (2, 500)");
            transfer(conn, 1, 2, 200);
        }
    }
}
/*
Transfer successful
*/