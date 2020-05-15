package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.Round;

public class RoundDto {
	private String id;
	private String roundInitiator;
	private SittingOrderDto sittingOrder;

	public RoundDto() {
	}

	public RoundDto(String id) {
		this.id = id;
	}

	public RoundDto(Round round) {
		this.id = round.id().toString();
		this.roundInitiator = round.roundInitiator().toString();
		this.sittingOrder = new SittingOrderDto(round.sittingOrder());
	}

	public String getId() {
		return id;
	}

	public String getRoundInitiator() {
		return roundInitiator;
	}

	public SittingOrderDto getSittingOrder() {
		return sittingOrder;
	}
}
