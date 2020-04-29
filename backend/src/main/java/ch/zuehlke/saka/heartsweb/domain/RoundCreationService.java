package ch.zuehlke.saka.heartsweb.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class RoundCreationService {
	private final GameRepository gameRepository;
	private final PlayerRepository playerRepository;
	private final RoundRepository roundRepository;

	public RoundCreationService(GameRepository gameRepository, PlayerRepository playerRepository, RoundRepository roundRepository) {
		this.gameRepository = gameRepository;
		this.playerRepository = playerRepository;
		this.roundRepository = roundRepository;
	}

	public Round createRound(GameId gameId) {
		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new IllegalArgumentException("Game with gameId=" + gameId + " not found"));

		List<Player> players = playerRepository.findAllInGame(gameId);
		if (players.size() != Game.PLAYERS_PER_GAME) {
			String message = String.format("There should be %d players (instead of %d) in a game to create a round",
					Game.PLAYERS_PER_GAME, players.size());
			throw new IllegalStateException(message);
		}

		assignRandomHands(players);

		Player roundInitiator = determineRoundInitiator(players);
		Round round = new Round(game.id(), roundInitiator.id(), game.sittingOrder());
		roundRepository.add(round);

		return round;
	}

	private void assignRandomHands(List<Player> players) {
		List<List<Card>> hands = createRandomHands();
		IntStream.range(0, Game.PLAYERS_PER_GAME).forEach(index -> {
			List<Card> cardsInHand = hands.get(index);
			players.get(index).assignHand(cardsInHand);
		});

		players.forEach(playerRepository::update);
	}

	private Player determineRoundInitiator(List<Player> players) {
		return players.stream()
				.filter(this::holdsClubsNumber2)
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No player holds clubs 2"));
	}

	private boolean holdsClubsNumber2(Player player) {
		return player.remainingHand().stream()
				.anyMatch(card -> card.cardColor() == CardColor.CLUBS &&
						card.cardRank() == CardRank.NUMBER_02);
	}

	private List<List<Card>> createRandomHands() {
		List<Card> allCards = Arrays.stream(CardColor.values())
				.flatMap(this::allCardsOfColor)
				.collect(Collectors.toList());
		Collections.shuffle(allCards);

		final AtomicInteger counter = new AtomicInteger(0);
		return new ArrayList<>(allCards.stream()
				.collect(Collectors.groupingBy(card -> counter.getAndIncrement() % Game.PLAYERS_PER_GAME))
				.values());
	}

	private Stream<Card> allCardsOfColor(CardColor cardColor) {
		return Arrays.stream(CardRank.values())
				.map(cardRank -> new Card(cardColor, cardRank));
	}
}
