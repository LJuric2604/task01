package hr.task.api.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.task.api.model.PerMessageResponse;
import hr.task.api.security.AdminRole;
import hr.task.api.service.PerMessageService;
import lombok.RequiredArgsConstructor;

@AdminRole
@RestController
@RequestMapping("/admin/per-message")
@RequiredArgsConstructor
public class PerMessageController {

	private final PerMessageService perMessageService;

	@GetMapping("/total")
	public PerMessageResponse getTotalData(@RequestParam String key,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {
		return perMessageService.getTotalData(key, timestamp);
	}

	@GetMapping("/interval")
	public PerMessageResponse getIntervalData(@RequestParam String key,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startInclusive,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endInclusive) {
		return perMessageService.getIntervalData(key, startInclusive, endInclusive);
	}

}
