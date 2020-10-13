package ShacharMales;

import controller.ManegmentSystemController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.AdminSystem;
import view.GUI;
import view.manageable;

public class Main extends Application{
	

	public static void main(String[] args) throws Exception {
		
		launch(args);

	}
	
	
	@Override
	public void start(Stage arg0) throws Exception {
		AdminSystem adminSystem = new AdminSystem();
		manageable systemInterface = new GUI(arg0);
		ManegmentSystemController controller = new ManegmentSystemController(adminSystem, systemInterface);
		
		
	}
	


}
