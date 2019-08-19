package ch.zuehlke.saka.heartsweb.infrastructure.resources.player;

import ch.zuehlke.saka.heartsweb.domain.Player;
import ch.zuehlke.saka.heartsweb.domain.PlayerRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/players", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PlayersResource {
    private PlayerResourceAssembler playerResourceAssembler;
    private PlayerRepository playerRepository;

    public PlayersResource(PlayerResourceAssembler playerResourceAssembler, PlayerRepository playerRepository) {
        this.playerResourceAssembler = playerResourceAssembler;
        this.playerRepository = playerRepository;
        this.playerRepository.add(new Player("foo"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<PlayerDto>> getPlayers() {
        List<Resource<PlayerDto>> resources = playerRepository.findAll().stream()
                .map(player -> playerResourceAssembler.toResource(player))
                .collect(Collectors.toList());
        return new Resources<>(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<PlayerDto>> findById(@PathVariable String id) {
        return null;
    }
}
