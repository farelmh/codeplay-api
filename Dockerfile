# --- STAGE 1: BUILD (Menggunakan Base Image yang memiliki Gradle dan JDK) ---
# Menggunakan base image dari Gradle/OpenJDK
FROM gradle:8-jdk17 AS build
WORKDIR /app

# Salin file konfigurasi Gradle untuk mengunduh dependensi
# Ini memungkinkan caching Docker yang lebih baik
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Menjadikan gradlew executable
RUN chmod +x gradlew

# Unduh dependensi (hanya terjadi jika build.gradle berubah)
# Perintah ini menjalankan 'compileJava' dan dependensi lainnya
RUN ./gradlew dependencies

# Salin sisa kode sumber
COPY src src

# Jalankan build Gradle untuk membuat JAR
# bootJar akan membuat JAR yang dapat dieksekusi
RUN ./gradlew bootJar --no-daemon

# --- STAGE 2: PACKAGE (Lingkungan Runtime yang Lebih Kecil) ---
# Menggunakan base image JRE (Java Runtime Environment) yang lebih ringan dan aman
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Ekspos port default Spring Boot
EXPOSE 8080

# Salin JAR yang dapat dieksekusi dari stage 'build'
# Nama JAR biasanya diatur oleh Gradle, cek di build.gradle Anda
# Ganti nama file JAR sesuai dengan output build Anda jika berbeda
COPY --from=build /app/build/libs/*.jar app.jar

# Perintah untuk menjalankan aplikasi Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]