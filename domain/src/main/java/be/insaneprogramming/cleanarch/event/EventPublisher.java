package be.insaneprogramming.cleanarch.event;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
	private List<EventListener> eventListeners;

	public EventPublisher() {
		this.eventListeners = new ArrayList<>();
	}

	public void register(EventListener eventListener) {
		eventListeners.add(eventListener);
	}

	public <E> void publish(E event) {
		eventListeners.stream().filter(it -> it.getEventClass() == event.getClass()).forEach(it -> it.onEvent(event));
	}
}
