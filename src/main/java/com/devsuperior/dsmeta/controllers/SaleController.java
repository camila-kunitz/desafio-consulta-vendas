package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	// Relatório de vendas
	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(@RequestParam(name = "name", defaultValue = "") String name,
													  @RequestParam(name = "minDate", required = false) String minDate,
													  @RequestParam(name = "maxDate", required = false) String maxDate,
													  Pageable pageable) {
		Page<SaleMinDTO> dto = service.searchBy(name, minDate, maxDate, pageable);
		return ResponseEntity.ok(dto);
	}

	// Sumário de vendas
	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSumDTO>> getSummary(@RequestParam(name = "minDate", required = false) String minDate,
													   @RequestParam(name = "maxDate", required = false) String maxDate) {
		List<SaleSumDTO> dto = service.searchSummary(minDate, maxDate);
		return ResponseEntity.ok(dto);
	}
}
