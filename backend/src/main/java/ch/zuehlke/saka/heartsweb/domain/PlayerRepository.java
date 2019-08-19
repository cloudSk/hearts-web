package ch.zuehlke.saka.heartsweb.domain;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    List<Player> findAll();
    Optional<Player> findById(PlayerId playerId);
    void add(Player player);
}
