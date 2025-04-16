# ClickHouse-Tool

A user-friendly web application for transferring data between ClickHouse databases and CSV files. This tool allows users to easily export data from ClickHouse to CSV files and import data from CSV files into ClickHouse tables.

---

## ✨ Features

- **Bi-directional Data Transfer**:
  - Import CSV files into ClickHouse tables
  - Export ClickHouse tables to CSV files
- **Interactive Column Selection**: Choose which columns to include in your data transfers
- **Data Preview**: View sample data before executing transfers
- **Auto Table Creation**: Automatically generate ClickHouse tables based on uploaded CSV structure
- **Flexible Delimiter Support**: Work with various CSV delimiters (comma, semicolon, tab, or custom)
- **Error Reporting**: User-friendly messages for validation and processing errors
- **Responsive Design**: Works across devices using Tailwind CSS
- **Persistent Connections**: Maintain session-aware connections for smoother workflow
- **JWT-Based Login & Signup**: Secure access to data ingestion features

---

## 🎥 Demo Video
[Watch a quick demo of ClickSync in action](https://drive.google.com/file/d/1-HHlNcGpbFCDgX8F-ELOcvZX01GvlLFt/view?usp=sharing)

---

## ⚙️ System Architecture

- **Frontend**: HTML, CSS, JavaScript (no framework used)
- **Backend**: Spring Boot Java application using ClickHouse JDBC driver

---

## 🔧 Prerequisites

- **Java** (JDK 11 or higher)
- **Maven**
- **ClickHouse** server (local or remote)

---

## 🚀 Setup Instructions

### 🗄️ ClickHouse Server Setup (Docker Recommended)
```bash
docker pull clickhouse/clickhouse-server
docker run -d --name clickhouse-server -p 8123:8123 -p 9000:9000 clickhouse/clickhouse-server
```

---

### 🔙 Backend Setup
```bash
mvn clean package
mvn spring-boot:run
```
> ✅ Runs at `http://localhost:8080`

---

## 🧭 Usage Guide

### 📤 Export from ClickHouse to CSV

1. Choose ClickHouse source and table/columns
2. Preview data
3. Click **Export to CSV**

### 📥 Import CSV to ClickHouse

1. Upload CSV & select delimiter
2. Provide ClickHouse connection
3. Preview & select columns
4. Click **Import to ClickHouse**

---

## 🔐 Security Credentials

Username | Password
--- | ---
admin | admin123

Secured via Spring Security and JWT authentication.

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot (Java)
- **Database**: ClickHouse
- **Frontend**: HTML, CSS, JavaScript
- **Build Tool**: Maven
- **Containerization**: Docker

---

## 🧪 Testing Datasets

Use sample ClickHouse tables like `uk_price_paid` with columns such as:
- price
- postcode1
- date

---

## ✅ Test Cases

1. Export selected columns to flat file
2. Upload CSV and verify data in ClickHouse
3. (Bonus) Join tables and export to file
4. Handle auth/connection errors
5. Preview ClickHouse data

---

## 🧾 Logs & Debugging

- **Backend Logs**: Console output (Spring Boot)
- **Frontend Errors**: Use browser dev tools

---

## 📄 AI Assistance Acknowledgement

1. Used AI to break down project requirements into manageable modules (CSV parsing, ClickHouse integration, etc.)
2. Consulted AI for best practices around JDBC and secure large file uploads
3. Enhanced React-like component behavior and error handling using AI advice (applied with vanilla JS)
4. Streamlined workflow for intuitive usability (e.g., preview before execution)
5. Used AI to validate advanced features like auto table creation and edge-case handling
6. Refined documentation language and structure using AI guidance

---

## 🤝 Contributing

Pull requests welcome! For major changes, open an issue first.

---

## 🔗 References

- [ClickHouse Docs](https://clickhouse.com/docs)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/index.html)

---

## 📦 Repo Setup

```bash
git clone https://github.com/prernasharma28/dataingestion.git
cd dataingestion
```

Frontend and backend are in the same root project using plain HTML/CSS/JS.