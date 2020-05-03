package ch.zuehlke.saka.heartsweb.domain;

import java.util.*;

public class Player {
	private final PlayerId id;
	private String name;
	private final Map<RoundId, List<Card>> hands;

	public Player(String name) {
		this(PlayerId.generate(), name);
	}

	public Player(PlayerId id, String name) {
		this.id = id;
		this.name = name;
		this.hands = new HashMap<>();
	}

	public PlayerId id() {
		return id;
	}

	public String name() {
		return name;
	}

	public List<Card> remainingHand(RoundId roundId) {
		return Collections.unmodifiableList(handOfRound(roundId));
	}

	void playCardToRound(Card cardToPlay, Round round) {
		List<Card> hand = handOfRound(round.id());
		if (!hand.contains(cardToPlay)) {
			throw new IllegalArgumentException("Card=" + cardToPlay + " is not in current hand of playerId=" + id);
		}

		round.playCard(cardToPlay, id);
		hand.remove(cardToPlay);
	}

	void assignHand(List<Card> cardsInHand, RoundId roundId) {
		List<Card> hand = handOfRound(roundId);
		hand.clear();
		hand.addAll(cardsInHand);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		Player player = (Player) o;
		return id.equals(player.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	private List<Card> handOfRound(RoundId roundId) {
		hands.putIfAbsent(roundId, new ArrayList<>());
		return hands.get(roundId);
	}
}
