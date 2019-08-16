package ch.zuehlke.saka.heartsweb.infrastructure.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayersResource {

    @RequestMapping(method = RequestMethod.GET)
    public List<String> getPlayers() {
        return Arrays.asList("Foo", "Bar");
    }
}
