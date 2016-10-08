package be.insaneprogramming.cleanarch.requestmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface CreateBuildingRequest {
	String getName();
}
