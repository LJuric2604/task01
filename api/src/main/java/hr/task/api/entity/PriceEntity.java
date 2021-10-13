package hr.task.api.entity;

public interface PriceEntity extends Comparable<PriceEntity> {
	
	public void setPrice(Integer price);
	public Integer getPrice();
	
	@Override
	default int compareTo(PriceEntity o) {
		return getPrice().compareTo(o.getPrice());
	}

}
