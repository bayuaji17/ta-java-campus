import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PerpustakaanDatabase perpustakaanDB = new PerpustakaanDatabase("perpustakaan.db");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu :");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Tampilkan Daftar Buku");
            System.out.println("5. Peminjaman Buku");
            System.out.println("6. Keluar");

            System.out.print("Pilihan Anda (1-6): ");
            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1:
                    // Tambah Buku
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
                        System.out.print("Tambah data lagi? (ya/tidak) : ");
                        String lanjutkanTambahBuku = scanner.nextLine().toLowerCase();
                        tambahDataLagi = lanjutkanTambahBuku.equals("ya") ? true : false;
                    }
                    break;
                case 2:
                    // Hapus Buku
                    boolean hapusLagi = true;
                    while (hapusLagi) {
                        System.out.print("Masukkan Nama atau ISBN Buku yang Akan Dihapus : ");
                        String namaAtauISBN = scanner.nextLine();
                        perpustakaanDB.hapusBuku(namaAtauISBN);
                        System.out.print("Hapus Buku lagi ? (ya/tidak) :");
                        String hapusBukuLagi = scanner.nextLine().toLowerCase();
                        hapusLagi = hapusBukuLagi.equals("ya");
                    }
                    break;
                case 3:
                    // Cari Buku
                    boolean cariLagi = true;
                    while (cariLagi) {
                        System.out.print("Masukan judul buku yang ingin dicari : \n");
                        String judul_buku = scanner.nextLine();
                        perpustakaanDB.cariBuku(judul_buku);
                        System.out.println(
                                "-----------------------------------------------------------------------------------------------");
                        System.out.print("Cari Buku lagi ? (ya/tidak) :");
                        String cariBukuLagi = scanner.nextLine().toLowerCase();
                        cariLagi = cariBukuLagi.equals("ya");
                    }
                    break;
                case 4:
                    // Tampilkan Semua Daftar Buku
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    System.out.println("|   ISBN    |\tTahun Terbit |\tPengarang                     |\tJudul Buku ");
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    perpustakaanDB.tampilkanDaftarBuku();
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------");
                    System.out.print("Apakah Anda ingin melanjutkan ke menu utama? (ya/tidak): ");
                    String lanjutkan = scanner.nextLine().toLowerCase();

                    exit = !lanjutkan.equals("ya") ? true : false;
                    break;
                case 5:
                    // Pinjam Buku
                    boolean pinjamLagi = true;
                    while (pinjamLagi) {
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
                    }
                    System.out.print("Apakah Anda ingin melanjutkan ke menu utama? (ya/tidak): ");
                    String tambahPinjaman = scanner.nextLine().toLowerCase();

                    pinjamLagi = tambahPinjaman.equals("ya") ? true : false;

                    break;
                case 6:
                    // Keluar dari aplikasi
                    System.out.println("Keluar dari aplikasi.");
                    exit = true;
                    break;
                default:
                    // inputan salah
                    System.out.println("Pilihan tidak valid. Coba lagi.");
                    break;
            }
        }

        perpustakaanDB.closeConnection();
        scanner.close();
    }
}
