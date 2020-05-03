package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.Round;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RoundResourceAssembler implements ResourceAssembler<Pair<GameId, Round>, Resource<RoundDto>> {

	@Override
	public Resource<RoundDto> toResource(Pair<GameId, Round> entity) {
		GameId gameId = entity.getLeft();
		Round round = entity.getRight();

		return new Resource<>(map(round),
				linkTo(methodOn(RoundResource.class).findById(gameId.toString(), round.id().toString())).withSelfRel());
	}

	private RoundDto map(Round entity) {
		return new RoundDto(entity.id().toString());
	}
}
