package ch.zuehlke.saka.heartsweb.presentation.resources.game;

import ch.zuehlke.saka.heartsweb.domain.Game;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class GameResourceAssembler implements ResourceAssembler<Game, Resource<GameDto>> {
	@Override
	public Resource<GameDto> toResource(Game entity) {
		return new Resource<>(map(entity),
				linkTo(methodOn(GameResource.class).findById(entity.id().toString())).withSelfRel()
		);
	}

	private GameDto map(Game entity) {
		return new GameDto(entity.id().toString());
	}
}
