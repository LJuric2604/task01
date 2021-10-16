package hr.task.api.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import hr.task.api.exception.PersonWithoutChannelException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String contactNumber;

	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
	private Set<PersonChannel> personChannels;

	public Channel getMinimumCostChannel() {
		if (personChannels == null) {
			throw new PersonWithoutChannelException();
		}
		return personChannels.stream().map(PersonChannel::getChannel).min(PriceEntity::compareTo)
				.orElseThrow(PersonWithoutChannelException::new);
	}

}
