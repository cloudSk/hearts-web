package ch.zuehlke.saka.heartsweb.domain;

import ch.zuehlke.saka.heartsweb.infrastructure.dataaccess.PlayerInMemoryRepository;
import ch.zuehlke.saka.heartsweb.infrastructure.dataaccess.RoundInMemoryRepository;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class RoundOrchestrationServiceTest {

	private static final Card ANY_CARD = new Card(CardColor.DIAMONDS, CardRank.QUEEN);

	@Test
	public void playCard_notExistingPlayer_throwsIllegalArgumentException() {
		PlayerRepository emptyPlayerRepository = new PlayerInMemoryRepository();
		RoundOrchestrationService testee = new RoundOrchestrationService(emptyPlayerRepository, null);

		Throwable result = catchThrowable(
				() -> testee.playCard(GameId.generate(), PlayerId.generate(), RoundId.generate(), ANY_CARD)
		);

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCard_notExistingRound_throwsIllegalArgumentException() {
		Player player = PlayerFixture.anyPlayer();
		GameId gameId = GameId.generate();
		PlayerRepository playerRepository = playerRepositoryContainingPlayer(gameId, player);
		RoundInMemoryRepository emptyRoundRepository = new RoundInMemoryRepository();

		RoundOrchestrationService testee = new RoundOrchestrationService(playerRepository, emptyRoundRepository);
		Throwable result = catchThrowable(
				() -> testee.playCard(GameId.generate(), PlayerId.generate(), RoundId.generate(), ANY_CARD)
		);

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void playCard_existingPlayerAndRound_repositoryCallsMade() {
		Game game = GameFixture.gameWith4Players();
		Player player = PlayerFixture.anyPlayerWithId(game.sittingOrder().north());
		Round round = new Round(game.id(), player.id(), game.sittingOrder());
		player.assignHand(Collections.singletonList(ANY_CARD), round.id());
		PlayerRepository playerRepository = playerRepositoryContainingPlayer(game.id(), player);
		RoundRepository roundRepository = roundRepositoryContainingRound(round);

		RoundOrchestrationService testee = new RoundOrchestrationService(playerRepository, roundRepository);
		testee.playCard(game.id(), player.id(), round.id(), ANY_CARD);

		verify(playerRepository).update(player);
		verify(roundRepository).update(round);
	}

	private PlayerRepository playerRepositoryContainingPlayer(GameId gameId, Player player) {
		PlayerRepository repository = mock(PlayerRepository.class);
		when(repository.findById(gameId, player.id())).thenReturn(Optional.of(player));
		return repository;
	}

	private RoundRepository roundRepositoryContainingRound(Round round) {
		RoundRepository repository = mock(RoundRepository.class);
		when(repository.findById(round.id())).thenReturn(Optional.of(round));
		return repository;
	}
}