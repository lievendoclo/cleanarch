package be.insaneprogramming.cleanarch.responsemodel;

import org.immutables.value.Value;

@Value.Immutable
public interface BuildingResponseModel {
	String getId();
	String getName();
}
