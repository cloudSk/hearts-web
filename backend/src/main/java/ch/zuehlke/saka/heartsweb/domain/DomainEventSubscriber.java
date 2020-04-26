package ch.zuehlke.saka.heartsweb.domain;

public interface DomainEventSubscriber<T extends DomainEvent> {
	void handleEvent(T event);
}
