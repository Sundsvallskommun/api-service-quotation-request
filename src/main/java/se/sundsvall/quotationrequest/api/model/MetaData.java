package se.sundsvall.quotationrequest.api.model;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class MetaData {

	@Schema(description = "Name", example = "Blåberget")
	private String name;

	@Schema(description = "Id", example = "123231123")
	private String id;

	public static MetaData create() {
		return new MetaData();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public MetaData withName(final String name) {
		this.name = name;
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public MetaData withId(final String id) {
		this.id = id;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final var metaData = (MetaData) o;
		return Objects.equals(name, metaData.name) && Objects.equals(id, metaData.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		return builder.append("MetaData [name=").append(name).append("id=").append(id).append("]").toString();
	}
}
