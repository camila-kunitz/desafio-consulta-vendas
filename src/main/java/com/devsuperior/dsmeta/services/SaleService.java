package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchBy(String name, String minDate, String maxDate, Pageable pageable) {
		LocalDate inputMaxDate = getMaxDate(maxDate);
		LocalDate inputMinDate = getMinDate(minDate, inputMaxDate);

		return repository.searchBy(name, inputMinDate, inputMaxDate, pageable);
	}

	public List<SaleSumDTO> searchSummary(String minDate, String maxDate) {
		LocalDate inputMaxDate = getMaxDate(maxDate);
		LocalDate inputMinDate = getMinDate(minDate, inputMaxDate);

		return repository.searchSummary(inputMinDate, inputMaxDate);
	}

	private LocalDate getMaxDate(String maxDate) {
		return (maxDate == null) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
								 : LocalDate.parse(maxDate);
	}

	private LocalDate getMinDate(String minDate, LocalDate inputMaxDate) {
		return (minDate == null) ? inputMaxDate.minusYears(1L)
								 : LocalDate.parse(minDate);
	}


}
