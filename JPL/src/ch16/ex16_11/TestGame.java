/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_11;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import ch16.ex16_11.Game.Hand;
import ch16.ex16_11.Game.Result;

/**
 * @author Ken Miura
 *
 */
public class TestGame {

	private static final String HOSTILE_HAND = "hostileHand";
	private static final String SCORE = "score";
	
	@Test
	public void 相手がグーのときのテスト() {
		Game game = new Game();
		Field hostileHand = null;
		try {
			hostileHand = game.getClass().getDeclaredField(HOSTILE_HAND);
			hostileHand.setAccessible(true);
			hostileHand.set(game, Hand.ROCK);
		} catch (Exception e) {
			fail();
		}
		Field score = null;
		try {
			score = game.getClass().getDeclaredField(SCORE);
			score.setAccessible(true);
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.ROCK);
		try {
			if (score.get(game) != Result.DRAW) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.SCISSORS);
		try {
			if (score.get(game) != Result.LOSE) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.PAPER);
		try {
			if (score.get(game) != Result.WIN) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void 相手がパーのときのテスト() {
		Game game = new Game();
		Field hostileHand = null;
		try {
			hostileHand = game.getClass().getDeclaredField(HOSTILE_HAND);
			hostileHand.setAccessible(true);
			hostileHand.set(game, Hand.PAPER);
		} catch (Exception e) {
			fail();
		}
		Field score = null;
		try {
			score = game.getClass().getDeclaredField(SCORE);
			score.setAccessible(true);
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.ROCK);
		try {
			if (score.get(game) != Result.LOSE) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.SCISSORS);
		try {
			if (score.get(game) != Result.WIN) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.PAPER);
		try {
			if (score.get(game) != Result.DRAW) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void 相手チョキのときのテスト() {
		Game game = new Game();
		Field hostileHand = null;
		try {
			hostileHand = game.getClass().getDeclaredField(HOSTILE_HAND);
			hostileHand.setAccessible(true);
			hostileHand.set(game, Hand.SCISSORS);
		} catch (Exception e) {
			fail();
		}
		Field score = null;
		try {
			score = game.getClass().getDeclaredField(SCORE);
			score.setAccessible(true);
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.ROCK);
		try {
			if (score.get(game) != Result.WIN) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.SCISSORS);
		try {
			if (score.get(game) != Result.DRAW) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
		
		game.start(Hand.PAPER);
		try {
			if (score.get(game) != Result.LOSE) {
				fail();
			}
		} catch (Exception e) {
			fail();
		}
	}
}
