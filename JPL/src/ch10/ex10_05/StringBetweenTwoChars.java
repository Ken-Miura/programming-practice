package ch10.ex10_05;

public class StringBetweenTwoChars {

	public static void main(String[] args) {
		try {
			displayStringBetweenTwoChars('b', 'a');
		} catch (IllegalArgumentException e) {
			System.out.println("一つ目のUnicode値が二つ目のUnicode値より大きいとき例外が投げられることの確認");	
		}
		displayStringBetweenTwoChars('a', 'a');
		System.out.println();
		displayStringBetweenTwoChars('a', 'z');
		System.out.println();
		displayStringBetweenTwoChars('A', 'z');
	}
	
	public static void displayStringBetweenTwoChars (char start, char end) {
		if (start > end) {
			throw new IllegalArgumentException("start must be less than end");
		}
		for (char c = start; c < end + 1; c++) {
			System.out.print(c);
		}
	}

}
