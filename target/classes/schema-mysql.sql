-- Pharmacy Medicine Inventory & Emergency Supply System
-- MySQL Schema Definition
-- Database: pharmacy_inventory_db

CREATE DATABASE IF NOT EXISTS pharmacy_inventory_db;
USE pharmacy_inventory_db;

-- 1. Users Table (RBAC: ADMIN, PHARMACIST, STAFF, SUPPLIER)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    department VARCHAR(150),
    phone VARCHAR(50),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Medicine Categories Table
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT
);

-- 3. Medicines Catalog Table
CREATE TABLE IF NOT EXISTS medicines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    generic_name VARCHAR(255) NOT NULL,
    sku VARCHAR(100) NOT NULL UNIQUE,
    category_id BIGINT,
    dosage_form VARCHAR(100) NOT NULL,
    strength VARCHAR(100),
    manufacturer VARCHAR(200),
    unit_price DECIMAL(10,2),
    min_stock_threshold INT NOT NULL DEFAULT 20,
    is_critical BOOLEAN DEFAULT FALSE,
    storage_condition VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- 4. Batches Table (FEFO inventory tracking)
CREATE TABLE IF NOT EXISTS batches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medicine_id BIGINT NOT NULL,
    batch_number VARCHAR(100) NOT NULL UNIQUE,
    initial_quantity INT NOT NULL,
    current_quantity INT NOT NULL,
    manufacturing_date DATE,
    expiry_date DATE NOT NULL,
    unit_cost DECIMAL(10,2),
    storage_location VARCHAR(150),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE
);

-- 5. Emergency Requests Table
CREATE TABLE IF NOT EXISTS emergency_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_number VARCHAR(100) NOT NULL UNIQUE,
    medicine_id BIGINT NOT NULL,
    requested_by_user_id BIGINT NOT NULL,
    department VARCHAR(150) NOT NULL,
    requested_quantity INT NOT NULL,
    allocated_quantity INT DEFAULT 0,
    urgency_level VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    clinical_reason TEXT,
    bed_or_patient_id VARCHAR(100),
    required_by DATETIME,
    approved_by_user_id BIGINT,
    pharmacist_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id),
    FOREIGN KEY (requested_by_user_id) REFERENCES users(id),
    FOREIGN KEY (approved_by_user_id) REFERENCES users(id)
);

-- 6. Request Batch Allocations Table
CREATE TABLE IF NOT EXISTS request_allocations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id BIGINT NOT NULL,
    batch_id BIGINT NOT NULL,
    allocated_quantity INT NOT NULL,
    allocated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (request_id) REFERENCES emergency_requests(id) ON DELETE CASCADE,
    FOREIGN KEY (batch_id) REFERENCES batches(id)
);

-- 7. Dispatch Records Table
CREATE TABLE IF NOT EXISTS dispatch_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dispatch_number VARCHAR(100) NOT NULL UNIQUE,
    request_id BIGINT NOT NULL UNIQUE,
    dispatched_by_user_id BIGINT NOT NULL,
    dispatched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    carrier_name VARCHAR(150) NOT NULL,
    destination_department VARCHAR(150) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'IN_TRANSIT',
    received_by_user_id BIGINT,
    received_at DATETIME,
    handling_instructions TEXT,
    verification_code VARCHAR(20),
    FOREIGN KEY (request_id) REFERENCES emergency_requests(id),
    FOREIGN KEY (dispatched_by_user_id) REFERENCES users(id),
    FOREIGN KEY (received_by_user_id) REFERENCES users(id)
);

-- 8. Stock Movement Audit Log Table
CREATE TABLE IF NOT EXISTS stock_movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medicine_id BIGINT NOT NULL,
    batch_id BIGINT,
    movement_type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    balance_after INT NOT NULL,
    reference_number VARCHAR(100),
    performed_by_user_id BIGINT,
    remarks TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id),
    FOREIGN KEY (batch_id) REFERENCES batches(id) ON DELETE SET NULL,
    FOREIGN KEY (performed_by_user_id) REFERENCES users(id)
);

-- 9. Supplier Orders Table
CREATE TABLE IF NOT EXISTS supplier_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100) NOT NULL UNIQUE,
    medicine_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    supplier_user_id BIGINT,
    supplier_company_name VARCHAR(255),
    status VARCHAR(50) NOT NULL DEFAULT 'PLACED',
    issued_batch_number VARCHAR(100),
    issued_expiry_date DATE,
    issued_manufacturing_date DATE,
    ordered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivered_at DATETIME,
    notes TEXT,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id),
    FOREIGN KEY (supplier_user_id) REFERENCES users(id)
);
