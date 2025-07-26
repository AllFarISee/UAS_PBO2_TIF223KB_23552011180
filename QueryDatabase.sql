-- Tabel Pasien
CREATE TABLE pasien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    umur INT
);

-- Tabel Obat
CREATE TABLE obat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    harga DECIMAL(10, 2),
    stok INT
);

-- Tabel Tenaga Medis (untuk menyimpan informasi dokter dan apoteker)
CREATE TABLE tenaga_medis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    jabatan ENUM('Dokter', 'Apoteker') NOT NULL
);

-- Tabel Resep
CREATE TABLE resep (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pasien_id INT,
    dokter_id INT,
    tanggal DATE,
    FOREIGN KEY (pasien_id) REFERENCES pasien(id),
    FOREIGN KEY (dokter_id) REFERENCES tenaga_medis(id)
);

-- Tabel Detail Resep
CREATE TABLE detail_resep (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resep_id INT,
    obat_id INT,
    jumlah INT,
    FOREIGN KEY (resep_id) REFERENCES resep(id),
    FOREIGN KEY (obat_id) REFERENCES obat(id)
);

-- Tabel Transaksi
CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pasien_id INT,
    total DECIMAL(10, 2),
    tanggal DATE,
    FOREIGN KEY (pasien_id) REFERENCES pasien(id)
);

