package hr.task.channel.api.mockup;

import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hr.task.channel.api.mockup.model.MockSender;
import hr.task.channel.api.mockup.model.MockupReq;
import hr.task.channel.api.mockup.model.SmsSender;
import hr.task.channel.api.mockup.model.ViberSender;
import hr.task.channel.api.mockup.model.WhatsAppSender;

@RestController
@RequestMapping("/mockups")
public class MockupController {

	@PostMapping("/sms")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	private void sendSMS(@Valid @RequestBody MockupReq request) {
		this.mockSend(request, SmsSender::new);
	}

	@PostMapping("/viber")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	private void sendViber(@Valid @RequestBody MockupReq request) {
		this.mockSend(request, ViberSender::new);
	}

	@PostMapping("/whatsapp")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	private void sendWhatsApp(@Valid @RequestBody MockupReq request) {
		this.mockSend(request, WhatsAppSender::new);
	}

	private void mockSend(MockupReq request, Function<MockupReq, MockSender> senderCreator) {
		MockSender sender = senderCreator.apply(request);
		sender.send();
	}

}
