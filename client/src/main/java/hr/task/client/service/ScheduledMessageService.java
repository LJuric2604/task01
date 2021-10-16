package hr.task.client.service;

import java.util.stream.IntStream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hr.task.client.ClientProperties;
import hr.task.client.impl.ApiClient;
import lombok.RequiredArgsConstructor;

/**
 * Scheduled task that sends 10 async messages per second.
 * 
 * @author ljuric
 *
 */
@Service
@RequiredArgsConstructor
public class ScheduledMessageService {

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
		IntStream.range(0, properties.getJobsPerTask()).forEach(i -> {
			Runnable job = new RandomMessageSender(client);
			jobs[i] = new Thread(job);
		});
		return jobs;

	}

}
