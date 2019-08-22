package ch.zuehlke.saka.heartsweb.presentation.resources.player;

import ch.zuehlke.saka.heartsweb.domain.Player;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<Player, Resource<PlayerDto>> {
	@Override
	public Resource<PlayerDto> toResource(Player entity) {
		// TODO
		return new Resource<>(map(entity));
	}

	private PlayerDto map(Player entity) {
		return new PlayerDto(entity.name(), entity.id().id());
	}
}
