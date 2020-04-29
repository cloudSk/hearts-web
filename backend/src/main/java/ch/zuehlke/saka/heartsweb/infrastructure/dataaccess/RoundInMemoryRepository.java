package ch.zuehlke.saka.heartsweb.infrastructure.dataaccess;

import ch.zuehlke.saka.heartsweb.domain.Round;
import ch.zuehlke.saka.heartsweb.domain.RoundRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RoundInMemoryRepository implements RoundRepository {

	@Override
	public void add(Round round) {

	}

	@Override
	public void update(Round round) {

	}
}
