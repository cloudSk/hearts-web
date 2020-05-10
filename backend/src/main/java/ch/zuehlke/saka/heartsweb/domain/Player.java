package ch.zuehlke.saka.heartsweb.domain;

import java.util.*;

public class Player {
	private final PlayerId id;
	private String name;
	private final Map<RoundId, Set<Card>> hands;

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

	public Set<Card> remainingHand(RoundId roundId) {
		return Collections.unmodifiableSet(handOfRound(roundId));
	}

	void playCardToRound(Card cardToPlay, Round round) {
		Set<Card> hand = handOfRound(round.id());
		if (!hand.contains(cardToPlay)) {
			throw new IllegalArgumentException("Card=" + cardToPlay + " is not in current hand of playerId=" + id);
		}

		round.playCard(cardToPlay, id);
		hand.remove(cardToPlay);
	}

	void assignHand(Set<Card> cardsInHand, RoundId roundId) {
		Set<Card> hand = handOfRound(roundId);
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

	private Set<Card> handOfRound(RoundId roundId) {
		hands.putIfAbsent(roundId, new HashSet<>());
		return hands.get(roundId);
	}
}
