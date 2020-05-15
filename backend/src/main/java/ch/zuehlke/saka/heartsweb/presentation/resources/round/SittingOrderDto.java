package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.SittingOrder;

public class SittingOrderDto {
	private String north;
	private String east;
	private String south;
	private String west;

	public SittingOrderDto() {
	}

	public SittingOrderDto(SittingOrder sittingOrder) {
		this.north = sittingOrder.north().toString();
		this.east = sittingOrder.east().toString();
		this.south = sittingOrder.south().toString();
		this.west = sittingOrder.west().toString();
	}

	public String getNorth() {
		return north;
	}

	public String getEast() {
		return east;
	}

	public String getSouth() {
		return south;
	}

	public String getWest() {
		return west;
	}
}
