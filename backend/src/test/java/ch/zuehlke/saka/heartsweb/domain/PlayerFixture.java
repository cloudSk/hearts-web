package ch.zuehlke.saka.heartsweb.domain;

public class PlayerFixture {
	static Player anyPlayer() {
		return new Player("Anybody");
	}

	static Player anyPlayerWithId(PlayerId playerId) {
		return new Player(playerId, "Somebody");
	}
}
