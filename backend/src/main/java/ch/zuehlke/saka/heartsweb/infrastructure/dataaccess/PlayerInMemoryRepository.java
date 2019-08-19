package ch.zuehlke.saka.heartsweb.infrastructure.dataaccess;

import ch.zuehlke.saka.heartsweb.domain.Player;
import ch.zuehlke.saka.heartsweb.domain.PlayerId;
import ch.zuehlke.saka.heartsweb.domain.PlayerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayerInMemoryRepository implements PlayerRepository {
    private final List<Player> playerList = new ArrayList<>();

    @Override
    public List<Player> findAll() {
        return Collections.unmodifiableList(playerList);
    }

    @Override
    public Optional<Player> findById(PlayerId playerId) {
        return playerList.stream()
                .filter(player -> player.id().equals(playerId))
                .findFirst();
    }

    @Override
    public void add(Player player) {
        playerList.add(player);
    }
}
