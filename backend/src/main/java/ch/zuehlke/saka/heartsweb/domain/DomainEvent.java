package ch.zuehlke.saka.heartsweb.domain;

import java.time.LocalDateTime;

public interface DomainEvent {
	LocalDateTime occurredOn();
}
