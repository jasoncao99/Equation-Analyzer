import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class mainPanel extends JPanel implements ActionListener{

	JLabel titleLabel = new JLabel();
	Font titleFont = new Font ("Arial",Font.BOLD,60);
	Font solutionFont = new Font ("Arial",Font.BOLD,16);

	JTextPane example = new JTextPane();
	JTextPane textbox = new JTextPane();
	JButton submit = new JButton("Submit");

	//input
	String stringEquation;
	char[] charEquation;

	//processing
	ArrayList<equation> equationList = new ArrayList<equation>();
	ArrayList<Double> xIntercept = new ArrayList<Double>();
	double yIntercept;

	String firstDerivative = new String();
	String secondDerivative = new String();

	ArrayList<Points> increasing = new ArrayList<Points>();
	ArrayList<Points> decreasing = new ArrayList<Points>();
	ArrayList<Points> concaveup = new ArrayList<Points>();
	ArrayList<Points> concavedown = new ArrayList<Points>();

	//output
	JTextPane[] output = new JTextPane[4];

	public mainPanel(){
		super();

		this.setPreferredSize(new Dimension(600,510));
		this.setVisible(true);
		this.setFocusable(true);
		this.setLayout(null);
		this.requestFocus();

		setupInput();
		setupSolution();

	}

	//method to set up objects required for input
	public void setupInput(){

		titleLabel.setVisible(true);
		titleLabel.setBounds(35,20,550,50);
		titleLabel.setText("Equation Analyzer");
		titleLabel.setFont(titleFont);
		this.add(titleLabel);

		example.setText("Please enter a polynomial function. For example, 3x^3");
		example.setVisible(true);
		example.setOpaque(false);
		example.setEditable(false);
		example.setBounds(80,90,500,20);
		example.setFont(solutionFont);
		this.add(example);


		textbox.setVisible(true);
		textbox.setBounds(100,120,300,50);
		textbox.setFont(solutionFont);
		this.add(textbox);

		submit.setVisible(true);
		submit.setBounds(450,120,75,50);
		submit.addActionListener(this);
		this.add(submit);

	}

	public void actionPerformed(ActionEvent e) {
		//if the submit button is pressed
		if (e.getSource() == submit){
			//isn't empty
			if (textbox.getText().length() > 0){
				reset();
				stringEquation = (textbox.getText());
				stringEquation = stringEquation.replaceAll("\\s+","");
				charEquation = stringEquation.toCharArray();
				equationList.add(new equation(stringEquation,charEquation));
				solve();
			}
		}
	}

	//clears arraylists for next equation
	public void reset(){
		xIntercept.clear();
		decreasing.clear();
		increasing.clear();
		concaveup.clear();
		concavedown.clear();
	}

	//setup the solution boxes
	public void setupSolution(){
		for (int x = 0; x < output.length; x++){
			output[x] = new JTextPane();
			output[x].setVisible(true);
			output[x].setEditable(false);
			output[x].setOpaque(false);
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			output[x].setBorder(BorderFactory.createCompoundBorder(border, 
					BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			output[x].setFont(solutionFont);
			this.add(output[x]);
			if (x <=1){
				output[x].setBounds(40 + (255*x),180,250,100);
			}else{
				output[x].setBounds(40 ,290 + (110 * (x-2)),505,100);
			}
		}
		//intercepts
		output[0].setText("	Intercepts" + "\n"
				+ "x intercept(s): " + "\n"
				+ "y intercept: "
				);

		//derivative
		output[1].setText("	Derivatives\n"
				+ "f '(x) = \n"
				+ "f ''(x) = ");

		//intervals of increase/decrease
		output[2].setText("\t                  \t Intervals\n"
				+ "increasing = \n"
				+ "decreasing = ");

		//concavity
		output[3].setText("\t                  \t Concavity\n"
				+ "concave up = \n"
				+ "concave down = ");

	}

	//display answers
	public void displaySolution(){

		//intercepts
		String xString = "none";
		if(xIntercept.size() > 0){
			String test = xIntercept.get(0).toString();
			for (int i = 1; i < xIntercept.size(); i++){
				test += "," + xIntercept.get(i).toString();
			}
			xString = test;
		}
		output[0].setText("	Intercepts" + "\n"
				+ "x intercept(s): " + xString + "\n"
				+ "y intercept: " + yIntercept
				);

		//derivatives
		output[1].setText("	Derivatives\n"
				+ "f '(x) = " + firstDerivative + "\n"
				+ "f ''(x) = " + secondDerivative);

		//intervals of increase/decrease
		String increase = "none";
		if(increasing.size() > 0){
			increase = "(" + increasing.get(0).getStart() + "," + increasing.get(0).getEnd() +")";
			for (int x = 1; x < increasing.size();x++){
				increase += "U(" + increasing.get(x).getStart() + "," + increasing.get(x).getEnd() +")";
			}
		}
		String decrease = "none";
		if(decreasing.size() > 0){
			decrease = "(" + decreasing.get(0).getStart() + "," + decreasing.get(0).getEnd() +")";
			for (int i = 1; i < decreasing.size();i++){
				decrease += "U(" + decreasing.get(i).getStart() + "," + decreasing.get(i).getEnd() +")";
			}
		}
		output[2].setText("\t                  \t Intervals\n"
				+ "increasing = " + increase + "\n"
				+ "decreasing = " + decrease);

		//concavity
		String up = "none";
		if(concaveup.size() > 0){
			up = "(" + concaveup.get(0).getStart() + "," + concaveup.get(0).getEnd() +")";
			for (int x = 1; x < concaveup.size();x++){
				up += "U(" + concaveup.get(x).getStart() + "," + concaveup.get(x).getEnd() +")";
			}
		}
		String down = "none";
		if(concavedown.size() > 0){
			down = "(" + concavedown.get(0).getStart() + "," + concavedown.get(0).getEnd() +")";
			for (int i = 1; i < concavedown.size();i++){
				down += "U(" + concavedown.get(i).getStart() + "," + concavedown.get(i).getEnd() +")";
			}
		}
		output[3].setText("\t                  \t Concavity\n"
				+ "concave up =" + up + "\n"
				+ "concave down =  " + down);

		//prints out the information of the equation
		System.out.println("-------------");
		System.out.println("x int: " + xString);
		System.out.println("y int: " + yIntercept);
		System.out.println("f '(x)="+firstDerivative);
		System.out.println("f ''(x)="+secondDerivative);
		System.out.println("increasing: "+increase);
		System.out.println("decreasing: "+decrease);
		System.out.println("concave up: "+up);
		System.out.println("concave down: "+down);
		System.out.println("-------------");

	}

	//call methods to fill in text boxes
	public void solve(){
		intercepts(stringEquation);
		firstDerivative = derive(stringEquation);
		secondDerivative = derive(firstDerivative);
		intervals(firstDerivative);
		concavity(secondDerivative);
		displaySolution();
	}

	//method that replaces variables with numbers and evaluates it
	//requires equation and number to be swapped in
	public double replace(String equation, double index){
		String testValue;
		String temp = equation.toLowerCase();
		index = Math.round(index * 100.0) / 100.0;
		if (temp.length()>1){
			for (int x=1; x<temp.length();x++){
				//String newName = temp.substring(0,x-1)+'x'+temp.substring(x+1);
				if(temp.charAt(x) == 'x'){
					if (temp.charAt(x-1) >= '0' && temp.charAt(x-1) <= '9'){
						testValue = "*(" + Double.toString(index) + ")";
					}
					else{
						testValue = "(" + Double.toString(index) + ")";
					}
					temp=temp.substring(0,x)+testValue+temp.substring(x+1);
				}
			}	
		}else {
			testValue = "(" + Double.toString(index) + ")";
			temp = temp.toLowerCase().replaceAll("x",testValue);
		}
		double value = evaluate(temp);
		return value;
	}

	//method to find concavity of the equation
	public void concavity(String equation){
		//arraylist to store all points (x,y)
		ArrayList<Points> tempStorage = new ArrayList<Points>();
		for(double index = -99; index <= 99; index+=0.01){ 
			//round index
			index = Math.round(index * 100.0) / 100.0;
			//evaluate
			double value = replace(equation,index);
			//round answer
			value = Math.round(value * 1000.0) / 1000.0;
			//add point to arraylist
			tempStorage.add(new Points(index,value));
		}
		//variables to track progress
		boolean started = false;
		double startX = 0;
		double startY = 0;
		for(int i = 0; i < tempStorage.size(); i++){
			double tempX = tempStorage.get(i).getStart();
			double tempY = tempStorage.get(i).getEnd();
			if (started == true){
				if (startY > 0){
					if(tempY <= 0 || i == tempStorage.size()-1){
						started = false;
						concaveup.add(new Points(startX,tempX));
					}
				}else if (startY < 0 ){
					if(tempY >= 0 || i == tempStorage.size()-1){
						started = false;
						concavedown.add(new Points(startX,tempX));
					}
				}else if (startY == 0){
					started = false;
				}
			}else {
				startX = tempX; //starting pos
				startY = tempY; //+ or -
				started = true;
				if (i > 1){
					if (tempStorage.get(i-1).getStart() == 0){
						startX = 0;
					}
				}
			}
		}
	}

	//method to find intervals of increase/decrease
	public void intervals(String equation){
		//store all points
		ArrayList<Points> storage = new ArrayList<Points>();
		//check all points -99 to 99
		for(double index = -99; index <= 99; index+=0.01){ 
			index = Math.round(index * 100.0) / 100.0;
			double value = replace(equation,index);
			value = Math.round(value * 1000.0) / 1000.0;
			storage.add(new Points(index,value));
		}
		//keep track of where it started
		boolean started = false;
		double startX = 0;
		double startY = 0;
		//compare every point with the start point
		for(int i = 0; i < storage.size(); i++){
			double tempX = storage.get(i).getStart();
			double tempY = storage.get(i).getEnd();
			if (started == true){
				if (startY > 0){
					if(tempY <= 0 || i == storage.size()-1){
						started = false;
						increasing.add(new Points(startX,tempX));
						//System.out.println("\n" + tempX + ","+tempY);
						//System.out.println(storage.get(i+1).getX() + ","+storage.get(i+1).getY() + "\n");
					}
				}else if (startY < 0 ){
					if(tempY >= 0 || i == storage.size()-1){
						started = false;
						decreasing.add(new Points(startX,tempX));
					}
				}else if (startY == 0){
					started = false;
				}
			}else {
				startX = tempX; //starting pos
				startY = tempY; //+ or -
				started = true;
				if (i > 1){
					if (storage.get(i-1).getStart() == 0){
						startX = 0;
					}
				}
			}
		}
	}

	public void intercepts(String equation){
		//smallest difference I can do before an error occurs
		for(double index = -99; index <= 99; index+=0.01){ 
			index = Math.round(index * 100.0) / 100.0;
			double value = replace(equation,index);
			value = Math.round(value * 100000000.0) /100000000.0;
			if (value == 0){
				xIntercept.add(index);
			}
			if (index == 0){
				yIntercept = value;
			}	
		}
	}

	//method to solve equation
	//follows order of operation
	//must enter numerical values
	//no variables!
	public static double evaluate(final String equation) {
		//create new object to return
		//allows for error checking
		return new Object() {
			int position = -1;
			int character;
			void nextChar() {
				if (++position < equation.length()){
					character = equation.charAt(position);
				}else{
					character = -1;
				}
			}
			boolean shift(int shiftChar) {
				while (character == ' '){//if there are spaces
					nextChar();
				}
				if (character == shiftChar){
					nextChar();
					return true;
				}
				return false;
			}
			// used to return x out of the sub method
			double parse() {
				nextChar();
				double x = parseExpression();
				if (position < equation.length()){
					throw new RuntimeException("Parse error: " + (char)character);
				}
				return x;
			}
			double parseTerm() {
				double x = parseFactor();
				while(true) {
					if (shift('*')) {
						x *= parseFactor(); // multiply
					} else if (shift('/')) {
						x /= parseFactor();	// divide
					} else if (shift('(')) { // brackets
						x *= parseExpression();
						shift(')');
					} else {
						return x;
					}
				}
			}			
			double parseExpression() {
				double x = parseTerm();
				while(true) {
					if      (shift('+')){
						x += parseTerm(); // addition
					}
					else if (shift('-')){
						x -= parseTerm(); // subtraction
					}
					else{
						return x;
					}
				}
			}
			double parseFactor() {
				if (shift('+')){
					return parseFactor(); //plus
				}
				if (shift('-')){
					return -parseFactor(); //minus
				}
				double x = 0;
				int startPos = this.position;

				if (shift('(')) { // brackets
					x = parseExpression();
					shift(')');
				} else if ((character >= '0' && character <= '9') || character == '.') { // numbers
					while ((character >= '0' && character <= '9') || character == '.') {
						nextChar();
					}
					x = Double.parseDouble(equation.substring(startPos, this.position));
				} else if (character >= 'a' && character <= 'z') { // functions (square root, sin, cos, tan)
					while (character >= 'a' && character <= 'z'){
						nextChar();
					}
					String function = equation.substring(startPos, this.position);
					x = parseFactor();

					//functions needed for non polynomial equations
					if (function.equals("sqrt")){
						x = Math.sqrt(x);
					}
					else if (function.equals("sin")){
						x = Math.sin(Math.toRadians(x));
					}
					else if (function.equals("cos")){
						x = Math.cos(Math.toRadians(x));
					}
					else if (function.equals("tan")){
						x = Math.tan(Math.toRadians(x));
					}			
					else throw new RuntimeException("invalid function: " + function);
				} else {
					System.out.println("unknown character: " + (char)character);
				}
				if (shift('^')){
					x = Math.pow(x, parseFactor()); // exponent
				}
				return x;
			}
		}
		.parse(); // returns x to out of my sub methods
	}

	//gets derivative of the equation you input
	//requires string
	//returns string
	public String derive(String equationToDerive){ 

		//store the equation
		String temp = equationToDerive.toLowerCase();
		//count each 'x'
		int counter = 0;
		//arraylist to remember the locations of each 'x'
		ArrayList<Integer> locations = new ArrayList<Integer>();
		//count the 'x'
		for (int i=0;i<temp.length();i++){
			if (temp.charAt(i)=='x'){
				counter++;
				locations.add(i);
			}
		}
		//loop based on number of 'x'
		for (int numOfX=0; numOfX<counter;numOfX++){
			//spot of 'x'
			int spot = locations.get(0);
			//exponent error checking
			char exponent = ']';
			if (spot <= temp.length() - 3){
				exponent = temp.charAt(spot+2);
			}
			//coefficient error checking
			int coeff = -2;
			String doubleCoeff = "";
			//if coefficient is atleast double digit
			if (spot >= 2){
				int test = 1;
				while (test <= spot && temp.charAt(spot - test) <= '9' && temp.charAt(spot - test) >= '0'){
					doubleCoeff = Character.toString(temp.charAt(spot-test)) + doubleCoeff;
					test++;
				}
			}
			else if (spot==1){
				coeff = temp.charAt(spot-1); //single digit coefficient
			}
			else{
				coeff = temp.charAt(spot);	//no coefficient, coeff will = 'x', won't pass through coeff derivative formula
			}	

			//DERIVING BEGINS HERE

			//has coefficient and exponent equal to or greater than 3
			if(exponent >= '3' && exponent <= '9'){
				if (doubleCoeff.length() > 0 || coeff >= '0' && coeff <= '9' ){
					//variable used if it is a double digit coeff
					int storage = 0;
					coeff = (coeff-48) * (exponent - 48);
					//double digit coeff
					if (doubleCoeff.length() > 0){
						storage = Integer.parseInt(doubleCoeff) * (exponent-48);
					}
					//reduce exponent
					exponent--;
					//double digit coefficient output equation
					if (doubleCoeff.length() > 0){
						temp = storage + temp.substring(spot,spot+2) + exponent + temp.substring(spot+3);
					}
					//single digit coefficient output equation
					else {
						temp = coeff + temp.substring(spot,spot+2) + exponent + temp.substring(spot+3);
						if (counter == 1){
							temp = coeff + temp.substring(spot,spot+2) + exponent + temp.substring(spot+3);
						}
					}		
				}	
				//coefficient is not a number but has an exponent greater to or equal to 3
				else{
					//bring exponent to front and reduce exponent
					char store = exponent;
					exponent--;
					temp = temp.substring(0,spot) + store +temp.substring(spot,spot+2) + exponent + temp.substring(spot+3);	
				}
			}
			//exponent is equal to '2'
			else if (exponent == '2'){
				int tempCoeff = coeff;
				coeff = (coeff-48) * (exponent - 48);
				int storage = 0;
				//double digit
				if (tempCoeff == '9'){
					storage = 18;
					temp = storage + temp.substring(spot,spot+1);
				}
				else if (doubleCoeff.length() > 0){
					storage = Integer.parseInt(doubleCoeff) * (exponent-48);
					temp = storage + temp.substring(spot,spot+1);
				}
				else{  //single digit or no coeff
					if (tempCoeff >= '0' && tempCoeff <= '9'){
						//no more x's, gets rid of integers
						if(counter == 1){	
							temp = temp.substring(0,spot-1) + coeff + temp.substring(spot,spot+1);
						}
						else{
							temp = temp.substring(0,spot-1) + coeff + temp.substring(spot,spot+1) + temp.substring(spot+3);
						}
					}else {
						char store = temp.charAt(spot);
						if (spot == 0){ //no coeff
							temp = Character.toString(exponent) + store + temp.substring(spot+3);
						}
						else{
							temp = temp.substring(0,spot-1) + Character.toString(exponent) + store + temp.substring(spot+3);
						}
					}
				}
			}else if (exponent == '1' || exponent <='0' || exponent >='9' ){ // 1 or not a number
				//gets rid of the x
				temp = temp.substring(0,spot) + temp.substring(spot+1);
			} 
		}
		if (equationToDerive.length() == counter*3){
			System.out.println("worked");
			temp = temp.substring(0,counter*3);
		}
		return temp;
	}
}
