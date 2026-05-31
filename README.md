# GreenLoop Management System

GreenLoop Management System is a Java Swing desktop application created for an eco-friendly packaging supply company.  
The system helps manage products, clients, and inventory records using a simple desktop interface connected to MongoDB.

This project was built as a beginner-friendly Java desktop application using IntelliJ IDEA, Maven, Java Swing, and MongoDB.

---

## Project Overview

GreenLoop is designed for a company that sells sustainable packaging products such as recycled boxes, compostable bags, biodegradable wraps, and reusable packaging materials.

The application focuses on three main features:

1. Product Catalogue Management
2. Client Management
3. Inventory / Stock Management

The goal of this project is to practice Java desktop application development, basic CRUD operations, MongoDB integration, and clean project structure.

---

## Features

### 1. Product Management

The Product Management feature allows the user to:

- Add new products
- View all products
- Update existing product details
- Delete products
- Search products
- Store product data in MongoDB

Product fields:

- Product ID
- Product Name
- Category
- Price
- Eco Rating
- Description

---

### 2. Client Management

The Client Management feature allows the user to:

- Add new clients
- View all clients
- Update client details
- Delete clients
- Search clients
- Store client data in MongoDB

Client fields:

- Client ID
- Client Name
- Business Name
- Phone
- Email
- Address

---

### 3. Inventory Management

The Inventory Management feature allows the user to:

- Add stock records
- View inventory records
- Update stock details
- Delete stock records
- Search inventory
- Check low-stock items
- Store inventory data in MongoDB

Inventory fields:

- Inventory ID
- Product Name
- Quantity on Hand
- Reorder Level
- Supplier Name
- Stock Status

Stock status is calculated automatically:

- If quantity is less than or equal to reorder level, status is `Low Stock`
- Otherwise, status is `Available`

---

## Technology Stack

| Area | Technology |
|---|---|
| Programming Language | Java |
| UI Framework | Java Swing |
| IDE | IntelliJ IDEA |
| Build Tool | Maven |
| Database | MongoDB |
| Database GUI | MongoDB Compass |
| Java Version | JDK 21 |

---

## Project Structure

```text
GreenLoopManagementSystem/
│
├── pom.xml
│
└── src/
    └── main/
        └── java/
            │
            ├── Main.java
            ├── MongoTest.java
            │
            ├── db/
            │   └── MongoConnection.java
            │
            ├── model/
            │   ├── Product.java
            │   ├── Client.java
            │   └── Inventory.java
            │
            ├── dao/
            │   ├── ProductDAO.java
            │   ├── ClientDAO.java
            │   └── InventoryDAO.java
            │
            └── ui/
                ├── DashboardFrame.java
                ├── ProductPanel.java
                ├── ClientPanel.java
                └── InventoryPanel.java
```

---

## Package Explanation

| Package | Purpose |
|---|---|
| `db` | Handles MongoDB connection |
| `model` | Contains data classes such as Product, Client, and Inventory |
| `dao` | Contains database access logic |
| `ui` | Contains Java Swing user interface screens |

---

## DAO and Model Explanation

| Concept | Java Term | C# Equivalent | Main Job |
|---|---|---|---|
| Data Carrier | `Model`, for example `Product.java` | DTO / Model | Carries data from one layer to another |
| Database Worker | `DAO`, for example `ProductDAO.java` | Repository / DAL | Handles CRUD operations with the database |

---

## MongoDB Database Design

Database name:

```text
greenloop_db
```

Collections:

```text
products
clients
inventory
```

### Products Collection Example

```json
{
  "productId": "P001",
  "name": "Recycled Cardboard Box",
  "category": "Boxes",
  "price": 12.5,
  "ecoRating": 5,
  "description": "Strong recycled packaging box"
}
```

### Clients Collection Example

```json
{
  "clientId": "C001",
  "clientName": "John Perera",
  "businessName": "Green Mart",
  "phone": "0771234567",
  "email": "john@greenmart.com",
  "address": "Colombo"
}
```

### Inventory Collection Example

```json
{
  "inventoryId": "INV001",
  "productName": "Recycled Cardboard Box",
  "quantity": 100,
  "reorderLevel": 20,
  "supplierName": "EcoPack Suppliers",
  "status": "Available"
}
```

---

## How to Run the Project

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd GreenLoopManagementSystem
```

### 2. Open in IntelliJ IDEA

Open IntelliJ IDEA and select:

```text
File → Open → GreenLoopManagementSystem
```

Wait until Maven finishes loading the project.

### 3. Install MongoDB Community Server

Make sure MongoDB Community Server is installed and running.

The local MongoDB connection URI should be:

```text
mongodb://localhost:27017
```

### 4. Open MongoDB Compass

Open MongoDB Compass and connect using:

```text
mongodb://localhost:27017
```

Create the database manually if needed:

```text
Database Name: greenloop_db
Collection Name: products
```

MongoDB can also create collections automatically when data is inserted from the Java app.

### 5. Run the Application

Open:

```text
src/main/java/Main.java
```

Run the `main()` method.

The GreenLoop desktop window should open with three tabs:

```text
Products | Clients | Inventory
```

---

## Maven Dependency

This project uses the MongoDB Java Sync Driver.

```xml
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>5.4.0</version>
</dependency>
```

---

## Testing Data

### Sample Product

```text
Product ID: P001
Product Name: Recycled Cardboard Box
Category: Boxes
Price: 12.50
Eco Rating: 5
Description: Strong recycled packaging box
```

### Sample Client

```text
Client ID: C001
Client Name: John Perera
Business Name: Green Mart
Phone: 0771234567
Email: john@greenmart.com
Address: Colombo
```

### Sample Inventory Item

```text
Inventory ID: INV001
Product Name: Recycled Cardboard Box
Quantity on Hand: 100
Reorder Level: 20
Supplier Name: EcoPack Suppliers
```

### Sample Low Stock Item

```text
Inventory ID: INV002
Product Name: Compostable Trash Bag
Quantity on Hand: 10
Reorder Level: 25
Supplier Name: Green Supplier Lanka
```

This should show the status as:

```text
Low Stock
```
---

## Current Status

| Feature | Status |
|---|---|
| Basic Swing window | Completed |
| Header and tab layout | Completed |
| MongoDB connection | Completed |
| Product Management | Completed |
| Client Management | Completed |
| Inventory Management | Completed |

---

## Future Improvements

Possible future improvements:

- Add login screen
- Add better UI styling
- Add product dropdown in Inventory instead of manual product name
- Add report generation
- Add order management
- Add PDF export
- Add role-based access
- Add better validation
- Add unit tests

---

## Author

Created as a Java Swing learning project for the GreenLoop Management System.