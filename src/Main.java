import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Gagal koneksi ke database.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MENU AWAL ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Pilih menu (1-2): ");
        int menuAwal = scanner.nextInt();
        scanner.nextLine(); // flush newline

        if (menuAwal == 1) {
            registerUser(connection, scanner);
        }
        // === LOGIN ===
        System.out.println("=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = null;
        try {
            user = UserDAO.login(connection, username, password);
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat login.");
            return;
        }

        if (user == null) {
            System.out.println("Login gagal! Username atau password salah.");
            return;
        }

        System.out.println("Login berhasil. Selamat datang, " + user.getUsername() + " (" + user.getRole() + ")");
        boolean running = true;

        while (running) {
            System.out.println("\n==== Menu Kasir Apotek ====");
            System.out.println("1. Lihat Pasien");
            System.out.println("2. Lihat Obat");
            System.out.println("3. Proses Transaksi dengan Resep");
            System.out.println("4. Proses Pembelian Langsung");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // flush

            switch (pilihan) {
                case 1:
                    tampilkanPasien(connection);
                    break;
                case 2:
                    tampilkanObat(connection);
                    break;
                case 3:
                    prosesTransaksiDenganResep(scanner, connection);
                    break;
                case 4:
                    prosesPembelianLangsung(scanner, connection);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }

        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Gagal menutup koneksi database.");
        }
    }

    private static void tampilkanPasien(Connection connection) {
        try {
            List<Pasien> daftar = PasienDAO.getAllPasien(connection);
            daftar.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Gagal menampilkan data pasien.");
        }
    }

    private static void tampilkanObat(Connection connection) {
        try {
            List<Obat> daftar = ObatDAO.getAllObat(connection);
            daftar.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Gagal menampilkan data obat.");
        }
    }

    private static void prosesTransaksiDenganResep(Scanner scanner, Connection connection) {
        try {
            System.out.print("Masukkan ID Pasien: ");
            int pasienId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan ID Resep: ");
            int resepId = scanner.nextInt();
            scanner.nextLine();

            MetodeResep metode = new ResepDenganDokter(resepId, pasienId);

            if (!metode.verifikasi(connection)) {
                System.out.println("Resep tidak ditemukan atau tidak valid.");
                return;
            }

            double total = metode.hitungTotal(connection);
            System.out.println("Total tagihan: Rp" + total);

            TransaksiDAO.prosesTransaksi(connection, pasienId, total);
            System.out.println("Transaksi dengan resep berhasil!");
        } catch (Exception e) {
            System.out.println("Gagal memproses transaksi dengan resep.");
        }
    }

    private static void prosesPembelianLangsung(Scanner scanner, Connection connection) {
        try {
            System.out.print("Masukkan ID Pasien: ");
            int pasienId = scanner.nextInt();
            scanner.nextLine();

            List<Obat> daftar = ObatDAO.getAllObat(connection);
            daftar.forEach(System.out::println);

            System.out.print("Masukkan ID Obat yang ingin dibeli: ");
            int obatId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Masukkan jumlah: ");
            int jumlah = scanner.nextInt();
            scanner.nextLine();

            Obat obat = ObatDAO.getObatById(connection, obatId);
            double total = obat.getHarga() * jumlah;
            System.out.println("Total tagihan: Rp" + total);

            TransaksiDAO.prosesTransaksi(connection, pasienId, total);
            System.out.println("Pembelian langsung berhasil!");
        } catch (Exception e) {
            System.out.println("Gagal memproses pembelian langsung.");
        }
    }
    private static void registerUser(Connection connection, Scanner scanner) {
        try {
            System.out.println("\n=== REGISTER USER ===");
            System.out.print("Masukkan username baru: ");
            String username = scanner.nextLine();
            System.out.print("Masukkan password: ");
            String password = scanner.nextLine();
            System.out.print("Masukkan role (admin/kasir): ");
            String role = scanner.nextLine();
    
            boolean success = UserDAO.register(connection, username, password, role);
            if (success) {
                System.out.println("Registrasi berhasil. Silakan login.");
            } else {
                System.out.println("Registrasi gagal. Username mungkin sudah digunakan.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat registrasi.");
        }
    }
}


