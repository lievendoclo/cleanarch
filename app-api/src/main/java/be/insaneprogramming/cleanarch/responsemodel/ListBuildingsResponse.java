package be.insaneprogramming.cleanarch.responsemodel;

import java.util.ArrayList;
import java.util.List;

public class ListBuildingsResponse {

	private List<ListBuildingsResponseItem> items = new ArrayList<>();

	public static class ListBuildingsResponseItem {
		private String id;
		private String name;

		public ListBuildingsResponseItem(String id, String name) {
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

	public List<ListBuildingsResponseItem> getItems() {
		return items;
	}
}
