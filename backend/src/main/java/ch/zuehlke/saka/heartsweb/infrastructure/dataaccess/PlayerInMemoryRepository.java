package ch.zuehlke.saka.heartsweb.infrastructure.dataaccess;

import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.Player;
import ch.zuehlke.saka.heartsweb.domain.PlayerId;
import ch.zuehlke.saka.heartsweb.domain.PlayerRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PlayerInMemoryRepository implements PlayerRepository {
	private final Map<GameId, List<Player>> players = new HashMap<>();

	@Override
	public List<Player> findAllInGame(GameId gameId) {
		List<Player> players = this.players.getOrDefault(gameId, new ArrayList<>());
		return Collections.unmodifiableList(players);
	}

	@Override
	public Optional<Player> findById(GameId gameId, PlayerId playerId) {
		return players.getOrDefault(gameId, new ArrayList<>()).stream()
				.filter(player -> player.id().equals(playerId))
				.findFirst();
	}

	@Override
	public void add(GameId gameId, Player player) {
		players.merge(gameId, listOf(player), (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		});
	}

	private List<Player> listOf(Player player) {
		List<Player> playerList = new ArrayList<>();
		playerList.add(player);
		return playerList;
	}
}
