package sudokuDesktopGame;

import java.util.Random;

public class SudokuGenerator {
	
	static SudokuPuzzle puzz = new SudokuPuzzle(9, 9, 3, 3);

	//method to generate a new Sudoku
	public static SudokuPuzzle generateSudoku() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				puzz.makeMove(i, j, "0");
			}
		}
		
		//you can use makeMove to insert
		fillValues();	
		return puzz;
	}
	
	public static void fillValues() {
		 fillDiagonal(); 
	     fillRemaining(0, 3); 
	     //save the one before remove digits somewhere
	     System.out.println("silly" + puzz);
	     
	     removeKDigits(16); // the difficulty is determined by this line!
	}

	private static boolean fillRemaining(int a, int b) { //backtracking algorithm
		if(b >= 9 && a < 8) {
			a += 1;
			b = 0;
		}
		if (a >= 9 && b >= 9) {
			return true;
		}
		if (a < 3) {
			if (b < 3) {
				b = 3;
			}
		}
		else if (a < 6) {
			if (b == (int)(a/3)*3) {
				b += 3;
			}
		}
		else {
			if (b == 6) {
				a += 1;
				b = 0;
				if (a >= 9) {
					return true;
				}
			}
		}
		
		for(int num = 1; num <= 9; num++) {
			String strnum = String.valueOf(num);
			if (CheckIfSafe(a, b, strnum)) {
				puzz.makeMove(a, b, strnum);
				if (fillRemaining(a, b + 1)) {
					return true;
				}
				else {
					puzz.makeMove(a, b, "0");
				}
			}
		}
		return false;
	}

	private static void removeKDigits(int k) {
		int count = k;
		while(count != 0) {
			Random r = new Random();
            int cell = r.nextInt(81) + 1;
            
            int i = cell/9;
            int cell2 = r.nextInt(81) + 1;

            int j = cell2 / 9; //j has problems
            //System.out.println("i value" + i);
            //System.out.println("j value" + j);
            
            if (j == 9) {
            	j -= 1;
            }
            if (i == 9) {
            	i -= 1;
            }
            
            if(!(puzz.getValue(i, j).equals("0"))) {
            	count--;
            	puzz.makeMove(i, j, "0");
            	
            	//change it in the mutable list as well
            	puzz.makeMoveBool(i, j, true);
            }
		}
	}

	private static void fillDiagonal() {
		for (int i = 0; i < 9; i += 3) {
            // for diagonal box, start coordinates->i==j 
            fillBox(i, i);
		}
			  
	}
	
	//return false if it already exists in that row
	private static boolean unUsedInRow(int i, String num) 
    { 
        for (int j = 0; j < 9; j++) {
           if (puzz.getValue(i, j).equals(num)) { 
                return false;
               }
           }
        return true; 
    } 
	
	private static boolean unUsedInCol(int j, String num) 
    { 
        for (int i = 0; i < 9; i++) {
           if (puzz.getValue(i, j).equals(num)) { 
                return false;
               }
           }
        return true; 
    } 
	
	private static boolean CheckIfSafe(int i, int j, String num) 
	{
		if (unUsedInRow(i, num) && unUsedInCol(j, num) && unUsedInBox((i - i%3), (j - j%3), num)) {
			return true;
		}
		return false;
	}
	
	//return false if the block contains that number
	private static boolean unUsedInBox(int rowStart, int colStart, String num) {
        for (int i = 0; i< 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzz.getValue(rowStart + i, colStart + j).equals(num)) {
                	return false;
                }
            }
        }
        return true;

	 }
	
	private static void fillBox(int row, int col) {
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
            	//generate a random number from 1 to 9
            	 Random r = new Random();
                 int nfill = r.nextInt(9) + 1;
                 String nstring = String.valueOf(nfill);
                 
                 while(!unUsedInBox(row, col, nstring)) {
                	 Random r1 = new Random();
                     int nfill1 = r1.nextInt(9) + 1;
                     nstring = String.valueOf(nfill1);
                 }
                puzz.makeMove(row + i, col + j, nstring);
            }
        } 
	}
}