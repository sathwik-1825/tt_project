package com.pharmacy.inventory.config;

import com.pharmacy.inventory.entity.*;
import com.pharmacy.inventory.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MedicineRepository medicineRepository;
    private final BatchRepository batchRepository;
    private final EmergencyRequestRepository requestRepository;
    private final RequestAllocationRepository allocationRepository;
    private final DispatchRecordRepository dispatchRecordRepository;
    private final StockMovementRepository stockMovementRepository;
    private final SupplierOrderRepository supplierOrderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           MedicineRepository medicineRepository,
                           BatchRepository batchRepository,
                           EmergencyRequestRepository requestRepository,
                           RequestAllocationRepository allocationRepository,
                           DispatchRecordRepository dispatchRecordRepository,
                           StockMovementRepository stockMovementRepository,
                           SupplierOrderRepository supplierOrderRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.medicineRepository = medicineRepository;
        this.batchRepository = batchRepository;
        this.requestRepository = requestRepository;
        this.allocationRepository = allocationRepository;
        this.dispatchRecordRepository = dispatchRecordRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.supplierOrderRepository = supplierOrderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already initialized.");
            return;
        }

        log.info("Initializing Pharmacy Medicine Inventory & Emergency Supply System data...");

        String encodedPass = passwordEncoder.encode("password123");

        User admin = User.builder()
                .email("admin@pharmacy.com")
                .password(encodedPass)
                .fullName("Dr. Evelyn Carter")
                .role(Role.ROLE_ADMIN)
                .department("Pharmacy Administration")
                .phone("+1 555-0101")
                .active(true)
                .build();

        User pharmacist = User.builder()
                .email("pharmacist@pharmacy.com")
                .password(encodedPass)
                .fullName("Marcus Vance, PharmD")
                .role(Role.ROLE_PHARMACIST)
                .department("Central Hospital Pharmacy")
                .phone("+1 555-0102")
                .active(true)
                .build();

        User icuStaff = User.builder()
                .email("icu@hospital.com")
                .password(encodedPass)
                .fullName("Nurse Sarah Jenkins, BSN")
                .role(Role.ROLE_STAFF)
                .department("Intensive Care Unit (ICU)")
                .phone("+1 555-0103")
                .active(true)
                .build();

        User erStaff = User.builder()
                .email("er@hospital.com")
                .password(encodedPass)
                .fullName("Dr. David Kim, MD")
                .role(Role.ROLE_STAFF)
                .department("Emergency Department")
                .phone("+1 555-0104")
                .active(true)
                .build();

        User supplier = User.builder()
                .email("supplier@medisupply.com")
                .password(encodedPass)
                .fullName("Robert Sterling")
                .role(Role.ROLE_SUPPLIER)
                .department("Apex Medical Logistics")
                .phone("+1 555-0105")
                .active(true)
                .build();

        userRepository.saveAll(List.of(admin, pharmacist, icuStaff, erStaff, supplier));

        Category catEmergency = categoryRepository.save(Category.builder()
                .name("Emergency & Resuscitation")
                .description("Crash cart medications, cardiac stimulants, and acute reversal agents")
                .build());

        Category catCritical = categoryRepository.save(Category.builder()
                .name("Critical Care & Anesthesia")
                .description("Sedatives, paralytics, and general anesthetics for ICU and Operating Theatres")
                .build());

        Category catAntibiotics = categoryRepository.save(Category.builder()
                .name("Broad-Spectrum Antibiotics")
                .description("Potent anti-infective agents for sepsis and severe acute infections")
                .build());

        Category catCardiovascular = categoryRepository.save(Category.builder()
                .name("Cardiovascular & Vasopressors")
                .description("Inotropes, vasopressors, and antiarrhythmic agents")
                .build());

        Category catIvFluids = categoryRepository.save(Category.builder()
                .name("IV Fluids & Electrolytes")
                .description("Crystalloids, volume expanders, and concentrated electrolyte infusions")
                .build());

        Medicine epinephrine = medicineRepository.save(Medicine.builder()
                .name("Epinephrine Injection 1mg/mL")
                .genericName("Epinephrine (Adrenaline)")
                .sku("MED-RES-001")
                .category(catEmergency)
                .dosageForm("Injection Ampoule")
                .strength("1 mg / 1 mL")
                .manufacturer("Pfizer Hospira")
                .unitPrice(new BigDecimal("12.50"))
                .minStockThreshold(40)
                .isCritical(true)
                .storageCondition("Protect from light, 20°C - 25°C")
                .description("First-line sympathomimetic agent for cardiac arrest, anaphylactic shock, and severe bronchospasm.")
                .build());

        Medicine atropine = medicineRepository.save(Medicine.builder()
                .name("Atropine Sulfate 0.1mg/mL")
                .genericName("Atropine Sulfate")
                .sku("MED-RES-002")
                .category(catEmergency)
                .dosageForm("Prefilled Syringe")
                .strength("1 mg / 10 mL")
                .manufacturer("Amphastar Pharmaceuticals")
                .unitPrice(new BigDecimal("18.75"))
                .minStockThreshold(30)
                .isCritical(true)
                .storageCondition("Room Temperature (15°C - 30°C)")
                .description("Anticholinergic agent for symptomatic bradycardia and organophosphate poisoning reversal.")
                .build());

        Medicine norepinephrine = medicineRepository.save(Medicine.builder()
                .name("Norepinephrine Bitartrate 4mg/4mL")
                .genericName("Norepinephrine")
                .sku("MED-VAS-003")
                .category(catCardiovascular)
                .dosageForm("Injection Vial")
                .strength("4 mg / 4 mL")
                .manufacturer("Hikma Pharmaceuticals")
                .unitPrice(new BigDecimal("35.00"))
                .minStockThreshold(25)
                .isCritical(true)
                .storageCondition("Protect from light, 20°C - 25°C")
                .description("Potent peripheral vasoconstrictor and inotropic stimulator for septic and cardiogenic shock.")
                .build());

        Medicine propofol = medicineRepository.save(Medicine.builder()
                .name("Propofol Injectable Emulsion 1%")
                .genericName("Propofol")
                .sku("MED-ANE-004")
                .category(catCritical)
                .dosageForm("Vial")
                .strength("10 mg / mL (20 mL)")
                .manufacturer("Fresenius Kabi")
                .unitPrice(new BigDecimal("22.00"))
                .minStockThreshold(50)
                .isCritical(true)
                .storageCondition("Room Temperature (4°C - 22°C), Do not freeze")
                .description("Short-acting intravenous general anesthetic and procedural sedative for mechanical ventilation.")
                .build());

        Medicine meropenem = medicineRepository.save(Medicine.builder()
                .name("Meropenem IV 1g Powder")
                .genericName("Meropenem Trihydrate")
                .sku("MED-ANT-005")
                .category(catAntibiotics)
                .dosageForm("Powder for Injection")
                .strength("1 g")
                .manufacturer("Aurobindo Pharma")
                .unitPrice(new BigDecimal("42.50"))
                .minStockThreshold(35)
                .isCritical(false)
                .storageCondition("Store dry powder below 25°C")
                .description("Broad-spectrum carbapenem antibiotic for severe nosocomial pneumonia and intra-abdominal sepsis.")
                .build());

        Medicine saline = medicineRepository.save(Medicine.builder()
                .name("Sodium Chloride 0.9% IV Infusion 500mL")
                .genericName("Normal Saline (0.9% NaCl)")
                .sku("MED-FLU-006")
                .category(catIvFluids)
                .dosageForm("IV Infusion Bag")
                .strength("500 mL")
                .manufacturer("Baxter Healthcare")
                .unitPrice(new BigDecimal("4.20"))
                .minStockThreshold(100)
                .isCritical(false)
                .storageCondition("Controlled Room Temperature (20°C - 25°C)")
                .description("Isotonic crystalloid fluid resuscitation and drug diluent solution.")
                .build());

        LocalDate today = LocalDate.now();

        Batch epiBatch1 = batchRepository.save(Batch.builder()
                .medicine(epinephrine)
                .batchNumber("BAT-EPI-2026A")
                .initialQuantity(30)
                .currentQuantity(22)
                .manufacturingDate(today.minusMonths(11))
                .expiryDate(today.plusDays(18))
                .unitCost(new BigDecimal("9.50"))
                .storageLocation("Crash Cart Rack A-01")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        Batch epiBatch2 = batchRepository.save(Batch.builder()
                .medicine(epinephrine)
                .batchNumber("BAT-EPI-2026B")
                .initialQuantity(60)
                .currentQuantity(55)
                .manufacturingDate(today.minusMonths(2))
                .expiryDate(today.plusMonths(14))
                .unitCost(new BigDecimal("10.00"))
                .storageLocation("Main Pharmacy Vault - Bay 2")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        Batch atrBatch1 = batchRepository.save(Batch.builder()
                .medicine(atropine)
                .batchNumber("BAT-ATR-2026A")
                .initialQuantity(25)
                .currentQuantity(15)
                .manufacturingDate(today.minusMonths(8))
                .expiryDate(today.plusMonths(8))
                .unitCost(new BigDecimal("14.00"))
                .storageLocation("Resuscitation Shelf B-02")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        Batch norBatch1 = batchRepository.save(Batch.builder()
                .medicine(norepinephrine)
                .batchNumber("BAT-NOR-2026A")
                .initialQuantity(40)
                .currentQuantity(28)
                .manufacturingDate(today.minusMonths(4))
                .expiryDate(today.plusMonths(6))
                .unitCost(new BigDecimal("28.00"))
                .storageLocation("ICU Rapid Dispense Box")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        Batch proBatch1 = batchRepository.save(Batch.builder()
                .medicine(propofol)
                .batchNumber("BAT-PRO-2026A")
                .initialQuantity(80)
                .currentQuantity(68)
                .manufacturingDate(today.minusMonths(3))
                .expiryDate(today.plusMonths(9))
                .unitCost(new BigDecimal("16.50"))
                .storageLocation("Anesthesia Vault C-04")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        Batch meroBatch1 = batchRepository.save(Batch.builder()
                .medicine(meropenem)
                .batchNumber("BAT-MER-2026A")
                .initialQuantity(50)
                .currentQuantity(44)
                .manufacturingDate(today.minusMonths(5))
                .expiryDate(today.plusMonths(12))
                .unitCost(new BigDecimal("32.00"))
                .storageLocation("Infectious Disease Shelf D-01")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        Batch salBatch1 = batchRepository.save(Batch.builder()
                .medicine(saline)
                .batchNumber("BAT-SAL-2026A")
                .initialQuantity(200)
                .currentQuantity(180)
                .manufacturingDate(today.minusMonths(1))
                .expiryDate(today.plusMonths(24))
                .unitCost(new BigDecimal("2.80"))
                .storageLocation("Bulk IV Pallet Zone E")
                .status(Batch.BatchStatus.ACTIVE)
                .build());

        batchRepository.save(Batch.builder()
                .medicine(atropine)
                .batchNumber("BAT-ATR-EXPIRED")
                .initialQuantity(10)
                .currentQuantity(6)
                .manufacturingDate(today.minusMonths(24))
                .expiryDate(today.minusDays(5))
                .unitCost(new BigDecimal("12.00"))
                .storageLocation("Quarantine Bin Q-01")
                .status(Batch.BatchStatus.EXPIRED)
                .build());

        EmergencyRequest req1 = EmergencyRequest.builder()
                .requestNumber("REQ-EMG-" + today.toString().replace("-", "") + "-001")
                .medicine(epinephrine)
                .requestedBy(icuStaff)
                .department("Intensive Care Unit (ICU)")
                .requestedQuantity(5)
                .allocatedQuantity(5)
                .urgencyLevel(EmergencyRequest.UrgencyLevel.CRITICAL)
                .status(EmergencyRequest.RequestStatus.ALLOCATED)
                .clinicalReason("Acute anaphylactic shock, patient in resuscitation bed 4")
                .bedOrPatientId("BED-ICU-04")
                .requiredBy(LocalDateTime.now().plusMinutes(15))
                .approvedBy(pharmacist)
                .pharmacistNotes("FEFO auto-allocated 5 units from earliest batch BAT-EPI-2026A.")
                .build();

        req1 = requestRepository.save(req1);

        RequestAllocation alloc1 = RequestAllocation.builder()
                .emergencyRequest(req1)
                .batch(epiBatch1)
                .allocatedQuantity(5)
                .build();
        allocationRepository.save(alloc1);
        req1.getAllocations().add(alloc1);
        requestRepository.save(req1);

        EmergencyRequest req2 = EmergencyRequest.builder()
                .requestNumber("REQ-EMG-" + today.toString().replace("-", "") + "-002")
                .medicine(norepinephrine)
                .requestedBy(erStaff)
                .department("Emergency Department")
                .requestedQuantity(4)
                .allocatedQuantity(0)
                .urgencyLevel(EmergencyRequest.UrgencyLevel.HIGH)
                .status(EmergencyRequest.RequestStatus.PENDING)
                .clinicalReason("Severe septic shock following trauma, mean arterial pressure < 50")
                .bedOrPatientId("TRAUMA-BAY-1")
                .requiredBy(LocalDateTime.now().plusHours(1))
                .build();
        requestRepository.save(req2);

        stockMovementRepository.save(StockMovement.builder()
                .medicine(epinephrine)
                .batch(epiBatch1)
                .movementType(StockMovement.MovementType.INWARD)
                .quantity(30)
                .balanceAfter(30)
                .referenceNumber("PO-INITIAL-001")
                .performedBy(admin)
                .remarks("Initial inventory onboarding")
                .timestamp(LocalDateTime.now().minusDays(15))
                .build());

        stockMovementRepository.save(StockMovement.builder()
                .medicine(atropine)
                .batch(atrBatch1)
                .movementType(StockMovement.MovementType.INWARD)
                .quantity(25)
                .balanceAfter(25)
                .referenceNumber("PO-INITIAL-002")
                .performedBy(admin)
                .remarks("Initial inventory onboarding")
                .timestamp(LocalDateTime.now().minusDays(10))
                .build());

        supplierOrderRepository.save(SupplierOrder.builder()
                .orderNumber("PO-2026-001")
                .medicine(atropine)
                .quantity(50)
                .supplier(supplier)
                .supplierCompanyName("Apex Medical Logistics")
                .status(SupplierOrder.OrderStatus.PLACED)
                .notes("Emergency replenishment due to inventory falling below threshold.")
                .orderedAt(LocalDateTime.now().minusHours(4))
                .build());

        log.info("Sample pharmacy inventory data initialized successfully!");
    }
}
