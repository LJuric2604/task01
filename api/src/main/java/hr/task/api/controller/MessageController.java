package hr.task.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.task.api.model.MessageReq;
import hr.task.api.service.MessageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@PostMapping("/send")
	private String send(@RequestBody MessageReq message) {
		return messageService.send(message);
	}

}
