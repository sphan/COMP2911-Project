import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class GameInterface {
	static JFrame frame;
	static Container pane;
	//The boxes themselves
	static JButton[][] box;
	//The smaller boxes which can be used to store possible numbers
	static JButton btnInputMode;
	static JLabel lblInputLabel;
	static JButton btnQuit;
	static JButton btnHint;
	//static Square[][] entry;
	
	
	static final int boxWidth = 50;
	static final int boxHeight = 50;
	//static final int subBoxHeight = 20;
	//static final int subBoxWidth = 50;
	static final int frameWidth = 600;
	static final int frameHeight = 600;
	
	public GameInterface(Square[][] newLayout){
		boardLayout = newLayout;
		
		frame = new JFrame("Sudoku");
		frame.setSize(frameWidth, frameHeight);
		pane = frame.getContentPane();
		
		pane.setLayout(null);
		
		box = new JButton[9][9];
		//Get it so that I take this from the puzzle
		//entry = new Square[9][9];
		//subBox = new JTextPane[9][9];
		setStartingBoxInfo();
		
		
		btnInputMode = new JButton("Entry Mode");
		lblInputLabel = new JLabel("Current Writing Mode Click or hold Shift to change");
		pane.add(btnInputMode);
		pane.add(lblInputLabel);	
		btnInputMode.setBounds(10, frameHeight - 60, btnInputMode.getPreferredSize().width + 50, btnInputMode.getPreferredSize().height);
		lblInputLabel.setBounds(10, frameHeight - 80, lblInputLabel.getPreferredSize().width, lblInputLabel.getPreferredSize().height);
		btnInputMode.addActionListener(new btnInputModeListener());
		
		btnHint = new JButton("HINT");
		pane.add(btnHint);
		btnHint.setBounds(360, frameHeight - 60, btnHint.getPreferredSize().width + 50, btnInputMode.getPreferredSize().height);
		btnHint.addActionListener(new btnHintListener());
		
		btnQuit = new JButton("QUIT");
		pane.add(btnQuit);
		btnQuit.setBounds(200, frameHeight - 60, btnQuit.getPreferredSize().width + 50, btnInputMode.getPreferredSize().height);
		btnQuit.addActionListener(new btnQuitListener());

		//frame.addKeyListener(new keyPressedListener());
		frame.requestFocus();
		frame.setVisible(true);
	}
		
	/**
	 * Returns the integer of the given square or -1 if the square is empty
	 * @param row	The row value of the square (0-8)
	 * @param col	The column value of the square (0-8)
	 * @return		The integer of the square
	 */
	public int getBoxValue(int row, int col){
		return boardLayout[row][col].getCurrentValue();
	}
	
	/**
	 * Checks if the player has won the game. This checks
	 * if the boards if completely filled first, then for each filled
	 * squares, checks if there are duplicates in the regions.
	 * @return True if the board is completely filled and all values
	 * are valid. False otherwise.
	 */
	public boolean hasWon() {
		boolean boardFilled = false;
		for (int i = 0; i < Puzzle.ROW_NUMBER; i++) {
			for (int j = 0; j < Puzzle.COLUMN_NUMBER; j++) {
				if (boardLayout[i][j].getCurrentValue() != 0) {
					boardFilled = true;
				} else {
					boardFilled = false;
					break;
				}
			}
		}
		
		if (boardFilled) {
			for (int i = 0; i < Puzzle.ROW_NUMBER; i++) {
				for (int j = 0; j < Puzzle.COLUMN_NUMBER; j++) {
					if (!LegalCheck.checkLegal(boardLayout, boardLayout[i][0], boardLayout[i][0].getCurrentValue()))
						return false;
				}
			}
		} else
			return false;
		return true;
	}
	
	//TODO Input to set the setting of the boxes (E.g. red "MISTAKE" color)
	/**
	 * De-selects all squares
	 */
	public static void deselectAll(){
		inputX = -1;
		inputY = -1;
		source = null;
	}
	
	/**
	 * Returns whether or not there is a selected square
	 * @return	True if a square is selected, false otherwise
	 */
	public boolean squareSelected(){
		return (source != null);
	}

	
	private static void setStartingBoxInfo(){
		int x = 0;
		int y = 0;
		Integer value;
		while (y < 9){
			while (x < 9){
				box[x][y] = new JButton();
				//subBox[x][y] = new JTextPane();
				pane.add(box[x][y]);
				//pane.add(subBox[x][y]);
				box[x][y].setBounds(x*boxWidth+10, y*boxHeight+10, boxWidth, boxHeight);
				box[x][y].addActionListener(new btnSquareListener(x, y));
				box[x][y].addKeyListener(new keyPressedListener(y, x));
				value = boardLayout[y][x].getCurrentValue();
				if (value != 0){
					box[x][y].setText(value.toString());
				}
				//box[x][y].setForeground(defaultBGColor);
				//subBox[x][y].setBounds(x*boxWidth+10, y*boxHeight+10, boxWidth, boxHeight);
				//subBox[x][y].setEnabled(false);
				//entry[x][y] = new Square(0, 0);
				x++;
			}
			y++;
			x = 0;
		}
	}
	
	private static void resetSourceBox(){
		System.out.println("STUFF");
		//If there is a set value for the square, set it to that
		if (boardLayout[inputY][inputX].getCurrentValue() > 0){
			source.setText(Integer.toString(boardLayout[inputY][inputX].getCurrentValue()));
		} else {
			Square tempDraft = boardLayout[inputY][inputX];
			Integer x = 0;
			String boxString = "";
			while (x < 9){
				if (tempDraft.isMarkedDraft(x)){
					boxString = (boxString + " " + x.toString());
				}
				x++;
			}
			source.setText(boxString);
		}
		pane.repaint();

	}
		
	private static boolean draftEntry = false;
	private static JButton source;
	private static int inputX;
	private static int inputY;
	private static Square[][] boardLayout;
	
	//============================================================================================================================================================
	//AAAAAAAA  CCCCCCCC  TTTTTTTTTT  IIIIII  OOOOOOOO  NN      NN        LL      IIIIII  SSSSSSSS  TTTTTTTTTT  EEEEEEEE  NN      NN  EEEEEEEE  RRRRRR    SSSSSSSS
	//AA    AA  CC            TT        II    OO    OO  NNNN    NN        LL        II    SS            TT      EE        NNNN    NN  EE        RR    RR  SS
	//AA    AA  CC            TT        II    OO    OO  NN  NN  NN        LL        II    SSSS          TT      EEEEEE    NN  NN  NN  EEEEEE    RRRRRR    SSSS
	//AAAAAAAA  CC            TT        II    OO    OO  NN  NN  NN        LL        II        SSSS      TT      EE        NN  NN  NN  EE        RRRR          SSSS
	//AA    AA  CC            TT        II    OO    OO  NN    NNNN        LL        II          SS      TT      EE        NN    NNNN  EE        RR  RR          SS
	//AA    AA  CCCCCCCC      TT      IIIIII  OOOOOOOO  NN      NN        LLLLLL  IIIIII  SSSSSSSS      TT      EEEEEEEE  NN      NN  EEEEEEEE  RR    RR  SSSSSSSS
	//============================================================================================================================================================
	
	/**
	 * Action Listener to change the input mode
	 * @author Sam
	 */
	public static class btnInputModeListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (draftEntry == true){
				btnInputMode.setText("Entry Mode");
				draftEntry = false;
				deselectAll();
				//switchEntryMode();
			} else {
				btnInputMode.setText("Draft Notes Mode");
				draftEntry = true;
				deselectAll();
				//switchEntryMode();
			}
		}
		
	}
	
	/**
	 * Action Listener to quit
	 * @author Sam
	 */
	public static class btnQuitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	
	/**
	 * 
	 */
	public static class btnHintListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			HintSystem h = new HintSystem();
			Move newMove = h.Hint(boardLayout);
			if (newMove.getValue() != 0) {
				boardLayout[newMove.getY()][newMove.getX()].setCurrentValue(newMove.getValue());
				System.out.println("board val is " + boardLayout[newMove.getX()][newMove.getY()].getCurrentValue());
				inputX = newMove.getX();
				inputY = newMove.getY();
				source = box[inputX][inputY];
				Color hintColor = new Color(102, 255, 178);
				source.setForeground(hintColor);
				//source.setBackground(new Color(255,255,255));
				resetSourceBox();
			}
		}
	}
	
	/**
	 * Action Listener for each of the 81 squares 
	 * @author Sam
	 */
	public static class btnSquareListener implements ActionListener{
		int squareX, squareY;
		
		public btnSquareListener(int x, int y){
			squareX = x;
			squareY = y;
		}
		
		public void actionPerformed(ActionEvent e){
			//source.setForeground(defaultBGColor);
			
			System.out.println("square selected " + squareX + " " + squareY);
			System.out.println(" square type is " + boardLayout[squareY][squareX].getType() + " and has value " + boardLayout[squareY][squareX].getCurrentValue());
			source = (JButton) e.getSource();
			inputX = squareX;
			inputY = squareY;
			//source.setText(inputX + " " + inputY);
			//source.setForeground(selectedBGColor);
		}
	}
	
	/**
	 * Key Press listeners for typing
	 * @author Sam
	 */
	public static class keyPressedListener implements KeyListener{
		private int row;
		private int column;
		
		
		public keyPressedListener(int row, int col){
			this.row = row;
			this.column = col;
		}
		
		public void keyPressed(KeyEvent e) {}

		public void keyReleased(KeyEvent e) {}

		//When a key is typed
		public void keyTyped(KeyEvent e) {
			System.out.println("Key Typed " + e.getKeyChar());
			char key = e.getKeyChar();
			//if the square selected isn't a predefined cell
			if (boardLayout[row][column].getType() != Square.PREDEFINE_CELL && Character.isDigit(key)){
				int number = 0;
				boolean shift = false;
				// If shift is pressed, set shift to true
				if (e.isShiftDown()){
					shift = true;
					System.out.println("SHIFT");
				}
				// then work out what number is pressed
				if (key == '!' || key == '1'){
					number = 1;
				} else if (key == '@' || key == '2'){
					number = 2;
				} else if (key == '#' || key == '3'){
					number = 3;
				} else if (key == '$' || key == '4'){
					number = 4;
				} else if (key == '%' || key == '5'){
					number = 5;
				} else if (key == '^' || key == '6'){
					number = 6;
				} else if (key == '&' || key == '7'){
					number = 7;
				} else if (key == '*' || key == '8'){
					number = 8;
				} else if (key == '(' || key == '9'){
					number = 9;
				}
				if (shift){
					boardLayout[row][column].switchDraftValue(number);
				} else {
					boardLayout[row][column].setCurrentValue(number);
				}
				resetSourceBox();
			} else if (boardLayout[row][column].getType() != Square.PREDEFINE_CELL && (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
				
			}
			

			
		}
		
	}
		
	//NOTE: if you're looking for private variables, they're above the Action Listeners
}
