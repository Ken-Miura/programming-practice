/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import ch16.ex16_12.Game.Hand;

/**
 * @author Ken Miura
 *
 */
public final class StrategicPlayer extends Player {

	private URL strategy;
	
	@Override
	public void setStrategy(URL strategy) {
		this.strategy = strategy;
	}
	
	@Override
	public void play(Game game) {
		Objects.requireNonNull(game, "game must not be null.");
		if (strategy == null || strategy.getPath().equals("")) {
			game.start(Hand.ROCK);
		} else {
			try (BufferedReader br = new BufferedReader(new FileReader(new File(strategy.getPath())))) {
				String line = br.readLine();
				String[] keyAndValue = line.split("=");
				if (keyAndValue[1].equalsIgnoreCase("true")) {
					game.start(Hand.values()[new Random().nextInt(Hand.values().length)]);			
				} else if (keyAndValue[1].equalsIgnoreCase("false")) {
					game.start(Hand.ROCK);
				} else {
					System.err.println("parse error");
					game.start(Hand.ROCK);
				}
			} catch (IOException e) {
				e.printStackTrace();
				game.start(Hand.ROCK);
			}
		}
	}

}
