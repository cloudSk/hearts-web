package ch.zuehlke.saka.heartsweb.domain;

public class GameFixture {
	private static final Player FIRST_PLAYER = new Player("First");
	private static final Player SECOND_PLAYER = new Player("Second");
	private static final Player THIRD_PLAYER = new Player("Third");
	private static final Player FOURTH_PLAYER = new Player("Fourth");

	static Game gameWith4Players() {
		Game game = new Game();
		game.joinGame(FIRST_PLAYER);
		game.joinGame(SECOND_PLAYER);
		game.joinGame(THIRD_PLAYER);
		game.joinGame(FOURTH_PLAYER);
		return game;
	}

	static Game gameWithPlayers(Player first, Player second, Player third, Player fourth) {
		Game game = new Game();
		game.joinGame(first);
		game.joinGame(second);
		game.joinGame(third);
		game.joinGame(fourth);
		return game;
	}
}
