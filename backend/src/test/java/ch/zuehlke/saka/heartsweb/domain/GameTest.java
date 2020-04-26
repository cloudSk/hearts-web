package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GameTest {
	private static final Player FIRST_PLAYER = new Player("First");
	private static final Player SECOND_PLAYER = new Player("Second");
	private static final Player THIRD_PLAYER = new Player("Third");
	private static final Player FOURTH_PLAYER = new Player("Fourth");

	@Test
	public void joinGame_4PlayersAlreadyJoined_throwsIllegalStateException() {
		Game testee = new Game();
		testee.joinGame(FIRST_PLAYER);
		testee.joinGame(SECOND_PLAYER);
		testee.joinGame(THIRD_PLAYER);
		testee.joinGame(FOURTH_PLAYER);

		Throwable result = catchThrowable(() -> testee.joinGame(new Player("Fifth Player")));

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void joinGame_playerAlreadyJoined_throwsIllegalArgumentException() {
        Player player = FIRST_PLAYER;
        Game testee = new Game();
        testee.joinGame(player);

		Throwable result = catchThrowable(() -> testee.joinGame(player));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}
}