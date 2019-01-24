
public class equation {

	private String equation;
	private char[] characters;
	
	public equation(String equation, char[] characters){
		setEquation(equation);
		setCharacters(characters);
	}
	
	public String getEquation() {
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
	}
	public char[] getCharacters() {
		return characters;
	}
	public int getCharacter(int a){
		return (int)characters[a];
	}
	public void setCharacters(char[] characters) {
		this.characters = characters;
	}
	
}
