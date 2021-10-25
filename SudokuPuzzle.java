package sudokuDesktopGame;

public class SudokuPuzzle {
	protected String[][] board;
	protected boolean mutable[][]; //this shit is to find out whether that slot is mutable
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	
	public SudokuPuzzle(int ROWS, int COLUMNS, int BOXWIDTH, int BOXHEIGHT) {
		//assign the parameters to existing variables
		this.ROWS = ROWS;
		this.COLUMNS = COLUMNS;
		this.BOXWIDTH = BOXWIDTH;
		this.BOXHEIGHT = BOXHEIGHT;
		this.board = new String[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		initializeBoard();
		initializeMutable();
	}

	public int getNumRows() {
		return ROWS;
	}
	
	public int getNumColumns() {
		return COLUMNS;
	}
	
	public int getBoxWidth() {
		return BOXWIDTH;
	}
	
	public int getBoxHeight() {
		return BOXHEIGHT;
	}

	//@Override
	public String toString() {
		String str = "Game Board: " + "\n";
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				str += this.board[i][j] + " ";
			}
			str += "\n";
		}
		return str + "\n";
	}
	
	private void initializeBoard() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				this.board[i][j] = ""; //i reps rows, j reps cols
			}
		}
	}
	

	private void initializeMutable() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				this.mutable[i][j] = false; //i reps rows, j reps cols
			}
		}
	}

	public void makeMove(int row, int column, String value) {
		this.board[row][column] = value;
	}
	
	public void UsermakeMove(int row, int col, String value) {
		//hoho
		if(isSlotMutable(row, col) && isValidMove(row, col, value)) {
			this.board[row][col] = value;
			//there's something else here but i dont care
		}
		
	}
	
	//return true means number already exists in columns
	public boolean numInCol(int col, String value) {
		for(int row = 0; row < this.ROWS; row++) {
			if(this.board[row][col].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	//return true means number already exists in row
	public boolean numInRow(int row, String value) {
		for(int col = 0; col < this.COLUMNS; col++) {
			if(this.board[row][col].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean numInBox(int row, int col, String value) {
		row = row - row%3;
		col = col - col%3;
		for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (this.board[row + a][col + b].equals(value)) {
                	return true;
                }
            }
        }
        return false;
	}
	
	public boolean isValidMove(int row, int col, String value) {
		if(!this.numInBox(row, col, value) && !this.numInCol(col, value) && !this.numInRow(row, value)) {
			return true;
		}
		return false;
	}
	
	public boolean isSlotMutable(int row, int col) {
		return this.mutable[row][col];
	}
	
	public void makeMoveBool(int row, int column, boolean value) {
		this.mutable[row][column] = value;
	}
	
	public String getValue(int a, int b) {
		String val = this.board[a][b];
		return val;
	}
}