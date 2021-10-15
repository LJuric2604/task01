package hr.task.client.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hr.task.client.ClientProperties;
import hr.task.client.impl.ApiClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final ClientProperties properties;

	private final ApiClient client;

	@Async
	@Scheduled(fixedRate = 1000)
	public void callApi() {
		Thread[] jobs = createJobs();
		for (Thread job : jobs) {
			job.start();
		}
	}

	private Thread[] createJobs() {
		Thread[] jobs = new Thread[properties.getJobsPerTask()];
		for (int i = 0; i < properties.getJobsPerTask(); i++) {
			Runnable job = new RandomMessageSender(client);
			jobs[i] = new Thread(job);
		}
		return jobs;

	}

}
