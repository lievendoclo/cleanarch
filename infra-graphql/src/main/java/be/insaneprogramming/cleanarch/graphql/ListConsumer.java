package be.insaneprogramming.cleanarch.graphql;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListConsumer<T> implements Consumer<T> {
	private List<T> content = new ArrayList<>();

	@Override
	public void accept(T s) {
		content.add(s);
	}

	public List<T> getContent() {
		return content;
	}
}