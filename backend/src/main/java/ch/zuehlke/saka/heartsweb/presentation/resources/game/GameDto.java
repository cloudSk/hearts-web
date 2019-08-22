package ch.zuehlke.saka.heartsweb.presentation.resources.game;

public class GameDto {
	private String id;

	public GameDto() {
	}

	public GameDto(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
