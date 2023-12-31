public class Buku {
    public int isbn;
    public String judul_buku;
    private String pengarang;
    private int tahun_terbit;

    public Buku(int isbn, String judul_buku) {
        this.isbn = isbn;
        this.judul_buku = judul_buku;
    }

    public Buku(int isbn, String judul_buku, String pengarang, int tahun_terbit) {
        this.isbn = isbn;
        this.judul_buku = judul_buku;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
    }

    public int getISBN() {
        return isbn;
    }

    public String getJudul() {
        return judul_buku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public int getTahunTerbit() {
        return tahun_terbit;
    }
}
