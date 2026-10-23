# PharmaShield: Pharmacy Medicine Inventory & Emergency Supply System

> A full-stack healthcare web application designed to eliminate medication stockouts, automate clinical emergency supply requests, enforce intelligent **FEFO (First Expired, First Out)** inventory allocation, and execute live transactional stock deductions upon dispatch.

---

## 🏥 1. System Architecture & Tech Stack

```
   ┌─────────────────────────────────────────────────────────┐
   │                   React.js Frontend                     │
   │  (Vite + React 18 + Lucide Icons + Custom Medical CSS)  │
   │  - Live Dashboard & Alert Center                        │
   │  - 1-Click Demo Role Switcher (Admin/Pharm/Staff/Supp) │
   │  - FEFO Match & Allocation Modals                       │
   │  - Printable Emergency Dispatch Gate Pass Slips         │
   └───────────────────────────┬─────────────────────────────┘
                               │ HTTP / REST (JWT Auth)
                               ▼
   ┌─────────────────────────────────────────────────────────┐
   │                Spring Boot 3.3.5 Backend                │
   │  - Java 17/24 Clean POJO Architecture (No Lombok dep)   │
   │  - Spring Security 6 (Stateless JWT + RBAC)             │
   │  - Spring Data JPA with @Transactional deduction        │
   │  - Intelligent FEFO Recommendation Engine               │
   │  - Real-time Alert & Audit Movement Engine              │
   └───────────────────────────┬─────────────────────────────┘
                               │
            ┌──────────────────┴──────────────────┐
            ▼                                     ▼
   ┌─────────────────────────────┐   ┌─────────────────────────────┐
   │   H2 In-Memory (Default)    │   │   MySQL Database (Prod)     │
   │   - Zero-config instant run │   │   - `application-mysql.properties`
   │   - MySQL compatibility mode│   │   - `schema-mysql.sql`      │
   │   - Auto-seeded test data   │   │   - `data-mysql.sql`        │
   └─────────────────────────────┘   └─────────────────────────────┘
```

- **Frontend:** React 18, Vite 5, Axios, Lucide React icons, Inter & JetBrains Mono typography.
- **Backend:** Java 24 / 17, Spring Boot 3.3.5, Spring Data JPA, Spring Security 6, JJWT 0.11.5.
- **Database:** Dual support for MySQL (`pharmacy_inventory_db`) and embedded H2 (MySQL Mode) for instant zero-configuration testing.
- **Security:** Stateless JWT authentication with Bearer tokens and `@PreAuthorize` role access control.

---

## 👥 2. User Roles & Access Control (RBAC)

The system supports 4 authenticated roles with tailored capabilities:

| Role | Demo Credentials | Primary Responsibilities |
| :--- | :--- | :--- |
| **Admin** | `admin@pharmacy.com` / `password123` | Formulary & medicine catalog management, threshold definitions, user management, and executive audit oversight. |
| **Pharmacist** | `pharmacist@pharmacy.com` / `password123` | Stock monitoring, expiry verification, FEFO batch matching, batch allocation, and authorizing physical dispatches. |
| **Staff (ICU / ER / OT)** | `icu@hospital.com` / `password123`<br>`er@hospital.com` / `password123` | Submitting emergency supply requests for crash carts, clinical justifications, urgency ranking (CRITICAL / HIGH / MEDIUM), and delivery confirmation. |
| **Supplier** | `supplier@medisupply.com` / `password123` | Receiving replenishment purchase orders, dispatching new batches with manufacturing and expiry dates directly into inventory. |

> **Pro-Tip:** The frontend includes a **1-Click Quick Role Switcher** in the top navigation bar. You can switch between Admin, Pharmacist, Staff, and Supplier instantly without logging out.

---

## ⚙️ 3. Core Functional Workflows

### A. Intelligent FEFO (First Expired, First Out) Allocation
1. When hospital wards request medication, the system checks all active batches for that medicine with `expiryDate >= today` and `currentQuantity > 0`.
2. Batches are sorted ascending by expiration date:
   $$\text{Batch Priority} = \min(\text{Expiry Date})$$
3. Batches with the nearest valid expiration date are allocated first to minimize expired pharmaceutical waste.
4. If a single batch cannot satisfy the requested quantity, the algorithm automatically splits and pulls the balance from the next earliest batch.

