package ch.zuehlke.saka.heartsweb.domain;

public class DomainEventPublisher {
	private static final DomainEventPublisher instance = new DomainEventPublisher();

	private final DomainEventObservable<PlayerJoinedGame> playerJoinedGameObservable = new DomainEventObservable<>();
	private final DomainEventObservable<CardPlayed> cardPlayed = new DomainEventObservable<>();

	public static DomainEventPublisher instance() {
		return instance;
	}

	public DomainEventObservable<PlayerJoinedGame> playerJoinedGame() {
		return playerJoinedGameObservable;
	}

	public DomainEventObservable<CardPlayed> cardPlayed() {
		return cardPlayed;
	}
}
