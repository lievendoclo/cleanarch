package be.insaneprogramming.cleanarch.presenter.viewmodel;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PRIVATE, newBuilder = "builder")
@JsonSerialize
public interface BuildingJson {
	String getId();
	String getName();
}
