package ch.zuehlke.saka.heartsweb.infrastructure.resources.player;

import ch.zuehlke.saka.heartsweb.domain.Player;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<Player, Resource<PlayerDto>> {
    @Override
    public Resource<PlayerDto> toResource(Player entity) {
        return new Resource<>(map(entity),
                linkTo(methodOn(PlayersResource.class).findById(entity.id().id())).withSelfRel()
        );
    }

    private PlayerDto map(Player entity) {
        return new PlayerDto(entity.name(), entity.id().id());
    }
}
