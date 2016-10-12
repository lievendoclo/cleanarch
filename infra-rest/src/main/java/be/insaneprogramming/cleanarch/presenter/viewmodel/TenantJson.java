package be.insaneprogramming.cleanarch.presenter.viewmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface TenantJson {
	String getId();
	String getName();
}
