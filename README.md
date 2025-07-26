# UAS Pemrograman Berorientasi Objek 2
- Mata Kuliah: Pemrograman Berorientasi Obyek 2
- Dosen Pengampu: [Muhammad Ikhwan Fathulloh](https://github.com/Muhammad-Ikhwan-Fathulloh)

### Profil
- Nama: {Alfarisi Azhar}
- NIM: {23552011180}
- Studi Kasus: {Kasir Apotek}

## Judul Studi Kasus
Aplikasi Pegelola Kasir Apotek dengan fitur Login

## Penjelasan Studi Kasus
##### 1. Latar Belakang
Apotek merupakan fasilitas pelayanan kesehatan yang menyediakan obat-obatan kepada pasien, baik dengan resep dari dokter maupun pembelian langsung. Dalam operasional sehari-hari, dibutuhkan sistem yang dapat:
- Mengelola data pasien dan tenaga medis
- Mengelola data obat dan stok
- Memproses pembelian obat berdasarkan resep atau langsung
- Mencatat transaksi secara otomatis
Untuk itu, dibuatlah aplikasi Kasir Apotek berbasis Java dengan paradigma OOP (Object-Oriented Programming) dan koneksi ke database MySQL.

##### 2.	Tujuan
Membangun aplikasi kasir apotek yang:
- Menerapkan prinsip OOP (encapsulation, inheritance, polymorphism, abstract class)
- Mempermudah proses transaksi dan pencatatan
- Menjamin integritas data melalui relasi tabel di database
- Memberikan kemudahan verifikasi resep dan stok obat

##### 3.	Fitur Aplikasi Fitur utama aplikasi
- Menampilkan daftar pasien dan obat
- Menambah, mengedit, dan menghapus data pasien atau obat (melalui DAO)
- Proses transaksi pembelian obat:
- Dengan resep dokter: Verifikasi resep → Hitung total → Catat transaksi
- Pembelian langsung: Pilih obat dan jumlah → Hitung total → Catat transaksi
- Penyimpanan semua data ke dalam MySQL Database

##### 4.	Struktur OOP aplikasi ini menggunakan konsep OOP sebagai berikut
- Encapsulation: Setiap class (Pasien, Obat, Resep, dll.) memiliki atribut private dan getter/setter.
- Inheritance: TenagaMedis sebagai superclass, diwarisi oleh Dokter dan Apoteker.
- Polymorphism: Interface MetodeResep diimplementasikan oleh class ResepDenganDokter untuk proses resep.
- Abstract Class (opsional): Bisa digunakan bila ingin membuat dasar umum untuk entitas medis.

##### 5.	Struktur Database pasien: Menyimpan data pasien
- tenaga_medis: Data dokter dan apoteker
- obat: Data obat dan stok
- resep & detail_resep: Resep pasien dan detail obat yang diresepkan
- transaksi: Catatan pembelian pasien
- Relasi menggunakan foreign key untuk menjaga integritas data, contohnya:
resep.dokter_id → tenaga_medis.id
detail_resep.obat_id →  obat.id

##### 6.	Proses Bisnis Petugas memilih transaksi dengan resep atau langsung
Jika dengan resep:
Masukkan ID pasien → tampilkan resep → hitung harga berdasarkan isi resep
Simpan data transaksi
Jika langsung:
Pilih obat dan jumlah → hitung total → simpan transaksi
Semua data otomatis tersimpan di database

##### 7.	Manfaat Sistem Mengurangi kesalahan pencatatan manual
- Mempercepat proses transaksi apotek
- Memudahkan pemantauan data pasien, obat, dan transaksi
- Fleksibel untuk pengembangan lebih lanjut (seperti cetak struk, login, laporan)

## Penambahan Fitur Login pada Aplikasi Kasir Apotek (Java OOP)
Menambahkan fitur login untuk membatasi akses pengguna hanya kepada yang memiliki akun. Fitur ini akan memverifikasi username dan password melalui database sebelum mengizinkan akses ke menu utama.

Terdapat sebuah class model User yang merepresentasikan data pengguna. Class ini berisi atribut seperti id, username, password, dan role, serta getter untuk masing-masing atribut.
```java
public class User {… 
public User(int id, String username, String password, String role) 
…}
```

Kemudian, dibuat class UserDAO yang bertanggung jawab mengakses data user dari database. Method login() akan mencocokkan username dan password pengguna dengan data di tabel users, sedangkan register() digunakan untuk menambahkan user baru ke dalam database jika username belum digunakan.
Login :
``` java
public static User login(Connection conn, String username, String password) throws SQLException { 
String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
```
Register :
```java
public static boolean register(Connection conn, String username, String password, String role) throws SQLException { 
String check = "SELECT * FROM users WHERE username = ?";
```
Fitur tambahan berupa registrasi juga dapat dimasukkan agar pengguna baru dapat membuat akun sendiri. Proses ini akan meminta username, password, dan role (misalnya "admin" atau "kasir"), lalu menyimpannya ke database jika username belum digunakan.
```java
boolean sukses = UserDAO.register(connection, username, password, role);
if (sukses) { System.out.println("Registrasi berhasil!"); } 
else {System.out.println("Username sudah digunakan.");}
```
Dengan penambahan fitur login ini, aplikasi Kasir Apotek menjadi lebih aman karena hanya pengguna terverifikasi yang dapat mengakses sistem. Selain itu, penggunaan role memungkinkan pengembangan ke arah sistem manajemen multi-level, seperti membedakan hak akses antara admin dan kasir.

## Penjelasan 4 Pilar OOP dalam Studi Kasus
##### 1. Inheritance
Penjelasan: Inheritance memungkinkan kita membuat class baru (child) yang mewarisi properti dan method dari class yang sudah ada (parent).
Cara Kerja di Aplikasi: Class Dokter dan Apoteker mewarisi dari class TenagaMedis:
```java
public class Dokter extends TenagaMedis { ... }
public class Apoteker extends TenagaMedis { ... }
```
Dengan ini, Dokter dan Apoteker otomatis memiliki atribut seperti id, nama, dan jabatan, serta bisa memiliki method tambahan sesuai kebutuhan masing-masing.

##### 2. Encapsulation
Penjelasan: Encapsulation adalah proses menyembunyikan data (atribut) suatu objek agar tidak bisa diakses langsung dari luar class, kecuali melalui method khusus seperti getter dan setter.
Cara Kerja di Aplikasi: Class seperti Pasien, Obat, dan TenagaMedis memiliki atribut private, contoh:
private String nama untuk mengakses atau mengubah nilai nama, digunakan method
```java
public String getNama() { return nama; } 
public void setNama(String nama) { this.nama = nama; }
```
Tujuannya untuk menjaga agar data tidak diubah sembarangan, dan bisa divalidasi terlebih dahulu di dalam setter jika dibutuhkan.

##### 3. Polymorphism
Penjelasan: Polymorphism memungkinkan sistem untuk menggunakan satu interface atau superclass untuk berbagai implementasi berbeda.
Cara Kerja di Aplikasi: Interface MetodeResep memiliki beberapa implementasi, misalnya ResepDenganDokter.
Saat dijalankan:
```java
MetodeResep metode = new ResepDenganDokter(pasienId, resepId); metode.verifikasi(connection); metode.hitungTotal(connection);
```
Dengan cara ini jenis resep lain bisa ditambahkan tanpa mengubah kode utama. Ini membuat aplikasi fleksibel terhadap perubahan atau penambahan fitur baru.

##### 4. Abstract
Penjelasan: Abstraction menyembunyikan detail kompleks dari pengguna dan hanya menunjukkan bagian penting dari suatu objek.
Cara Kerja di Aplikasi: Interface MetodeResep adalah abstraksi dari cara memproses resep:
```java
public interface MetodeResep { boolean verifikasi(Connection connection); double hitungTotal(Connection connection);}
```
User tidak perlu tahu detail cara menghitung total atau proses verifikasi, cukup memanggil method-nya.


Demo Proyek

•	Github: https://github.com/AllFarISee/UAS_PBO2_TIF223KB_23552011180.git

•	Video: https://drive.google.com/drive/folders/1iTrjulR75YJb8SDD8P5zTVDSEbW3hi7e?usp=sharing

