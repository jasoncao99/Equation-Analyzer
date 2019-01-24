
import javax.swing.JFrame;

public class programGUI extends JFrame{
	
	public programGUI (){
		
		this.setTitle("Equation Analyzer");
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new mainPanel());
		this.pack();
		
	}

}
