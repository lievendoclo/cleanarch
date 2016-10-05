package be.insaneprogramming.cleanarch.responsemodel;

import java.util.ArrayList;
import java.util.List;

public class ListBuildingResponse {

	private List<ListBuildingResponseItem> items = new ArrayList<>();

	public static class ListBuildingResponseItem {
		private String id;
		private String name;

		public ListBuildingResponseItem(String id, String name) {
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

	public List<ListBuildingResponseItem> getItems() {
		return items;
	}
}
