package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {
    public static void main(String[] args) {
        
        
         try{
            String portString = JOptionPane.showInputDialog("Digite a Porta do Servidor: ");
            int port = Integer.parseInt(portString);

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Servidor TCP escutando na porta "+serverSocket.getLocalPort());
            
            boolean exit = false;
            do{
                Socket socket = serverSocket.accept();
  
                System.out.println("Recebendo mensagem de "+
                  socket.getInetAddress().getHostName()+":"+socket.getPort());
                
                DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                String data = dataInput.readUTF();
                if(data.equalsIgnoreCase("exit")){
                    exit = true;
                }
                
                System.out.println("Mensagem recebida do cliente: "+data);
                
                DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
                System.out.println("Mensagem a ser enviada para o cliente (echo): "+data);
                dataOutput.writeUTF(data);
                
                socket.close();
            }while(!exit);
            
            serverSocket.close();
        }catch(Exception e){
            System.err.println("And exception ocourred: "+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
