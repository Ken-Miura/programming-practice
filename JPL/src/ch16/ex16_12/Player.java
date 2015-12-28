/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_12;

import java.net.URL;

/**
 * @author Ken Miura
 *
 */
public abstract class Player {

	public abstract void play(Game game);

	public abstract void setStrategy(URL strategy);
	
}
