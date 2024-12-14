# PBO-Project-Brick-Buster-KLP-16

### Penerapan GUI dan Multithreading dalam Aplikasi Brick Buster

## Deskripsi

project ini membahas penerapan komponen GUI (Graphical User Interface) menggunakan Java Swing, serta penggunaan multithreading untuk menjaga permainan tetap responsif dalam aplikasi _Brick Buster_. Aplikasi ini adalah game arkade yang memungkinkan pemain menghancurkan brick menggunakan bola yang dipantulkan oleh paddle, dengan fitur-fitur seperti power-up, leaderboard, dan beberapa level yang semakin menantang. Dokumentasi ini mencakup struktur kode utama, pengaturan tata letak GUI, dan implementasi Thread untuk mendukung gameplay.

## Struktur Kode Utama

### 1. MainContainer

Kelas ini adalah titik pusat navigasi antarlayar aplikasi menggunakan _CardLayout_. Beberapa panel utama dalam aplikasi:

- **MainMenu**: Menu utama untuk memulai permainan, melihat leaderboard, dan membaca deskripsi game di bagian _About_.
- **Gameplay**: Panel inti permainan yang mencakup paddle, bola, brick, power-up, dan logika level.
- **Leaderboard**: Panel untuk menampilkan daftar skor tertinggi pemain yang disimpan di database MySQL.
- **About**: Panel yang memberikan informasi singkat tentang aplikasi.

### 2. Gameplay

Panel inti untuk menjalankan logika permainan, termasuk:

- **Gerakan Bola**: Bola bergerak secara dinamis, memantul dari paddle, dinding, atau brick, yang dihancurkan saat terkena bola.
- **Power-Up**: Pemain dapat mengaktifkan power-up seperti Multi-Ball, Extend Paddle, Double Score, dan Quick Paddle.
- **Level Up**: Setelah semua brick hancur, pemain naik ke level berikutnya dengan jumlah brick dan kesulitan yang lebih tinggi.

### 3. MapGenerator

Kelas ini bertanggung jawab untuk membuat tata letak brick di setiap level permainan:

- **Konfigurasi Brick**: Brick dihasilkan secara dinamis sesuai dengan level permainan, dengan jumlah baris dan kolom yang meningkat.
- **Desain Brick**: Brick digambar menggunakan _Graphics2D_ dengan warna dan posisi yang disesuaikan.

### 4. PowerUp

Kelas ini mengelola mekanisme power-up yang muncul secara acak saat brick dihancurkan:

- **Jenis Power-Up**:
  - Multi-Ball: Menambahkan bola tambahan.
  - Extend Paddle: Memperbesar paddle untuk mempermudah memantulkan bola.
  - Double Score: Menggandakan skor untuk setiap brick yang dihancurkan.
  - Quick Paddle: Menambah kecepatan gerakan paddle.
- **Aktivasi dan Durasi**: Power-up aktif untuk waktu tertentu, dengan efek yang dinonaktifkan secara otomatis setelah habis durasinya.

### 5. DatabaseManager

Mengelola penyimpanan data pemain ke database MySQL:

- **Leaderboard**: Menyimpan nama pemain, skor tertinggi, dan level tertinggi ke tabel `Leaderboard`.
- **Pengambilan Data**: Mengambil 10 pemain dengan skor tertinggi untuk ditampilkan di panel leaderboard.

## Penggunaan Multithreading di Aplikasi

### 1. Gerakan Bola

Thread digunakan untuk memperbarui posisi bola secara terus-menerus tanpa mengganggu responsivitas antarmuka. Gerakan bola dikelola di dalam metode `run()` kelas _Gameplay_, yang memastikan pergerakan bola, interaksi dengan brick, paddle, dan power-up dilakukan dalam loop yang berjalan secara paralel.

### 2. Power-Up

Thread juga digunakan untuk mengatur durasi power-up. Ketika power-up diaktifkan, timer dimulai di Thread terpisah untuk memastikan power-up dinonaktifkan setelah beberapa detik, tanpa memengaruhi jalannya permainan.

### 3. Responsivitas Level Up

Saat semua brick di level tertentu dihancurkan, Thread digunakan untuk menunggu hingga pemain menekan tombol _Enter_ untuk melanjutkan ke level berikutnya.

### 4. Background Music (BGM) dan Sound Effect

Multithreading juga digunakan untuk menangani _background music_ (BGM) dan efek suara sehingga audio dapat diputar bersamaan dengan permainan tanpa mengganggu mekanisme inti permainan.

## Komponen Antarmuka Pengguna

### 1. MainMenu

Menu utama menampilkan tiga opsi dengan desain modern dan ikon:

- **Start Game**: Memulai permainan baru.
- **Leaderboard**: Menampilkan daftar skor tertinggi.
- **About**: Menampilkan informasi tentang aplikasi.

### 2. Gameplay

- **Paddle dan Bola**: Paddle dapat diperbesar dengan power-up, sementara bola bergerak dinamis sesuai interaksi dengan paddle dan brick.
- **Heads-Up Display (HUD)**: Menampilkan nama pemain, skor, dan level di bagian atas layar.
- **Brick dan Power-Up**: Brick yang dihancurkan memiliki kemungkinan menjatuhkan power-up secara acak.

### 3. Leaderboard

Panel ini menampilkan daftar skor tertinggi pemain dari database MySQL, dengan kolom untuk _Nama_, _Level_, dan _Score_.

### 4. About

Panel sederhana yang memberikan deskripsi singkat tentang game dan mekanismenya.

## Hasil Run Program

### Tampilan Menu Utama

![Tampilan Menu Utama](/assets/runProgram/menuUtama.png)  
_Menu utama dengan pilihan Start Game, Leaderboard, dan About._

### Tampilan Gameplay

![Tampilan Gameplay](/assets/runProgram/gameplay.png)  
_Permainan dengan paddle, bola, brick, dan power-up yang aktif._

### Tampilan Leaderboard

![Tampilan Leaderboard](/assets/runProgram/leaderboard.png)  
_Daftar skor tertinggi yang ditampilkan secara rapi._

### Tampilan About

![Tampilan About](/assets/runProgram/about.png)  
_Penjelasan mengenai permainan brick buster_

## Kesimpulan

Aplikasi _Brick Buster_ adalah contoh penerapan GUI dan multithreading dalam pengembangan game berbasis Java. Penggunaan Thread memungkinkan permainan berjalan secara responsif, dengan gerakan bola, interaksi power-up, dan transisi level yang mulus. Tata letak yang dikelola dengan baik, serta integrasi database untuk leaderboard, menambah nilai fungsionalitas aplikasi ini. Proyek ini menunjukkan pentingnya multithreading dalam menjaga pengalaman bermain yang optimal.
