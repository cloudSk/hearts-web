package ch.zuehlke.saka.heartsweb.domain;

public class Player {
    private PlayerId id;
    private String name;

    public Player(String name) {
        this.name = name;
        this.id = PlayerId.generate();
    }

    public PlayerId id() {
        return id;
    }

    public String name() {
        return name;
    }
}
