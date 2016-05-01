/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_05;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * @author Ken Miura
 *
 */
public final class Probability {

	private static final Random r = new Random();
	private static final Map<Integer, Integer> sumOfDiceNumAndCount = new HashMap<>();
	private static final int NUM_OF_DICE = 2;
	private static final int NUMBER_OF_TRIALS = 1000000;
	
	/**
	 * サイコロ2個の時
	 * 2 → 1/36
	 * 3 → 1/18
	 * 4 → 1/12
	 * 5 → 1/9
	 * 6 → 5/36
	 * 7 → 1/6
	 * 8　→ 5/36
	 * 9 → 1/9
	 * 10 → 1/12
	 * 11 → 1/18
	 * 12 → 1/36
	 * なので試行回数を増やせば大体あってる
	 */
	public static void main(String[] args) {
		test();
		System.out.println("NUM_OF_DICE: " + NUM_OF_DICE);
		for (final Entry<Integer, Integer> entry: sumOfDiceNumAndCount.entrySet()) {
			int sumOfDiceNum = entry.getKey();
			int count = entry.getValue();
			double probability = ((double) count / (double) NUMBER_OF_TRIALS);
			System.out.print("sumOfDiceNum: " + sumOfDiceNum + ", ");
			System.out.printf("probability: %f%n", probability);
		}
	}

	private static void test () {
		for (int i=0; i<NUMBER_OF_TRIALS; i++) {
			int sumOfDiceNum = 0;
			for (int j=0; j<NUM_OF_DICE; j++) {
				sumOfDiceNum+=throwDice();
			}
			Integer count = sumOfDiceNumAndCount.get(sumOfDiceNum);
			if (count == null) {
				sumOfDiceNumAndCount.put(sumOfDiceNum, 1);
			} else {
				sumOfDiceNumAndCount.put(sumOfDiceNum, ++count);
			}
		}
	}
	
	// 線形合同発生法で確率が一様分布になるので、どのメソッドを使っても同じ結果に収束する
	private static int throwDice () {
		return r.nextInt(6) + 1;
		
		//int intNum = r.nextInt();
		//int diceNum = (Math.abs(intNum) % 6) + 1;
		//return diceNum;
		
		//long longNum = r.nextLong();
		//int diceNum = (int) ((Math.abs(longNum) % 6L) + 1L);
		//return diceNum;
		
		//float floatNum = r.nextFloat();
		//int diceNum = (int)(floatNum * 6.0f + 1.0f); // float → intは小数点以下切り捨て
		//return diceNum;
		
		//double doubleNum = r.nextDouble();
		//int diceNum = (int)(doubleNum * 6.0 + 1.0); // double → intは小数点以下切り捨て
		//return diceNum;
	}
}
