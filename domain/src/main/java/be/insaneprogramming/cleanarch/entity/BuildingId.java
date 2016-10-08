package be.insaneprogramming.cleanarch.entity;

import org.immutables.value.Value;

@Value.Immutable
public interface BuildingId {
	@Value.Parameter
	String get();
}
