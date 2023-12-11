import java.util.Scanner;
//TODO REVIEW CODINGAN
//TODO - Review Class Buku.java dan Class Peminjaman.java  (1)
//TODO - Review database dan 2 table, method koneksi database, Method tambah (2)
//TODO - Review Method hapus buku, edit buku (3)
//TODO - Review Method tampilkan daftar buku, cari buku,tambah peminjaman (4)
//TODO - Review Method tampilkan daftar peminjaman,ubah status peminjaman,cetak Struk (5)
//TODO - Review Class Main.java dan OUTPUT PROGRAM ! (6)

// * Driver menggunakan versi sqlite-jdbc-3.42.0.0.jar
// * Database menggunakan sqlite3

//TODO REVIEW (6)
public class Main {
    public static void main(String[] args) {
        PerpustakaanDatabase perpustakaanDB = new PerpustakaanDatabase("perpustakaan.db");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String lanjutkan = "";
        while (!exit) {
            System.out.println("Menu :");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Edit Buku");
            System.out.println("4. Tampilkan Daftar Buku");
            System.out.println("5. Cari Buku");
            System.out.println("6. Peminjaman Buku");
            System.out.println("7. Tampilkan daftar peminjam");
            System.out.println("8. Ubah Status Peminjaman");
            System.out.println("9. Cetak Struk");
            System.out.println("10. Keluar");

            System.out.print("Pilihan Anda (1-10): ");
            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1:
                    // *Tambah Buku
                    boolean tambahDataLagi = true;
                    while (tambahDataLagi) {
                        System.out.print("Masukkan jumlah buku yang ingin di input: ");
                        int jumlahBuku = scanner.nextInt();
                        scanner.nextLine();
                        for (int i = 0; i < jumlahBuku; i++) {

                            System.out.print("Masukkan ISBN         : ");
                            int isbn = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Masukkan Judul        : ");
                            String judul_buku = scanner.nextLine();
                            System.out.print("Masukkan Pengarang    : ");
                            String pengarang = scanner.nextLine();
                            System.out.print("Masukkan Tahun Terbit : ");
                            int tahun_terbit = scanner.nextInt();
                            scanner.nextLine();

                            Buku bukuBaru = new Buku(isbn, judul_buku, pengarang, tahun_terbit);

                            perpustakaanDB.tambahBuku(bukuBaru);
                        }
                        System.out.print("Tambah data lagi? (y/n) : ");
                        lanjutkan = scanner.nextLine().toLowerCase();
                        tambahDataLagi = lanjutkan.equals("y") ? true : false;
                    }
                    break;
                case 2:
                    // *Hapus Buku
                    boolean hapusLagi = true;
                    while (hapusLagi) {
                        System.out.print("Masukkan Nama atau ISBN Buku yang Akan Dihapus : ");
                        String namaAtauISBN = scanner.nextLine();
                        perpustakaanDB.hapusBuku(namaAtauISBN);
                        System.out.print("Hapus Buku lagi ? (y/n) : ");
                        lanjutkan = scanner.nextLine().toLowerCase();
                        hapusLagi = lanjutkan.equals("y");
                    }
                    break;
                case 3:
                    // * Edit BUKU
                    perpustakaanDB.editBuku();
                    break;
                case 4:
                    // * Tampilkan Semua Daftar Buku
                    System.out.println(
                            "===============================================================================================");
                    System.out.println(
                            "                                    DAFTAR SEMUA BUKU                                          ");
                    System.out.println(
                            "===============================================================================================");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    System.out.println("|   ISBN    |\tTahun Terbit |\tPengarang                     |\tJudul Buku ");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    perpustakaanDB.tampilkanDaftarBuku();
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    System.out.print("Apakah Anda ingin melanjutkan ke menu utama? (y/n) : ");
                    lanjutkan = scanner.nextLine().toLowerCase();

                    exit = !lanjutkan.equals("y") ? true : false;
                    break;
                case 5:
                    // * Cari Buku
                    boolean cariLagi = true;
                    while (cariLagi) {
                        System.out.print("\nMasukan judul buku yang ingin dicari : \n");
                        String judul_buku = scanner.nextLine();
                        System.out.println(
                                "===============================================================================================");
                        perpustakaanDB.cariBuku(judul_buku);
                        System.out.println(
                                "-----------------------------------------------------------------------------------------------");
                        System.out.print("Cari Buku lagi ? (y/n) : ");
                        lanjutkan = scanner.nextLine().toLowerCase();
                        cariLagi = lanjutkan.equals("y");
                    }
                    break;
                case 6:
                    // * Peminjaman Buku
                    boolean pinjamLagi = true;
                    while (pinjamLagi) {
                        System.out.print("Masukkan jumlah peminjam yang ingin di input : ");
                        int jumlahPeminjam = scanner.nextInt();
                        scanner.nextLine();
                        for (int i = 0; i < jumlahPeminjam; i++) {

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

                            Peminjaman peminjamanBuku = new Peminjaman(id_anggota, nama_anggota, alamat_peminjam,
                                    isbn_buku,
                                    judulBuku, tanggal_peminjaman, tanggal_pengembalian, status_peminjaman);
                            perpustakaanDB.peminjamanBuku(peminjamanBuku);
                        }
                        System.out.print("Apakah Anda ingin menambah pinjaman (y/n): ");
                        lanjutkan = scanner.nextLine().toLowerCase();

                        pinjamLagi = lanjutkan.equals("y") ? true : false;
                    }
                    break;
                case 7:
                    // * Tampilkan Semua Daftar Peminjam
                    System.out.println(
                            "===================================================================================");
                    System.out.println("                        DAFTAR PEMINJAM BUKU");
                    System.out.println(
                            "===================================================================================");
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println(
                            "| Id Peminjaman | Id Anggota | Nama Anggota   | ISBN      | Judul Buku                    | Tgl Peminjaman | Tgl Pengembalian | Status        | Alamat Peminjam          ");
                    System.out.println(
                            "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    perpustakaanDB.tampilkanSemuaPeminjam();
                    System.out.println("");
                    System.out.print("Apakah Anda ingin melanjutkan ke menu utama? (y/n): ");
                    lanjutkan = scanner.nextLine().toLowerCase();

                    exit = !lanjutkan.equals("y") ? true : false;
                    break;
                case 8:
                    // * Ubah Status Pinjaman
                    boolean ubahLagi = true;
                    while (ubahLagi) {
                        System.out.print("Masukkan Nomor Pinjaman : ");
                        int id_peminjaman = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Ubah Status Pinjaman    : ");
                        String status_pinjaman = scanner.next();
                        Peminjaman editPinjam = new Peminjaman(id_peminjaman, status_pinjaman);
                        perpustakaanDB.editPeminjaman(editPinjam);
                        System.out.print("Ingin mengubah lagi ? (y/n) :");
                        lanjutkan = scanner.next().toLowerCase();
                        ubahLagi = lanjutkan.equals("y") ? true : false;
                    }
                    break;
                case 9:
                    // * CETAK STRUK
                    boolean cetakLagi = true;

                    while (cetakLagi) {
                        System.out.println("Masukan nomor Pinjaman untuk mencetak Struk");
                        System.out.print("Masukan nomor Pinjaman :");
                        int idPeminjaman = scanner.nextInt();
                        perpustakaanDB.tampilkanPeminjaman(idPeminjaman);
                        System.out.print("Ingin mencetak lagi ? (y/n) :");
                        lanjutkan = scanner.next();
                        cetakLagi = lanjutkan.equals("y") ? true : false;
                    }

                    break;
                case 10:
                    // !Keluar dari aplikasi
                    System.out.println("Keluar dari aplikasi.");
                    exit = true;
                    break;
                default:
                    // !inputan salah
                    System.out.println("Pilihan tidak valid. Coba lagi.");
                    break;
            }
        }

        perpustakaanDB.closeConnection();
        scanner.close();
    }
}
