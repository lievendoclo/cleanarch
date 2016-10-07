package be.insaneprogramming.cleanarch.responsemodel;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PRIVATE, newBuilder = "builder")
public interface BuildingResponseModel {
	String getId();
	String getName();
}
