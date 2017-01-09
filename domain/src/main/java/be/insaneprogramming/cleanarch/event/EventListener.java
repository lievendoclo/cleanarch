package be.insaneprogramming.cleanarch.event;

public interface EventListener<E> {
	Class<E> getEventClass();
	void onEvent(E event);
}
