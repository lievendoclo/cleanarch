package be.insaneprogramming.cleanarch.responsemodel;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
public class TenantResponseModel {
	private final String id;
	private final String name;

	public TenantResponseModel(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TenantResponseModel that = (TenantResponseModel) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
