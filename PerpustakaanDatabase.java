import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PerpustakaanDatabase {
    // TODO REVIEW KONEKSI(2)
    private Connection connection;

    public PerpustakaanDatabase(String dbName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO REVIEW TAMBAH BUKU (2)
    // * METHOD UNTUK TAMBAH BUKU */
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

    // TODO REVIEW HAPUS BUKU (3)
    // * METHOD UNTUK HAPUS BUKU */
    public void hapusBuku(String namaAtauISBN) {
        String sql = "DELETE FROM data_buku WHERE judul_buku = ? OR isbn = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            try {
                int isbn = Integer.parseInt(namaAtauISBN);
                statement.setNull(1, java.sql.Types.VARCHAR);
                statement.setInt(2, isbn);
            } catch (NumberFormatException e) {

                statement.setString(1, namaAtauISBN);
                statement.setNull(2, java.sql.Types.INTEGER);
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

    // TODO REVIEW EDIT BUKU(3)
    // * METHOD UNTUK EDIT BUKU */
    public void editBuku() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pilih atribut yang ingin diubah:");
        System.out.println("1. ISBN");
        System.out.println("2. Judul Buku");
        System.out.println("3. Pengarang");
        System.out.println("4. Tahun Terbit");

        System.out.print("Masukkan nomor atribut (1-4) : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String columnName = "";
        switch (choice) {
            case 1:
                columnName = "isbn";
                break;
            case 2:
                columnName = "judul_buku";
                break;
            case 3:
                columnName = "pengarang";
                break;
            case 4:
                columnName = "tahun_terbit";
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }

        System.out.print("Masukkan nilai " + columnName.toUpperCase() + " Baru : ");
        String newValue = scanner.nextLine();

        System.out.print("Masukkan ISBN buku yang akan diubah: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();

        String sql = "UPDATE data_buku SET " + columnName + " = ? WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newValue);
            statement.setInt(2, isbn);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Buku berhasil diubah.");
            } else {
                System.out.println("Buku tidak ditemukan atau gagal diubah.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal mengubah buku.");
        }

    }

    // TODO REVIEW TAMPILKAN BUKU(4)
    // * METHOD UNTUK MENAMPILKAN DAFTAR BUKU
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

    // TODO REVIEW CARI BUKU(4)
    // * METHOD UNTUK CARI BUKU */
    public void cariBuku(String judul_buku) {
        String sql = "SELECT * FROM data_buku WHERE judul_buku LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + judul_buku + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Buku ditemukan ! berikut adalah daftarnya...");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    System.out.println("|   ISBN    |\tTahun Terbit |\tPengarang                     |\tJudul Buku ");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    do {
                        int isbn = resultSet.getInt("isbn");
                        String judul = resultSet.getString("judul_buku");
                        String pengarang = resultSet.getString("pengarang");
                        int tahunTerbit = resultSet.getInt("tahun_terbit");

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

    // TODO REVIEW TAMBAH PEMINJAMAN(4)
    // * METHOD UNTUK PEMINJAMAN BUKU
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
                        System.out.println("Peminjaman berhasil ditambahkan !");
                        tampilkanPeminjaman(idPeminjamanBaru);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menambahkan peminjaman buku ke database.");
        }

    }

    // TODO REVIEW TAMPILKAN SEMUA DAFTAR PEMINJAMAN(5)
    // * METHOD UNTUK MENAMPILKAN SEMUA PEMINJAM
    public void tampilkanSemuaPeminjam() {
        String sql = "SELECT * FROM peminjaman";
        try (PreparedStatement selecStatement = connection.prepareStatement(sql);
                ResultSet resultSet = selecStatement.executeQuery()) {
            while (resultSet.next()) {
                int id_peminjaman = resultSet.getInt("id_peminjaman");
                int id_anggota = resultSet.getInt("id_anggota");
                String nama_anggota = resultSet.getString("nama_anggota");
                String alamat_peminjam = resultSet.getString("alamat_peminjam");
                int isbn = resultSet.getInt("isbn");
                String judul_buku = resultSet.getString("judul_buku");
                String tanggal_peminjaman = resultSet.getString("tanggal_peminjaman");
                String tanggal_pengembalian = resultSet.getString("tanggal_pengembalian");
                String status_peminjaman = resultSet.getString("status_peminjaman");

                System.out.printf("| %-13d ", id_peminjaman);
                System.out.printf("| %-10d ", id_anggota);
                System.out.printf("| %-14s ", nama_anggota);
                System.out.printf("| %4d ", isbn);
                System.out.printf("| %-29s ", judul_buku);
                System.out.printf("| %-14s ", tanggal_peminjaman);
                System.out.printf("| %-16s ", tanggal_pengembalian);
                System.out.printf("| %-13s ", status_peminjaman);
                System.out.printf("| %4s ", alamat_peminjam);
                System.out.println("");

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagl menampilkan daftar peminjam");
        }
    }

    // TODO REVIEW UBAH STATUS PEMINJAMAN (5)
    // * METHOD UNTUK UBAH STATUS PEMINJAMAN
    public void editPeminjaman(Peminjaman peminjaman) {
        String sqlEdit = "UPDATE peminjaman SET status_peminjaman = ? WHERE id_peminjaman = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlEdit)) {
            statement.setString(1, peminjaman.getStatus_peminjaman());
            statement.setInt(2, peminjaman.getIdPeminjaman());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Berhasil Mengubah Status Pinjaman !");
            } else {
                System.out.println("Gagal Mengubah Status Pinjaman !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal Mengubah Status Pinjaman !");
        }
    }

    // TODO REVIEW CETAK STRUK (5)
    // * METHOD TAMPILKAN STRUK
    public void tampilkanPeminjaman(int id_peminjaman) {
        String selectSql = "SELECT id_peminjaman, id_anggota, nama_anggota, isbn, judul_buku, alamat_peminjam, tanggal_peminjaman, tanggal_pengembalian, status_peminjaman FROM peminjaman WHERE id_peminjaman = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setInt(1, id_peminjaman);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("========================================================");
                System.out.println("\tSTRUK PEMINJAMAN BUKU PERPUSTAKAAN");
                System.out.println("========================================================");
                System.out.println("No Pinjam           : " + resultSet.getInt("id_peminjaman"));
                System.out.println("Tanggal Peminjaman  : " + resultSet.getString("tanggal_peminjaman"));
                System.out.println("Tanggal Jatuh Tempo : " + resultSet.getString("tanggal_pengembalian"));
                System.out.println("Nomor Anggota       : " + resultSet.getInt("id_anggota"));
                System.out.println("Nama  Anggota       : " + resultSet.getString("nama_anggota"));
                System.out.println("--------------------------------------------------------");
                System.out.println("| ISBN      | Judul Buku                                |");
                System.out.println("--------------------------------------------------------");
                System.out.printf("| %-10d", resultSet.getInt("isbn"));
                System.out.printf("| %-41s |", resultSet.getString("judul_buku"));
                System.out.println("");
                System.out.println("--------------------------------------------------------");
                System.out.println(
                        "Setiap keterlambatan pengembalian buku, \ndikenakan denda sebesar Rp. 10,000 per hari !");
                System.out.println("========================================================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menampilkan peminjaman buku.");
        }
    }

    // TODO REVIEW KONEKSI (2)
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
