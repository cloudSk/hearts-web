package ch.zuehlke.saka.heartsweb.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlayerJoiningService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerJoiningService.class);

	private final PlayerRepository playerRepository;

	public PlayerJoiningService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public void joinGame(Player player, Game game) {
		game.joinGame(player);

		LOGGER.info("Adding player with name={} and id={} to gameId={}", player.name(), player.id(), game.id());
		playerRepository.add(game.id(), player);

		DomainEventPublisher.instance()
				.playerJoinedGame()
				.publish(new PlayerJoinedGame(player.id(), game.id()));
	}
}
