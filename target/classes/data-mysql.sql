-- Sample Seed Data for MySQL
-- Default password for all users is: password123
-- Encrypted hash: $2a$10$w8uY9s8N8j6e5c5G6e4.5.3L8F8L5k1e2f3g4h5i6j7k8l9m0n1o

INSERT IGNORE INTO users (id, email, password, full_name, role, department, phone, active) VALUES
(1, 'admin@pharmacy.com', '$2a$10$y5KzCgWlV9f43fU2DvhgG.u0Y29dYgq4Y/V2L9P4M6gWw/o1U76dG', 'Dr. Evelyn Carter', 'ROLE_ADMIN', 'Pharmacy Administration', '+1 555-0101', true),
(2, 'pharmacist@pharmacy.com', '$2a$10$y5KzCgWlV9f43fU2DvhgG.u0Y29dYgq4Y/V2L9P4M6gWw/o1U76dG', 'Marcus Vance, PharmD', 'ROLE_PHARMACIST', 'Central Hospital Pharmacy', '+1 555-0102', true),
(3, 'icu@hospital.com', '$2a$10$y5KzCgWlV9f43fU2DvhgG.u0Y29dYgq4Y/V2L9P4M6gWw/o1U76dG', 'Nurse Sarah Jenkins, BSN', 'ROLE_STAFF', 'Intensive Care Unit (ICU)', '+1 555-0103', true),
(4, 'er@hospital.com', '$2a$10$y5KzCgWlV9f43fU2DvhgG.u0Y29dYgq4Y/V2L9P4M6gWw/o1U76dG', 'Dr. David Kim, MD', 'ROLE_STAFF', 'Emergency Department', '+1 555-0104', true),
(5, 'supplier@medisupply.com', '$2a$10$y5KzCgWlV9f43fU2DvhgG.u0Y29dYgq4Y/V2L9P4M6gWw/o1U76dG', 'Robert Sterling', 'ROLE_SUPPLIER', 'Apex Medical Logistics', '+1 555-0105', true);

INSERT IGNORE INTO categories (id, name, description) VALUES
(1, 'Emergency & Resuscitation', 'Crash cart medications, cardiac stimulants, and acute reversal agents'),
(2, 'Critical Care & Anesthesia', 'Sedatives, paralytics, and general anesthetics for ICU and Operating Theatres'),
(3, 'Broad-Spectrum Antibiotics', 'Potent anti-infective agents for sepsis and severe acute infections'),
(4, 'Cardiovascular & Vasopressors', 'Inotropes, vasopressors, and antiarrhythmic agents'),
(5, 'IV Fluids & Electrolytes', 'Crystalloids, volume expanders, and concentrated electrolyte infusions');
