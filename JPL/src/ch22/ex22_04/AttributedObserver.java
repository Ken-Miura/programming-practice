/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_04;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Ken Miura
 *
 */
public final class AttributedObserver<E> implements Observer {

	// 通知されてることの確認
	public static void main (String... args) {
		final Attr<Integer> attr = new Attr<>("int", 1);
		final ObservableAttributed<Integer> observableAttributed = new ObservableAttributed<>();
		final AttributedObserver<Integer> observer = new AttributedObserver<>(observableAttributed);
		observableAttributed.addObserver(observer);
		
		System.out.println("add");
		observableAttributed.add(attr);
		
		System.out.println("remove");
		observableAttributed.remove(attr.getName());
	}
	
	private final ObservableAttributed<E> obsrervable;
	
	public AttributedObserver (ObservableAttributed<E> obsrervable) {
		Objects.requireNonNull(obsrervable, "obsrervable must not be null");
		this.obsrervable = obsrervable;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (obsrervable != o) {
			throw new IllegalArgumentException("o must be same obsrervable");
		}
		System.out.println(arg);
	}
}
