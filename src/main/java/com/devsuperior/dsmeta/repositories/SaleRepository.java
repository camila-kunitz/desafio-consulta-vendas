package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(sale.id, sale.amount, sale.date, sale.seller.name)" +
            "FROM Sale sale " +
            "WHERE UPPER(sale.seller.name) LIKE UPPER(CONCAT('%', :inputName, '%')) " +
            "AND sale.date BETWEEN :inputMinDate AND :inputMaxDate",
            countQuery = "SELECT COUNT(sale) FROM Sale sale")
    Page<SaleMinDTO> searchBy(String inputName, LocalDate inputMinDate, LocalDate inputMaxDate, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :inputMinDate AND :inputMaxDate " +
            "GROUP BY obj.seller.name")
    List<SaleSumDTO> searchSummary(LocalDate inputMinDate, LocalDate inputMaxDate);
}




