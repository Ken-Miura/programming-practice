/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_11;

import java.util.Objects;
import java.util.Random;

import ch16.ex16_11.Game.Hand;

/**
 * @author Ken Miura
 *
 */
public class RandomPlayer extends Player {

	@Override
	public void play(Game game) {
		Objects.requireNonNull(game, "game must not be null.");
		game.start(Hand.values()[new Random().nextInt(Hand.values().length)]);
	}

}
