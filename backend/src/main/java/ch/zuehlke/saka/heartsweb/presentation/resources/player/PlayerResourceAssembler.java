package ch.zuehlke.saka.heartsweb.presentation.resources.player;

import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<Pair<GameId, Player>, Resource<PlayerDto>> {
	@Override
	public Resource<PlayerDto> toResource(Pair<GameId, Player> entity) {
		GameId gameId = entity.getLeft();
		Player player = entity.getRight();

		return new Resource<>(map(player),
				linkTo(methodOn(PlayersResource.class).findById(gameId.toString(), player.id().toString())).withSelfRel());
	}

	private PlayerDto map(Player entity) {
		return new PlayerDto(entity.name(), entity.id().toString());
	}
}
