package multiclientserver;

import javax.swing.*;


public class ClientServer {

	
	public static void main(String [] args){
		
		Object[] selectioValues = { "Server","Client"};
		String initialSection = "Server";
		
		Object selection = JOptionPane.showInputDialog(null, "Login as : ", "MyChat", JOptionPane.QUESTION_MESSAGE, null, selectioValues, initialSection);
		if(selection.equals("Server")){
                   String[] arguments = new String[] {};
			new MultiThreadChatServerSync().main(arguments);
		}else if(selection.equals("Client")){
			String IPServer = JOptionPane.showInputDialog("Enter the Server ip adress");
                        String[] arguments = new String[] {IPServer};
			new ChatClient().main(arguments);
		}
		
	}

}