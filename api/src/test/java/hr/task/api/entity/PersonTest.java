package hr.task.api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hr.task.api.exception.PersonWithoutChannelException;

public class PersonTest {

	@ParameterizedTest
	@MethodSource("generateData")
	public void whenPersonHasChannelsExpectLowestPrice(int lowestPrice, Channel[] channels) {
		final var person = createPersonWithChannels(channels);

		final var minimumCostChannel = person.getMinimumCostChannel();
		assertThat(minimumCostChannel).extracting(Channel::getPrice).isEqualTo(lowestPrice);

	}

	@Test
	public void whenPersonWithoutChannelExpectPersonWithoutChannelException() {
		final var person = createPersonWithChannels();

		assertThatExceptionOfType(PersonWithoutChannelException.class).isThrownBy(person::getMinimumCostChannel);
	}

	@Test
	public void whenPersonWithNullChannelExpectPersonWithoutChannelException() {
		final var person = new Person();

		assertThatExceptionOfType(PersonWithoutChannelException.class).isThrownBy(person::getMinimumCostChannel);
	}

	private static Stream<Arguments> generateData() {
		return Stream.of(Arguments.of(1, new Channel[] { createChannelWithPrice(1) }),
				Arguments.of(1, new Channel[] { createChannelWithPrice(1), createChannelWithPrice(2) }),
				Arguments.of(1,
						new Channel[] { createChannelWithPrice(1), createChannelWithPrice(1),
								createChannelWithPrice(2) }),
				Arguments.of(1,
						new Channel[] { createChannelWithPrice(1), createChannelWithPrice(2), createChannelWithPrice(3),
								createChannelWithPrice(4), createChannelWithPrice(5) }),
				Arguments.of(5,
						new Channel[] { createChannelWithPrice(5), createChannelWithPrice(5),
								createChannelWithPrice(5) }),
				Arguments.of(3, new Channel[] { createChannelWithPrice(3), createChannelWithPrice(3),
						createChannelWithPrice(5) }));
	}

	private static Channel createChannelWithPrice(Integer price) {
		final var channel = new Channel();
		channel.setPrice(price);
		return channel;
	}

	private static Person createPersonWithChannels(Channel... channels) {
		final var person = new Person();
		final var personChannels = createPersonChannels(channels);
		person.setPersonChannels(personChannels);
		return person;
	}

	private static Set<PersonChannel> createPersonChannels(Channel... channels) {
		return Stream.of(channels).map(PersonTest::createPersonChannelWithChannel).collect(Collectors.toSet());
	}

	private static PersonChannel createPersonChannelWithChannel(Channel channel) {
		final var personChannel = new PersonChannel();
		personChannel.setChannel(channel);
		return personChannel;
	}

}
