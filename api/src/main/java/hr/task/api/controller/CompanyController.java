package hr.task.api.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.task.api.model.CompanyResponse;
import hr.task.api.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/company")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping("/total")
	public CompanyResponse getTotalData(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {
		return companyService.getTotalData(timestamp);
	}

	@GetMapping("/interval")
	public CompanyResponse getIntervalData(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startInclusive,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endInclusive) {
		return companyService.getIntervalData(startInclusive, endInclusive);
	}

}
