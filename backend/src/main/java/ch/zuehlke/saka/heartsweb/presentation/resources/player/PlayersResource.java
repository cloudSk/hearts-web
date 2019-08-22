package ch.zuehlke.saka.heartsweb.presentation.resources.player;

import ch.zuehlke.saka.heartsweb.domain.*;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/games/{gameId}/players", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PlayersResource {
	private final PlayerResourceAssembler playerResourceAssembler;
	private final PlayerRepository playerRepository;
	private final GameRepository gameRepository;

	public PlayersResource(PlayerResourceAssembler playerResourceAssembler, PlayerRepository playerRepository, GameRepository gameRepository) {
		this.playerResourceAssembler = playerResourceAssembler;
		this.playerRepository = playerRepository;
		this.gameRepository = gameRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Resources<Resource<PlayerDto>> getPlayersOfGame(@PathVariable String gameId) {
		List<Resource<PlayerDto>> resources = playerRepository.findAllInGame(GameId.of(gameId)).stream()
				.map(playerResourceAssembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(resources);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource<PlayerDto>> findById(@PathVariable String gameId, @PathVariable String id) {
		PlayerId playerId = PlayerId.of(id);
		return playerRepository.findById(GameId.of(gameId), playerId)
				.map(playerResourceAssembler::toResource)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Resource<PlayerDto>> add(@PathVariable String gameId, PlayerDto playerDto) {
		GameId gameIdToUse = GameId.of(gameId);
		Optional<Game> game = gameRepository.findById(gameIdToUse);
		if (game.isPresent()) {
			Player player = map(playerDto);
			game.get().joinGame(player);

			playerRepository.add(gameIdToUse, player);

			Resource<PlayerDto> resource = playerResourceAssembler.toResource(player);
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
