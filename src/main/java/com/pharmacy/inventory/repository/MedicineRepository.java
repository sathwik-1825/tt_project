package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findBySku(String sku);
    boolean existsBySku(String sku);

    List<Medicine> findByCategoryId(Long categoryId);

    List<Medicine> findByIsCriticalTrue();

    @Query("SELECT m FROM Medicine m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.genericName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.sku) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.manufacturer) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Medicine> searchMedicines(@Param("query") String query);
}
