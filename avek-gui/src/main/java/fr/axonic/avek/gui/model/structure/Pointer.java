package fr.axonic.avek.gui.model.structure;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class Pointer<T> {
	private volatile T value;

	public Pointer(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}
}
