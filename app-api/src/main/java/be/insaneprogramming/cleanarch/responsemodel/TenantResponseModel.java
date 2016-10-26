package be.insaneprogramming.cleanarch.responsemodel;

public final class TenantResponseModel {
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
}
