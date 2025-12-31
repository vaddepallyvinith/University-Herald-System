# University of Hyderabad Herald System

A comprehensive Article Management System featuring a hybrid architecture with both a **Java Console Interface** and a modern **Web Frontend**. This system allows users (Admin, Faculty, Viewers) to manage and view articles seamlessly across different interfaces while sharing a common data source.

## 🚀 Features

### **Web Frontend (New)**
-   **Modern UI**: Built with Glassmorphism aesthetics, responsive grid layout, and University of Hyderabad branding (Maroon & Gold).
-   **Role-Based Access**:
    -   **Admin**: Full Create, Read, Update, Delete (CRUD) capabilities.
    -   **Faculty**: Full CRUD capabilities.
    -   **Viewer**: Read-only access to articles.
-   **Dynamic Interactions**: Real-time updates without page reloads using vanilla JavaScript and Fetch API.
-   **Smooth Animations**: Background blob animations and interactive hover effects.

### **Java Console (Legacy/Core)**
-   Robust command-line interface for terminal-based management.
-   Direct file handling logic.

### **Shared Data Persistence**
-   Both systems read from and write to the same `users.txt` and `articles.txt` files, ensuring data consistency regardless of the interface used.

---

## 🛠️ Technology Stack

-   **Frontend**: HTML5, CSS3 (Custom Glassmorphism), JavaScript (Vanilla)
-   **Backend**: Node.js, Express.js (REST API)
-   **Persistence**: File-based storage (`.txt`)
-   **Core Logic**: Java (Optional usage)

---

## 📂 Project Structure

```
├── public/              # Frontend static files
│   ├── index.html       # Single Page Application entry
│   ├── style.css        # Custom styles (Maroon & Gold Theme)
│   └── app.js           # Frontend logic & API calls
├── article/             # Original Java source code
│   ├── UoHHeraldSystem1.java
│   ├── Article.java
│   └── User.java
├── server.js            # Node.js Backend Server
├── users.txt            # User Database (CSV format)
├── articles.txt         # Article Database (Delimited format)
└── README.md            # Project Documentation
```

---

## 🚀 Getting Started

### Prerequisites
-   **Node.js** (v14 or higher)
-   **Java Development Kit (JDK)** (Optional, for running Java version)

### Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/uoh-herald-system.git
    cd uoh-herald-system
    ```

2.  **Install Node.js dependencies**:
    ```bash
    npm install express cors body-parser
    ```

### Running the Web Application

1.  **Start the Server**:
    ```bash
    node server.js
    ```
    The server will start at `http://localhost:3000`.

2.  **Access the App**:
    Open your browser and navigate to `http://localhost:3000`.

### Credentials

| Role    | User ID   | Password  |
| :------ | :-------- | :-------- |
| Admin   | `admin01` | `adminpass`|
| Faculty | `fac123`  | `facpass1` |
| Faculty | `fac456`  | `facpass2` |

---

## 📝 API Endpoints

-   `POST /api/login` - User authentication
-   `GET /api/articles` - Retrieve all articles
-   `POST /api/articles` - Create a new article
-   `PUT /api/articles` - Update an existing article
-   `DELETE /api/articles` - Delete an article

---

## 🎓 University Branding
The application is themed with the official **University of Hyderabad** colors:
-   **Primary**: Gold (`#fbbf24`)
-   **Background**: Deep Maroon (`#2a0a10`)

---

## 📄 License
This project is open-source and available under the MIT License.
