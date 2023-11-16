
// import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PerpustakaanDatabase perpustakaanDB = new PerpustakaanDatabase("perpustakaan.db");
        Scanner scanner = new Scanner(System.in);
        int menuChoice = 0;

        while (menuChoice != 6) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Tampilkan Daftar Buku");
            System.out.println("5. Peminjaman Buku");
            System.out.println("6. Keluar");

            System.out.print("Pilihan Anda: ");
            menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1:
                    // Meminta input data buku dari pengguna
                    System.out.print("Masukkan ISBN: ");
                    int isbn = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan newline
                    System.out.print("Masukkan Judul: ");
                    String judul_buku = scanner.nextLine();
                    System.out.print("Masukkan Pengarang: ");
                    String pengarang = scanner.nextLine();
                    System.out.print("Masukkan Tahun Terbit: ");
                    int tahun_terbit = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan newline

                    // Membuat objek buku dengan data yang dimasukkan
                    Buku bukuBaru = new Buku(isbn, judul_buku, pengarang, tahun_terbit);

                    // Menambahkan buku ke database
                    perpustakaanDB.tambahBuku(bukuBaru);
                    break;
                case 2:
                    System.out.print("Masukkan Nama atau ISBN Buku yang Akan Dihapus: ");
                    String namaAtauISBN = scanner.nextLine();

                    // Memanggil metode hapusBuku
                    perpustakaanDB.hapusBuku(namaAtauISBN);
                    break;
                case 3:
                    System.out.print("Masukan judul buku yang ingin dicari : \n");
                    judul_buku = scanner.nextLine();
                    perpustakaanDB.cariBuku(judul_buku);
                    break;
                case 4:
                    System.out.println("|\tISBN\t|\t\tJudul\t\t|\tPengarang\t|Tahun Terbit| ");
                    perpustakaanDB.tampilkanDaftarBuku();
                    break;
                case 5:
                    System.out.print("Masukkan Id Anggota           : ");
                    int id_anggota = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Masukkan Nama Anggota         : ");
                    String nama_anggota = scanner.nextLine();
                    System.out.print("Masukkan Alamat Peminjam      : ");
                    String alamat_peminjam = scanner.nextLine();
                    System.out.print("Masukkan ISBN                 : ");
                    int isbn_buku = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan Judul Buku           : ");
                    String judulBuku = scanner.nextLine();
                    System.out.print("Masukkan Tanggal Peminjaman   : ");
                    String tanggal_peminjaman = scanner.nextLine();
                    System.out.print("Masukkan Tanggal Pengembalian : ");
                    String tanggal_pengembalian = scanner.nextLine();
                    System.out.print("Masukkan Status Peminjaman    : ");
                    String status_peminjaman = scanner.nextLine();

                   
                    Peminjaman peminjamanBuku = new Peminjaman(id_anggota, nama_anggota, alamat_peminjam, isbn_buku,
                            judulBuku, tanggal_peminjaman, tanggal_pengembalian, status_peminjaman);
                    perpustakaanDB.peminjamanBuku(peminjamanBuku);
                    break;
                case 6:
                    System.out.println("Keluar dari aplikasi.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Coba lagi.");
                    break;
            }
        }

        perpustakaanDB.closeConnection();
        scanner.close();
    }
}
