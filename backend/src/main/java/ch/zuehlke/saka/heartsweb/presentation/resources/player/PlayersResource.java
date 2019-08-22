package ch.zuehlke.saka.heartsweb.presentation.resources.player;

import ch.zuehlke.saka.heartsweb.domain.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/games/{gameIdParameter}/players", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PlayersResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayersResource.class);

	private final PlayerResourceAssembler playerResourceAssembler;
	private final PlayerRepository playerRepository;
	private final GameRepository gameRepository;

	public PlayersResource(PlayerResourceAssembler playerResourceAssembler, PlayerRepository playerRepository, GameRepository gameRepository) {
		this.playerResourceAssembler = playerResourceAssembler;
		this.playerRepository = playerRepository;
		this.gameRepository = gameRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Resources<Resource<PlayerDto>> getPlayersOfGame(@PathVariable String gameIdParameter) {
		LOGGER.debug("getPlayersOfGame gameId={}", gameIdParameter);

		GameId gameId = GameId.of(gameIdParameter);
		List<Resource<PlayerDto>> resources = playerRepository.findAllInGame(gameId).stream()
				.map(player -> playerResourceAssembler.toResource(Pair.of(gameId, player)))
				.collect(Collectors.toList());
		return new Resources<>(resources);
	}

	@GetMapping("/{playerIdParameter}")
	public ResponseEntity<Resource<PlayerDto>> findById(@PathVariable String gameIdParameter, @PathVariable String playerIdParameter) {
		LOGGER.debug("findById gameId={}, playerId={}", gameIdParameter, playerIdParameter);

		PlayerId playerId = PlayerId.of(playerIdParameter);
		GameId gameId = GameId.of(gameIdParameter);

		return playerRepository.findById(gameId, playerId)
				.map(player -> playerResourceAssembler.toResource(Pair.of(gameId, player)))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource<PlayerDto>> add(@PathVariable String gameIdParameter, @RequestBody PlayerDto playerDto) {
		LOGGER.debug("add gameId={}, playerName={}", gameIdParameter, playerDto.getName());

		GameId gameId = GameId.of(gameIdParameter);
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isPresent()) {
			Player player = map(playerDto);
			game.get().joinGame(player);

			LOGGER.info("Adding player with name={} and id={} to gameId={}", player.name(), player.id(), gameId);
			playerRepository.add(gameId, player);

			Resource<PlayerDto> resource = playerResourceAssembler.toResource(Pair.of(gameId, player));
			return ResponseEntity
					.created(URI.create(resource.getId().getHref()))
					.body(resource);
		}

		return ResponseEntity.notFound().build();
	}

	private Player map(PlayerDto playerDto) {
		return new Player(playerDto.getName());
	}
}
