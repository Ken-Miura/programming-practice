/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_12;

import java.net.URL;
import java.util.Objects;
import java.util.Random;

/**
 * @author Ken Miura
 *
 */
public class Game {

	private static final String[] ALL_PLAYERS = { "ch16.ex16_12.StrategicPlayer" };
	private static int index = 0;
	
	public static enum Hand {
		ROCK, PAPER, SCISSORS
	}
	
	public static enum Result {
		WIN, LOSE, DRAW
	}
	
	private Result score = null;
	private final Hand hostileHand = Hand.values()[new Random().nextInt(Hand.values().length)];
	
	public static void main(String[] args) {
		String name;
		String strategyFile = "strategy.txt";
		while ((name = getNextPlayer()) != null) {
			try {
				PlayerLoader loader = new PlayerLoader();
				Class<? extends Player> classOf = loader.loadClass(name).asSubclass(Player.class);
				Player player = classOf.newInstance();
				
				URL url = loader.getResource(strategyFile);
				player.setStrategy(url);
				
				Game game = new Game();
				player.play(game);
				game.reportScore(name);
			} catch (Exception e) {
				reportException(name, e);
			} 
		}
	}
	
	private static String getNextPlayer() {
		if (index < ALL_PLAYERS.length) {
			String nextPlayer = ALL_PLAYERS[index];
			index++;
			return nextPlayer;
		}
		return null;
	}
	
	private static void reportException(String name, Exception e) {
		System.out.println("name: " + name + ", exception: " + e);
	}

	private void reportScore(String name) {
		System.out.println("Player: " + name + ", Score: " + score);
	}

	public void start (Hand yourHand) {
		Objects.requireNonNull(yourHand, "hand must not be null.");
		if (yourHand == hostileHand) {
			score = Result.DRAW;
			return;
		}
		
		if (yourHand == Hand.ROCK && hostileHand == Hand.PAPER) {
			score = Result.LOSE;
			return;
		}
		if (yourHand == Hand.ROCK && hostileHand == Hand.SCISSORS) {
			score = Result.WIN;
			return;
		}
		
		if (yourHand == Hand.PAPER && hostileHand == Hand.ROCK) {
			score = Result.WIN;
			return;
		}
		if (yourHand == Hand.PAPER && hostileHand == Hand.SCISSORS) {
			score = Result.LOSE;
			return;
		}
		
		if (yourHand == Hand.SCISSORS && hostileHand == Hand.ROCK) {
			score = Result.LOSE;
			return;
		}
		if (yourHand == Hand.SCISSORS && hostileHand == Hand.PAPER) {
			score = Result.WIN;
			return;
		}
		
		throw new AssertionError("must not reach here.");
	}
}
