package be.insaneprogramming.cleanarch.graphql;

import java.util.function.Consumer;

public class SimpleConsumer<T> implements Consumer<T> {
	private T content;

	@Override
	public void accept(T s) {
		content = s;
	}

	public T getContent() {
		return content;
	}
}