### B. Live Inventory Deduction & Dispatch Slip Generation
1. Once stock is allocated, the Pharmacist executes the dispatch.
2. The transaction (`@Transactional`) immediately deducts the issued quantity from the specific batches:
   $$\text{Remaining Batch Stock} = \text{Initial} - \sum \text{Dispatched}$$
3. An immutable audit record is appended to `StockMovement` (type: `DISPATCH`, negative quantity, updated balance).
4. An **Emergency Dispatch Slip / Gate Pass** is generated with a unique 4-digit verification code, assigned porter, cold chain handling instructions, and simulated QR verification.
5. The receiving ward enters the verification code or confirms delivery, marking the request as `DELIVERED`.

### C. Low Stock & Expiry Alerts Engine
- **Low Stock Threshold Alert:** Triggered whenever a medicine's active stock drops below its predefined threshold (`minStockThreshold`).
- **Stock Depletion Alert:** Triggered when active non-expired stock reaches 0 units.
- **Expiring Soon Warning:** Highlights batches expiring within 30 days so they can be prioritized for dispatch.
- **Expired Quarantine Alert:** Flagged batches that have passed expiration are automatically barred from allocation and can be quarantined or discarded with 1 click.
- **Critical Crash Cart Alert:** Prioritizes resuscitation and cardiac medications (< 15 min turnaround).

---

## 🚀 4. Quick Start & Execution

### Option 1: 1-Click Launch (Windows)
Double-click:
```bat
start-all.bat
```
This automatically starts both the Spring Boot backend on `http://localhost:8080` and the React frontend on `http://localhost:3000`, then opens your default browser.

### Option 2: Running Components Individually

#### Backend:
```powershell
cd backend
java -jar target\pharmacy-inventory-system-1.0.0.jar
```
*(Or compile from source: `mvn clean package -DskipTests`)*

#### Frontend:
```powershell
cd frontend
npm run dev
```

---

## 🌐 5. Default URLs & Endpoints

- **Web Application Portal:** [http://localhost:3000](http://localhost:3000)
- **Backend REST API:** [http://localhost:8080/api](http://localhost:8080/api)
- **Embedded Database Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:pharmacy_inventory_db`
  - User: `sa`
  - Password: *(leave blank)*

---

## 🗄️ 6. MySQL Database Setup (Optional)

If running a local MySQL server on port 3306:
1. Import `backend/src/main/resources/schema-mysql.sql` and `data-mysql.sql` using MySQL Workbench or CLI.
2. In `backend/src/main/resources/application-mysql.properties`, adjust your MySQL username and password (default `root` / `root`).
3. Run backend with the MySQL profile:
   ```powershell
   java -jar target\pharmacy-inventory-system-1.0.0.jar --spring.profiles.active=mysql
   ```

---

## 🧪 7. Verification & Test Walkthrough

1. **Sign in as Staff (`icu@hospital.com`):**
   - Click **+ Submit Emergency Request**.
   - Select **Epinephrine Injection 1mg/mL**, Urgency: **CRITICAL**, Qty: **5**, Reason: *"Severe anaphylaxis bay 2"*.
   - Submit request -> System registers request `REQ-EMG-...`.
2. **Switch to Pharmacist (`pharmacist@pharmacy.com`):**
   - Open **Emergency Requests**.
   - Notice the CRITICAL alert tag. Click **Auto-Match FEFO**.
   - The system inspects batches and allocates 5 units from `BAT-EPI-2026A` (earliest expiry batch).
   - Click **Prepare Dispatch & Deduct Stock**.
3. **Dispatch & Deduction:**
   - Input carrier name and submit.
   - Live inventory of `BAT-EPI-2026A` is automatically deducted from 22 units to 17 units.
   - An official **Emergency Dispatch Slip** pops up with verification PIN.
4. **Delivery Receipt:**
   - Switch back to ICU Staff -> Click **Confirm Receipt**.
   - Request and Dispatch records are marked **DELIVERED**.
5. **Auditing & Analytics:**
   - Navigate to **Stock & Expiry Alerts** to inspect low-stock warnings and expiring lots.
   - Navigate to **Analytics & Reports** to review category distribution charts, department demand breakdown, and export the complete transactional audit trail to CSV.
