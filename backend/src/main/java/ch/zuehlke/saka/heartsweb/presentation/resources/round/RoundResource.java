package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/games/{gameIdParameter}/rounds", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class RoundResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoundResource.class);

	private final RoundResourceAssembler roundResourceAssembler;
	private final GameRepository gameRepository;
	private final PlayerRepository playerRepository;
	private final RoundRepository roundRepository;
	private final RoundCreationService roundCreationService;
	private final RoundOrchestrationService roundOrchestrationService;

	public RoundResource(RoundResourceAssembler roundResourceAssembler, GameRepository gameRepository,
	                     PlayerRepository playerRepository, RoundRepository roundRepository,
	                     RoundCreationService roundCreationService,
	                     RoundOrchestrationService roundOrchestrationService) {
		this.roundResourceAssembler = roundResourceAssembler;
		this.gameRepository = gameRepository;
		this.playerRepository = playerRepository;
		this.roundRepository = roundRepository;
		this.roundCreationService = roundCreationService;
		this.roundOrchestrationService = roundOrchestrationService;
	}

	@GetMapping("/{roundIdParameter}")
	public ResponseEntity<Resource<RoundDto>> findById(@PathVariable String gameIdParameter, @PathVariable String roundIdParameter) {
		LOGGER.debug("findById gameId={}, roundId={}", gameIdParameter, roundIdParameter);

		RoundId roundId = RoundId.of(roundIdParameter);
		GameId gameId = GameId.of(gameIdParameter);

		return roundRepository.findById(roundId)
				.map(round -> roundResourceAssembler.toResource(Pair.of(gameId, round)))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource<RoundDto>> add(@PathVariable String gameIdParameter) {
		LOGGER.debug("add gameId={}", gameIdParameter);

		GameId gameId = GameId.of(gameIdParameter);
		Optional<Game> game = gameRepository.findById(gameId);
		if (!game.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Round round = roundCreationService.createRound(gameId);

		Resource<RoundDto> resource = roundResourceAssembler.toResource(Pair.of(gameId, round));
		return ResponseEntity
				.created(URI.create(resource.getId().getHref()))
				.body(resource);
	}
}
