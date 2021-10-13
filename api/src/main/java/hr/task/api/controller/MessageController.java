package hr.task.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hr.task.api.model.Message;
import hr.task.api.service.MessageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	private void send(@RequestBody Message message) {
		messageService.send(message);
	}

}
