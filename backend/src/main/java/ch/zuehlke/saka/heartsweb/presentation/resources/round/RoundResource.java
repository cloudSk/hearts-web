package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.*;
import ch.zuehlke.saka.heartsweb.presentation.resources.player.PlayersResource;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/games/{gameIdParameter}/rounds", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class RoundResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoundResource.class);

	private final RoundResourceAssembler roundResourceAssembler;
	private final CardResourcesAssembler cardResourcesAssembler;
	private final GameRepository gameRepository;
	private final PlayerRepository playerRepository;
	private final RoundRepository roundRepository;
	private final RoundCreationService roundCreationService;
	private final RoundOrchestrationService roundOrchestrationService;

	public RoundResource(RoundResourceAssembler roundResourceAssembler, CardResourcesAssembler cardResourcesAssembler,
	                     GameRepository gameRepository, PlayerRepository playerRepository,
	                     RoundRepository roundRepository, RoundCreationService roundCreationService,
	                     RoundOrchestrationService roundOrchestrationService) {
		this.roundResourceAssembler = roundResourceAssembler;
		this.cardResourcesAssembler = cardResourcesAssembler;
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

		return roundRepository.findById(gameId, roundId)
				.map(round -> roundResourceAssembler.toResource(Pair.of(gameId, round)))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Resource<RoundDto>> addRound(@PathVariable String gameIdParameter) {
		LOGGER.debug("add gameId={}", gameIdParameter);

		GameId gameId = GameId.of(gameIdParameter);
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Round round = roundCreationService.createRound(gameId);

		Resource<RoundDto> resource = roundResourceAssembler.toResource(Pair.of(gameId, round));
		return ResponseEntity
				.created(URI.create(resource.getId().getHref()))
				.body(resource);
	}

	@GetMapping("/{roundIdParameter}/cards")
	public ResponseEntity<Resources<Resource<CardDto>>> getRemainingHand(@PathVariable String gameIdParameter,
	                                                           @PathVariable String roundIdParameter,
	                                                           HttpSession session) {
		LOGGER.debug("getRemainingHand gameId={}, roundId={}", gameIdParameter, roundIdParameter);

		PlayerId sessionPlayerId = (PlayerId) session.getAttribute(PlayersResource.SESSION_PLAYER_ID_ATTRIBUTE);
		if (sessionPlayerId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		GameId gameId = GameId.of(gameIdParameter);
		RoundId roundId = RoundId.of(roundIdParameter);

		return playerRepository.findById(gameId, sessionPlayerId)
				.map(player -> player.remainingHand(roundId))
				.map(cards -> cardResourcesAssembler.toResource(Triple.of(gameId, roundId, cards)))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = "/{roundIdParameter}/cards/{cardIdParameter}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource<RoundDto>> playCard(@PathVariable String gameIdParameter,
	                                                   @PathVariable String roundIdParameter, HttpSession session,
	                                                   @PathVariable String cardIdParameter) {
		LOGGER.debug("playCard gameId={}, roundId={}, cardIdentifier={}", gameIdParameter, roundIdParameter,
				cardIdParameter);

		PlayerId sessionPlayerId = (PlayerId) session.getAttribute(PlayersResource.SESSION_PLAYER_ID_ATTRIBUTE);
		if (sessionPlayerId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		GameId gameId = GameId.of(gameIdParameter);
		RoundId roundId = RoundId.of(roundIdParameter);
		CardDtoId cardDtoId = CardDtoId.of(cardIdParameter);

		Optional<Round> round = roundRepository.findById(gameId, roundId);
		if (round.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		roundOrchestrationService.playCard(gameId, sessionPlayerId, roundId, map(cardDtoId));
		return ResponseEntity
				.ok(roundResourceAssembler.toResource(Pair.of(gameId, round.get())));
	}

	private Card map(CardDtoId cardDtoId) {
		return new Card(cardDtoId.getColor(), cardDtoId.getRank());
	}
}
