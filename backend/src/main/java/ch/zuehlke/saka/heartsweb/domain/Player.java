package ch.zuehlke.saka.heartsweb.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private final PlayerId id;
	private String name;
	private final List<Card> hand;

	public Player(String name) {
		this(PlayerId.generate(), name);
	}

	public Player(PlayerId id, String name) {
		this.id = id;
		this.name = name;
		this.hand = new ArrayList<>();
	}

	public PlayerId id() {
		return id;
	}

	public String name() {
		return name;
	}

	void playCardToTrick(Card cardToPlay, Trick trick) {
		if (!hand.contains(cardToPlay)) {
			throw new IllegalArgumentException("Card=" + cardToPlay + " is not in current hand of playerId=" + id);
		}

		hand.remove(cardToPlay);
		trick.playCard(cardToPlay, id);
	}

	void assignHand(List<Card> cardsInHand) {
		hand.clear();
		hand.addAll(cardsInHand);
	}
}
