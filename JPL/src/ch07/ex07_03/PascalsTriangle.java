package ch07.ex07_03;

public class PascalsTriangle {

	public static void main(String[] args) {
		//VM引数に-eaを追加して実行
		try {
			createPascalsTriangle(0);	
		} catch (AssertionError e) {
			System.out.println("0のときAssertionErrorのキャッチ確認");
		}
		try {
			createPascalsTriangle(-1);	
		} catch (AssertionError e) {
			System.out.println("負の値のときAssertionErrorのキャッチ確認");
		}
		System.out.println("深さ１");
		createPascalsTriangle(1);
		System.out.println("深さ４");
		createPascalsTriangle(4);
		System.out.println("深さ１２");
		createPascalsTriangle(12);
	}

	private static int[][] createPascalsTriangle(int depth) {
		assert depth > 0;
		
		int[][] pascalsTriangle = new int[depth][];
		
		for (int i=0; i<pascalsTriangle.length; i++) {
			pascalsTriangle[i] = new int[i+1];
			
			for (int j=0; j<pascalsTriangle[i].length; j++ ) {
				if (j==0) {
					pascalsTriangle[i][j] = 1;
				} else if (j==pascalsTriangle[i].length-1) {
					pascalsTriangle[i][j] = 1;
				} else {
					pascalsTriangle[i][j] = pascalsTriangle[i-1][j] + pascalsTriangle[i-1][j-1];
				}
				System.out.print(pascalsTriangle[i][j] + " ");
			}
			System.out.println();
		}
		return pascalsTriangle;	
	}

}
