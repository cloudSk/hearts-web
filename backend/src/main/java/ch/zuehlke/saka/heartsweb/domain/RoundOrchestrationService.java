package ch.zuehlke.saka.heartsweb.domain;

import org.springframework.stereotype.Service;

@Service
public class RoundOrchestrationService {
	private final PlayerRepository playerRepository;
	private final RoundRepository roundRepository;

	public RoundOrchestrationService(PlayerRepository playerRepository, RoundRepository roundRepository) {
		this.playerRepository = playerRepository;
		this.roundRepository = roundRepository;
	}

	public void playCard(GameId gameId, PlayerId playerId, RoundId roundId, Card cardToPlay) {
		Player player = playerRepository.findById(gameId, playerId)
				.orElseThrow(() -> new IllegalArgumentException("Player with playerId=" + playerId + " not found in game=" + gameId));
		Round round = roundRepository.findById(roundId)
				.orElseThrow(() -> new IllegalArgumentException("Round with roundId=" + roundId + " not found"));

		player.playCardToRound(cardToPlay, round);
		playerRepository.update(player);
		roundRepository.update(round);

		DomainEventPublisher.instance()
				.cardPlayed()
				.publish(new CardPlayed(playerId, gameId, roundId, cardToPlay));
	}
}
