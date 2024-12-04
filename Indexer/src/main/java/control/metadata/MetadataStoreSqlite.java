package control.metadata;

import control.interfaces.MetadataStoreManager;
import model.Metadata;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MetadataStoreSqlite implements MetadataStoreManager {

	private static final String DATABASE_URL = "jdbc:sqlite:books.db";

	public MetadataStoreSqlite() {
		createDatabase();
		createTable();
	}

	private void createDatabase() {
		try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
			if (conn != null) {
				System.out.println("Base de datos creada o conectada correctamente.");
			}
		} catch (SQLException e) {
			System.err.println("Error al crear la base de datos: " + e.getMessage());
		}
	}

	private void createTable() {
		String sql = """
                CREATE TABLE IF NOT EXISTS Books (
                    bookID TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    author TEXT NOT NULL,
                    year INTEGER NOT NULL,
                    language TEXT NOT NULL,
                    downloadLink TEXT NOT NULL
                );
                """;

		try (Connection conn = DriverManager.getConnection(DATABASE_URL);
			 Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println("Error al crear la tabla: " + e.getMessage());
		}
	}

	@Override
	public void update(Metadata metadata) {
		String sql = """
                INSERT INTO Books (bookID, name, author, year, language, downloadLink)
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT(bookID) DO NOTHING;
                """;

		try (Connection conn = DriverManager.getConnection(DATABASE_URL);
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			int i =0;
			for(String data: metadata.toList()){
				pstmt.setString(i, data);
				i++;
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al guardar el libro: " + e.getMessage());
		}
	}
}
