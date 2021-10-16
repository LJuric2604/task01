package hr.task.api.entity;

/**
 * Contract for entity that has price value.
 * 
 * @author ljuric
 *
 */
public interface PriceEntity extends Comparable<PriceEntity> {

	public void setPrice(Integer price);

	public Integer getPrice();

	@Override
	default int compareTo(PriceEntity o) {
		return getPrice().compareTo(o.getPrice());
	}

}
