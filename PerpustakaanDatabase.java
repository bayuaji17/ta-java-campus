import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerpustakaanDatabase {
    private Connection connection;

    public PerpustakaanDatabase(String dbName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambahBuku(Buku buku) {
        String sql = "INSERT INTO data_buku (isbn, judul_buku, pengarang, tahun_terbit) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, buku.getISBN());
            statement.setString(2, buku.getJudul());
            statement.setString(3, buku.getPengarang());
            statement.setInt(4, buku.getTahunTerbit());

            statement.executeUpdate();
            System.out.println("Buku berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menambahkan buku ke database.");
        }
    }

    public void hapusBuku(String namaAtauISBN) {
        // Implementasi hapus buku dari database
        String sql = "DELETE FROM data_buku WHERE judul_buku = ? OR isbn = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Cek apakah input adalah ISBN (angka) atau nama buku (string)
            try {
                int isbn = Integer.parseInt(namaAtauISBN);
                statement.setNull(1, java.sql.Types.VARCHAR); // Set judul_buku menjadi NULL
                statement.setInt(2, isbn);
            } catch (NumberFormatException e) {
                // Jika bukan ISBN, anggap sebagai nama buku
                statement.setString(1, namaAtauISBN);
                statement.setNull(2, java.sql.Types.INTEGER); // Set ISBN menjadi NULL
            }

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Buku berhasil dihapus dari database.");
            } else {
                System.out.println("Buku tidak ditemukan atau gagal dihapus.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menghapus buku dari database.");
        }
    }

    public void cariBuku(String judul_buku) {
        String sql = "SELECT * FROM data_buku WHERE judul_buku LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + judul_buku + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Buku ditemukan:");
                    System.out.println("---------------------------------------------------------------------------");
                    System.out.println("|   ISBN    |\tTahun Terbit |\tPengarang                     |\tJudul Buku ");
                    System.out.println("---------------------------------------------------------------------------");
                    do {
                        int isbn = resultSet.getInt("isbn");
                        String judul = resultSet.getString("judul_buku");
                        String pengarang = resultSet.getString("pengarang");
                        int tahunTerbit = resultSet.getInt("tahun_terbit");

                        // Menampilkan informasi buku yang ditemukan
                        System.out.printf("| %4d ", isbn);
                        System.out.printf("|\t%8d     ", tahunTerbit);
                        System.out.printf("| %-30s ", pengarang);
                        System.out.printf("| %10s ", judul);
                        System.out.print("\n");
                    } while (resultSet.next());
                } else {
                    System.out.println("Buku tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal melakukan pencarian buku berdasarkan judul.");
        }
    }

    public void tampilkanDaftarBuku() {
        String sql = "SELECT * FROM data_buku";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int isbn = resultSet.getInt("isbn");
                String judul_buku = resultSet.getString("judul_buku");
                String pengarang = resultSet.getString("pengarang");
                int tahun_terbit = resultSet.getInt("tahun_terbit");

                System.out.printf("| %4d ", isbn);
                System.out.printf("|\t%8d     ", tahun_terbit);
                System.out.printf("| %-30s ", pengarang);
                System.out.printf("| %10s ", judul_buku);
                System.out.print("\n");

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menampilkan daftar buku.");
        }
    }

    public void peminjamanBuku(Peminjaman peminjaman) {

        String sql = "INSERT INTO peminjaman (id_anggota, nama_anggota, isbn, judul_buku, alamat_peminjam, tanggal_peminjaman, tanggal_pengembalian, status_peminjaman) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, peminjaman.getId_anggota());
            statement.setString(2, peminjaman.getNama_anggota());
            statement.setInt(3, peminjaman.getIsbn());
            statement.setString(4, peminjaman.getJudul_buku());
            statement.setString(5, peminjaman.getAlamat_peminjam());
            statement.setString(6, peminjaman.getTanggal_peminjaman());
            statement.setString(7, peminjaman.getTanggal_pengembalian());
            statement.setString(8, peminjaman.getStatus_peminjaman());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                String selectLastId = "SELECT LAST_INSERT_ROWID() AS last_id";
                try (PreparedStatement selectStatement = connection.prepareStatement(selectLastId)) {
                    ResultSet resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        int idPeminjamanBaru = resultSet.getInt("last_id");
                        System.out.println("Peminjaman berhasil ditambahkan. ID Peminjaman: " + idPeminjamanBaru);
                        // Tampilkan peminjaman baru
                        tampilkanPeminjaman(idPeminjamanBaru);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menambahkan peminjaman buku ke database.");
        }

    }

    public void tampilkanPeminjaman(int id_peminjaman) {
        String selectSql = "SELECT id_peminjaman, id_anggota, nama_anggota, isbn, judul_buku, alamat_peminjam, tanggal_peminjaman, tanggal_pengembalian, status_peminjaman FROM peminjaman WHERE id_peminjaman = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setInt(1, id_peminjaman);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("ID Peminjaman         : " + resultSet.getInt("id_peminjaman"));
                System.out.println("ID Anggota            : " + resultSet.getInt("id_anggota"));
                System.out.println("Nama Anggota          : " + resultSet.getString("nama_anggota"));
                System.out.println("ISBN Buku             : " + resultSet.getInt("isbn"));
                System.out.println("Judul Buku            : " + resultSet.getString("judul_buku"));
                System.out.println("Alamat Peminjam       : " + resultSet.getString("alamat_peminjam"));
                System.out.println("Tanggal Peminjaman    : " + resultSet.getString("tanggal_peminjaman"));
                System.out.println("Tanggal Pengembalian  : " + resultSet.getString("tanggal_pengembalian"));
                System.out.println("Status Peminjaman     : " + resultSet.getString("status_peminjaman"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menampilkan peminjaman buku.");
        }
    }


    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
