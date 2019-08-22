package ch.zuehlke.saka.heartsweb.presentation.resources.game;

import ch.zuehlke.saka.heartsweb.domain.Game;
import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.GameRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/games", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class GameResource {
	private GameResourceAssembler gameResourceAssembler;
	private GameRepository gameRepository;

	public GameResource(GameResourceAssembler gameResourceAssembler, GameRepository gameRepository) {
		this.gameResourceAssembler = gameResourceAssembler;
		this.gameRepository = gameRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Resources<Resource<GameDto>> getGames() {
		List<Resource<GameDto>> resources = gameRepository.findAll().stream()
				.map(game -> gameResourceAssembler.toResource(game))
				.collect(Collectors.toList());
		return new Resources<>(resources);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource<GameDto>> findById(@PathVariable String id) {
		GameId gameId = GameId.of(id);
		return gameRepository.findById(gameId)
				.map(gameResourceAssembler::toResource)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Resource<GameDto>> create() {
		Game newGame = new Game();
		gameRepository.add(newGame);

		Resource<GameDto> createdResource = gameResourceAssembler.toResource(newGame);

		return ResponseEntity
				.created(URI.create(createdResource.getId().getHref()))
				.body(createdResource);
	}
}
