

import java.io.*;

class Player implements Constants {
	private String name;
	private Board board;
	private char mark;
	Player opponent;

	public Player(String nameA, char markA) {
		name = nameA;
		mark = markA;
	}

	public void setOpponent(Player other) {
		opponent = other;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String name() {
		return name;
	}

	public char mark() {
		return mark;
	}

	public void play() throws IOException {
	
		board.clear();

		while ((board.xWins() == false) && (board.oWins() == false) && (board.isFull() == false)) {
			makeMove();
			board.display();
			if (board.xWins() == false && board.isFull() == false){
				opponent.makeMove();
				board.display();
			}
		}

		System.out.print("\nTHE GAME IS OVER: ");
		if (board.xWins() != false)
			System.out.print(name() + " is the winner!\n\n");
		else if (board.oWins() != false)
			System.out.print(opponent.name() + " is the winner!\n\n");
		else
			System.out.print("the game is a tie.\n\n");
	}




	private void makeMove() throws IOException {
		String regex = "[0-9]+"; 
		System.out.print(name + ", what row should your next " + mark() + " be placed in?    ");
		int row, col;
		String rStr, cStr;

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		rStr = stdin.readLine();

		while(!rStr.matches(regex)){
			System.out.print(name + ", what row should your next " + mark() + " be placed in?    ");
			rStr = stdin.readLine();
		}
			
		row =  Integer.parseInt(rStr);

		System.out.flush();
		System.out.print(name + ", what column should your next " + mark() + " be placed in? ");

		cStr = stdin.readLine();
		
		while(!cStr.matches(regex)){
			System.out.print(name + ", what column should your next " + mark() + " be placed in?    ");
			cStr = stdin.readLine();
		}
		
		col = Integer.parseInt(cStr);

		while (true) {
            boolean acceptableInput = isAcceptableUserInput(row, col, rStr, cStr); 
			if (acceptableInput == true) break;

			System.out.print("Please enter the row again:    ");
			rStr = stdin.readLine();
			row = Integer.parseInt(rStr);
			System.out.print("Please enter the column again: ");
			cStr = stdin.readLine();
			col = Integer.parseInt(cStr);
		}
		
		board.addMark(row, col, mark());
	}
	


      private boolean isAcceptableUserInput(int row, int col, String rStr, String cStr) {
		boolean acceptableInput = true;
		if (rStr == null || cStr == null) {
			System.out.print("Sorry, " + name()
					+ ", I couldn't understand your input.\n");
			acceptableInput = false;
		} else if (row < 0 || row >= 3 || col < 0 || col >= 3) {
			System.out.print("Sorry, " + name
					+ ", but there is no square with coordinates (row="
					+ row + ", col=" + col + ").\n");
			acceptableInput = false;
		} else if (board.getMark(row, col) != SPACE_CHAR) {
			System.out.print("Sorry, " + name
					+ ", but the square with coordinates (row=" + row
					+ ", col=" + col + ") is marked.\n");
			acceptableInput = false;
		}
		
		return acceptableInput;
	}
}