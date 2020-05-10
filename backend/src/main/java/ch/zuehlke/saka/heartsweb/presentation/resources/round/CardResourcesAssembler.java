package ch.zuehlke.saka.heartsweb.presentation.resources.round;

import ch.zuehlke.saka.heartsweb.domain.Card;
import ch.zuehlke.saka.heartsweb.domain.GameId;
import ch.zuehlke.saka.heartsweb.domain.RoundId;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class CardResourcesAssembler implements
		ResourceAssembler<Triple<GameId, RoundId, Set<Card>>, Resources<Resource<CardDto>>> {

	@Override
	public Resources<Resource<CardDto>> toResource(Triple<GameId, RoundId, Set<Card>> entity) {
		GameId gameId = entity.getLeft();
		RoundId roundId = entity.getMiddle();

		List<Resource<CardDto>> resources = entity.getRight().stream()
				.map(card -> new CardDto(card.cardColor(), card.cardRank(), CardDtoId.of(card.cardColor(), card.cardRank())))
				.map(cardDto -> new Resource<>(cardDto, linkToSelf(gameId, roundId, cardDto)))
				.collect(Collectors.toList());
		return new Resources<>(resources);
	}

	private Link linkToSelf(GameId gameId, RoundId roundId, CardDto cardDto) {
		return linkTo(RoundResource.class, gameId.toString())
				.slash(roundId.toString())
				.slash("cards")
				.slash(cardDto.getId())
				.withSelfRel();
	}
}
