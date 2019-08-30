package ch.zuehlke.saka.heartsweb.presentation.websocket;

import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.PlayerRepository;
import ch.zuehlke.saka.heartsweb.presentation.resources.player.PlayerDto;
import ch.zuehlke.saka.heartsweb.presentation.resources.player.PlayerResourceAssembler;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JoinedPlayersWebsocketController {
	private static final Logger LOGGER = LoggerFactory.getLogger(JoinedPlayersWebsocketController.class);

	private final PlayerRepository playerRepository;
	private final PlayerResourceAssembler playerResourceAssembler;

	public JoinedPlayersWebsocketController(PlayerRepository playerRepository, PlayerResourceAssembler playerResourceAssembler) {
		this.playerRepository = playerRepository;
		this.playerResourceAssembler = playerResourceAssembler;
	}

	@SubscribeMapping("/topic/joinedPlayers/{gameIdParameter}")
	public Resources<Resource<PlayerDto>> getJoinedPlayers(@DestinationVariable String gameIdParameter) {
		LOGGER.debug("getJoinedPlayers gameId={}", gameIdParameter);

		GameId gameId = GameId.of(gameIdParameter);
		List<Resource<PlayerDto>> playerResources = playerRepository.findAllInGame(gameId).stream()
				.map(player -> playerResourceAssembler.toResource(Pair.of(gameId, player)))
				.collect(Collectors.toList());

		return new Resources<>(playerResources);
	}
}
