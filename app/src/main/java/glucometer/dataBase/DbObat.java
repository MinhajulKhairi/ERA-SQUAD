package glucometer.dataBase;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import glucometer.models.Obat;
import glucometer.utils.DataBaseConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DbObat {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS obat (id INTEGER PRIMARY KEY AUTOINCREMENT, namaObat TEXT, dosis INTEGER, bentuk TEXT, catatan TEXT, tanggal TEXT)";
    private static final String INSERT_QUERY = "INSERT INTO obat (namaObat, dosis, bentuk, catatan, tanggal) VALUES (?, ?, ?, ?, ?)";
    private Statement stmt;
    private Connection conn;

    public DbObat() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection conn = DataBaseConfig.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addData(Obat obat) {
        try (Connection conn = DataBaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)) {
            stmt.setString(1, obat.getNamaObat());
            stmt.setInt(2, obat.getDosis());
            stmt.setString(3, obat.getBentuk());
            stmt.setString(4, obat.getCatatan());
            stmt.setString(5, obat.getTanggal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Obat> getAll() throws SQLException {
        ObservableList<Obat> obatList = FXCollections.observableArrayList();

        try (Connection conn = DataBaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM obat")) {
            while (rs.next()) {
                String namaObat = rs.getString("namaObat");
                int dosis = rs.getInt("dosis");
                String bentuk = rs.getString("bentuk");
                String catatan = rs.getString("catatan");
                String tanggal = rs.getString("tanggal");

                Obat obatObj = new Obat(namaObat, dosis, bentuk, catatan, tanggal);
                obatList.add(obatObj);
            }
        } catch (SQLException e) {
            throw new SQLException();
        }

        return obatList;
    }

    public void syncData(List<Obat> listObat) {
        try {
            stmt.executeUpdate("DELETE from obat");
            stmt = conn.createStatement();
            for (Obat obat : listObat) {
                String sql = String.format("""
                        INSERT INTO obat(namaObat, dosis, bentuk, catatan, tanggal)
                        VALUES('%d', '%s');
                        """,
                        obat.getNamaObat(),
                        obat.getDosis(),
                        obat.getBentuk(),
                        obat.getCatatan(),
                        obat.getTanggal());
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
}
}
