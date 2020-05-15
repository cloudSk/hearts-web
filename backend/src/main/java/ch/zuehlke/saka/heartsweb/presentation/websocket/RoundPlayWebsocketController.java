package ch.zuehlke.saka.heartsweb.presentation.websocket;

import ch.zuehlke.saka.heartsweb.domain.*;
import ch.zuehlke.saka.heartsweb.presentation.resources.round.RoundDto;
import ch.zuehlke.saka.heartsweb.presentation.resources.round.RoundResourceAssembler;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Controller
public class RoundPlayWebsocketController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoundPlayWebsocketController.class);

	private final RoundRepository roundRepository;
	private final RoundResourceAssembler roundResourceAssembler;
	private final SimpMessagingTemplate messagingTemplate;

	public RoundPlayWebsocketController(
			RoundRepository roundRepository, RoundResourceAssembler roundResourceAssembler,
			SimpMessagingTemplate messagingTemplate) {
		this.roundRepository = roundRepository;
		this.roundResourceAssembler = roundResourceAssembler;
		this.messagingTemplate = messagingTemplate;
	}

	@PostConstruct
	private void init() {
		DomainEventPublisher.instance()
				.roundCreated()
				.subscribe(this::handleEvent);
	}

	@SubscribeMapping("/topic/activeRound/{gameIdParameter}")
	public void subscribeToActiveRoundOfGame(@DestinationVariable String gameIdParameter) {
		LOGGER.debug("subscribeToActiveRoundOfGame gameId={}", gameIdParameter);
		GameId gameId = GameId.of(gameIdParameter);
		roundRepository.findActiveRoundOfGame(gameId)
				.map(round -> roundResourceAssembler.toResource(Pair.of(gameId, round)))
				.ifPresent(resource -> messagingTemplate.convertAndSend("/topic/activeRound/" + gameId, resource));
	}

	void handleEvent(RoundCreated event) {
		LOGGER.debug("Round with id={} created in game={}", event.roundId(), event.gameId());
		Optional<Round> round = roundRepository.findById(event.gameId(), event.roundId());
		if (round.isEmpty()) {
			throw new IllegalStateException("Created round with id=" + event.roundId() + " is not available");
		}

		Resource<RoundDto> resource = roundResourceAssembler.toResource(Pair.of(event.gameId(), round.get()));
		messagingTemplate.convertAndSend("/topic/activeRound/" + event.gameId(), resource);
	}
}
