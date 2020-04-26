package ch.zuehlke.saka.heartsweb.domain;

import java.util.ArrayList;
import java.util.List;

public class DomainEventObservable<T extends DomainEvent> {
	private final List<DomainEventSubscriber<T>> subscribers = new ArrayList<>();

	public synchronized void publish(T event) {
		subscribers.forEach(subscriber -> subscriber.handleEvent(event));
	}

	public synchronized void subscribe(DomainEventSubscriber<T> subscriber) {
		subscribers.add(subscriber);
	}

	public synchronized void unsubscribe(DomainEventSubscriber<T> subscriber) {
		subscribers.remove(subscriber);
	}
}
