package ch.zuehlke.saka.heartsweb.infrastructure.resources.player;

public class PlayerDto {
	private String name;
	private String id;

	public PlayerDto() {
	}

	public PlayerDto(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
