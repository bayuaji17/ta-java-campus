//TODO REVIEW Class Peminjaman (1)
public class Peminjaman {
    private int id_peminjaman;
    private int id_anggota;
    private String nama_anggota;
    private String alamat_peminjam;
    private int isbn_buku;
    private String judulBuku;
    private String tanggal_peminjaman;
    private String tanggal_pengembalian;
    private String status_peminjaman;

    public Peminjaman(int id_anggota, String nama_anggota, String alamat_peminjam, int isbn_buku, String judulBuku,
            String tanggal_peminjaman, String tanggal_pengembalian, String status_peminjaman) {
        this.id_anggota = id_anggota;
        this.nama_anggota = nama_anggota;
        this.alamat_peminjam = alamat_peminjam;
        this.isbn_buku = isbn_buku;
        this.judulBuku = judulBuku;
        this.tanggal_peminjaman = tanggal_peminjaman;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.status_peminjaman = status_peminjaman;
    }

    public Peminjaman(int id_peminjaman, String status_peminjaman) {
        this.id_peminjaman = id_peminjaman;
        this.status_peminjaman = status_peminjaman;
    }

    public Peminjaman(int id_peminjaman, int id_anggota, String nama_anggota, String alamat_peminjam, int isbn_buku,
            String judulBuku, String tanggal_peminjaman, String tanggal_pengembalian, String status_peminjaman) {
        this.id_peminjaman = id_peminjaman;
        this.id_anggota = id_anggota;
        this.nama_anggota = nama_anggota;
        this.alamat_peminjam = alamat_peminjam;
        this.isbn_buku = isbn_buku;
        this.judulBuku = judulBuku;
        this.tanggal_peminjaman = tanggal_peminjaman;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.status_peminjaman = status_peminjaman;
    }

    public int getIdPeminjaman() {
        return id_peminjaman;
    }

    public int getId_anggota() {
        return id_anggota;
    }

    public String getNama_anggota() {
        return nama_anggota;
    }

    public int getIsbn() {
        return isbn_buku;
    }

    public String getJudul_buku() {
        return judulBuku;
    }

    public String getAlamat_peminjam() {
        return alamat_peminjam;
    }

    public String getTanggal_peminjaman() {
        return tanggal_peminjaman;
    }

    public String getTanggal_pengembalian() {
        return tanggal_pengembalian;
    }

    public String getStatus_peminjaman() {
        return status_peminjaman;
    }

}
