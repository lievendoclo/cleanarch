package be.insaneprogramming.cleanarch.entity;

import java.util.Objects;

public final class TenantId {
	private final String value;

	private TenantId(String value) {
		this.value = value;
	}

	public static TenantId of(String value) {
		return new TenantId(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TenantId tenantId = (TenantId) o;
		return Objects.equals(value, tenantId.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
