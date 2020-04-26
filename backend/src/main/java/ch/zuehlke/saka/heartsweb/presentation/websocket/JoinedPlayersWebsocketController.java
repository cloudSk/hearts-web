package ch.zuehlke.saka.heartsweb.presentation.websocket;

import ch.zuehlke.saka.heartsweb.domain.*;
import ch.zuehlke.saka.heartsweb.presentation.resources.player.PlayerDto;
import ch.zuehlke.saka.heartsweb.presentation.resources.player.PlayerResourceAssembler;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JoinedPlayersWebsocketController implements DomainEventSubscriber<PlayerJoinedGame> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JoinedPlayersWebsocketController.class);

	private final PlayerRepository playerRepository;
	private final PlayerResourceAssembler playerResourceAssembler;
	private final SimpMessagingTemplate messagingTemplate;

	public JoinedPlayersWebsocketController(
			PlayerRepository playerRepository,
			PlayerResourceAssembler playerResourceAssembler,
			SimpMessagingTemplate messagingTemplate) {
		this.playerRepository = playerRepository;
		this.playerResourceAssembler = playerResourceAssembler;
		this.messagingTemplate = messagingTemplate;
	}

	@PostConstruct
	private void init() {
		DomainEventPublisher.instance()
				.playerJoinedGame()
				.subscribe(this);
	}

	@SubscribeMapping("/topic/joinedPlayers/{gameId}")
	public Resources<Resource<PlayerDto>> getJoinedPlayers(@DestinationVariable String gameId) {
		LOGGER.debug("getJoinedPlayers gameId={}", gameId);
		return playerResourcesOfGame(GameId.of(gameId));
	}

	@Override
	public void handleEvent(PlayerJoinedGame event) {
		LOGGER.debug("Updating joined players of gameId={}", event.gameId());
		Resources<Resource<PlayerDto>> playerResources = playerResourcesOfGame(event.gameId());
		messagingTemplate.convertAndSend("/topic/joinedPlayers/" + event.gameId().toString(), playerResources);
	}

	private Resources<Resource<PlayerDto>> playerResourcesOfGame(GameId gameId) {
		List<Resource<PlayerDto>> playerResources = playerRepository.findAllInGame(gameId).stream()
				.map(player -> playerResourceAssembler.toResource(Pair.of(gameId, player)))
				.collect(Collectors.toList());
		return new Resources<>(playerResources);
	}
}
