package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SittingOrderTest {
	private static final PlayerId FIRST_PLAYER = new Player("First").id();
	private static final PlayerId SECOND_PLAYER = new Player("Second").id();
	private static final PlayerId THIRD_PLAYER = new Player("Third").id();
	private static final PlayerId FOURTH_PLAYER = new Player("Fourth").id();

	@Test
	public void nextPlayerAfter_firstPlayer_returnsSecondPlayer() {
		SittingOrder testee = new SittingOrder(FIRST_PLAYER, SECOND_PLAYER, THIRD_PLAYER, FOURTH_PLAYER);

		PlayerId result = testee.nextPlayerAfter(FIRST_PLAYER);

		assertThat(result).isEqualTo(SECOND_PLAYER);
	}

	@Test
	public void nextPlayerAfter_lastPlayer_returnsFirstPlayer() {
		SittingOrder testee = new SittingOrder(FIRST_PLAYER, SECOND_PLAYER, THIRD_PLAYER, FOURTH_PLAYER);

		PlayerId result = testee.nextPlayerAfter(FOURTH_PLAYER);

		assertThat(result).isEqualTo(FIRST_PLAYER);
	}
}