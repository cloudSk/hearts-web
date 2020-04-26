package ch.zuehlke.saka.heartsweb.domain;

public class DomainEventPublisher {
	private static final DomainEventPublisher instance = new DomainEventPublisher();

	private final DomainEventObservable<PlayerJoinedGame> playerJoinedGameObservable = new DomainEventObservable<>();

	public static DomainEventPublisher instance() {
		return instance;
	}

	public DomainEventObservable<PlayerJoinedGame> playerJoinedGame() {
		return playerJoinedGameObservable;
	}
}
