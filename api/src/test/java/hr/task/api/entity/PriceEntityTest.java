package hr.task.api.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PriceEntityTest {

	@Test
	public void whenSamePriceExpect0() {
		PriceEntity first = createPriceEntity(1);
		PriceEntity second = createPriceEntity(1);
		int result = first.compareTo(second);
		assertThat(result).isEqualTo(0);
	}

	@Test
	public void whenPriceOneGreaterThanPrice2Expect1() {
		PriceEntity first = createPriceEntity(2);
		PriceEntity second = createPriceEntity(1);
		int result = first.compareTo(second);
		assertThat(result).isEqualTo(1);
	}

	@Test
	public void whenPriceOneLessThanPrice2ExpectMinus1() {
		PriceEntity first = createPriceEntity(1);
		PriceEntity second = createPriceEntity(2);
		int result = first.compareTo(second);
		assertThat(result).isEqualTo(-1);
	}

	@Test
	public void whenPricesSameExpect0() {
		PriceEntity first = createPriceEntity(1);
		PriceEntity second = createPriceEntity(1);
		int result = first.compareTo(second);
		assertThat(result).isEqualTo(0);
	}

	private static PriceEntity createPriceEntity(Integer value) {
		PriceEntity priceEntity = new PriceEntity() {

			private Integer price;

			@Override
			public void setPrice(Integer price) {
				this.price = price;
			}

			@Override
			public Integer getPrice() {
				return price;
			}
		};

		priceEntity.setPrice(value);
		return priceEntity;
	}
}
