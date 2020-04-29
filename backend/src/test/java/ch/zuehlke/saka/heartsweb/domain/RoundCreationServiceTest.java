package ch.zuehlke.saka.heartsweb.domain;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class RoundCreationServiceTest {

	@Test
	public void createRound_forNotExistingGame_throwsIllegalArgumentException() {
		GameRepository emptyGameRepository = gameRepositoryContainingGames();
		RoundCreationService testee = new RoundCreationService(emptyGameRepository, null, null);

		Throwable result = catchThrowable(() -> testee.createRound(GameId.generate()));

		assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void createRound_noPlayersInGame_throwsIllegalStateException() {
		Game game = new Game();
		GameRepository gameRepository = gameRepositoryContainingGames(game);
		PlayerRepository emptyPlayerRepository = emptyPlayerRepository();
		RoundCreationService testee = new RoundCreationService(gameRepository, emptyPlayerRepository, null);

		Throwable result = catchThrowable(() -> testee.createRound(game.id()));

		assertThat(result).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void createRound_gameWith4Players_roundCreatedAndStoredWithUniqueHands() {
		Game game = GameFixture.gameWith4Players();
		GameRepository gameRepository = gameRepositoryContainingGames(game);
		PlayerRepository playerRepository = playerRepositoryWithPlayersOfGame(game);
		RoundRepository roundRepository = mock(RoundRepository.class);
		RoundCreationService testee = new RoundCreationService(gameRepository, playerRepository, roundRepository);

		Round result = testee.createRound(game.id());

		assertThat(result).isNotNull();
		assertThat(result.sittingOrder()).isEqualTo(game.sittingOrder());
		assertUniqueHands(playerRepository.findAllInGame(game.id()));
		assertThat(result.nextPlayer()).isEqualTo(playerWithClubs2(playerRepository.findAllInGame(game.id())));
		verify(playerRepository, times(4)).update(any());
		verify(roundRepository).add(any());
	}

	private GameRepository gameRepositoryContainingGames(Game... games) {
		GameRepository mock = mock(GameRepository.class);
		when(mock.findById(any())).thenReturn(Optional.empty());
		for (Game game : games) {
			when(mock.findById(game.id())).thenReturn(Optional.of(game));
		}
		return mock;
	}

	private PlayerRepository emptyPlayerRepository() {
		PlayerRepository mock = mock(PlayerRepository.class);
		when(mock.findAllInGame(any())).thenReturn(new ArrayList<>());
		return mock;
	}

	private PlayerRepository playerRepositoryWithPlayersOfGame(Game game) {
		PlayerRepository mock = mock(PlayerRepository.class);

		List<PlayerId> playerIds = Arrays.asList(game.sittingOrder().north(), game.sittingOrder().east(),
				game.sittingOrder().south(), game.sittingOrder().west());
		List<Player> players = playerIds.stream()
				.map(PlayerFixture::anyPlayerWithId)
				.collect(Collectors.toList());

		when(mock.findAllInGame(game.id())).thenReturn(players);
		return mock;
	}

	private PlayerId playerWithClubs2(List<Player> players) {
		return players.stream()
				.filter(player -> player.remainingHand().contains(new Card(CardColor.CLUBS, CardRank.NUMBER_02)))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No player owns clubs 2"))
				.id();
	}

	private void assertUniqueHands(List<Player> players) {
		assertThat(players).hasSize(4);
		assertThat(players).allMatch(player -> player.remainingHand().size() == 13);

		List<Card> first = players.get(0).remainingHand();
		List<Card> second = players.get(1).remainingHand();
		List<Card> third = players.get(2).remainingHand();
		List<Card> fourth = players.get(3).remainingHand();

		assertThat(first).doesNotContainAnyElementsOf(joinLists(second, third, fourth));
		assertThat(second).doesNotContainAnyElementsOf(joinLists(first, third, fourth));
		assertThat(third).doesNotContainAnyElementsOf(joinLists(first, second, fourth));
		assertThat(fourth).doesNotContainAnyElementsOf(joinLists(first, second, third));
	}

	private static List<Card> joinLists(List<Card>... lists) {
		return Arrays.stream(lists)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}