package tcpThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {
    public static void main(String[] args) {
     

        try{
            
            String addrString = JOptionPane.showInputDialog("Digite o Endereço IP do Servidor: ");
            InetAddress addr = InetAddress.getByName(addrString);
            String portString = JOptionPane.showInputDialog("Digite a Porta do Servidor: ");
            int port = Integer.parseInt(portString);
                                
            Socket socket = new Socket(addr,port);
            String message = JOptionPane.showInputDialog("Digite uma mensagem para o Servidor: ");
            DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            dataOutput.writeUTF(message);
            String data = dataInput.readUTF();
            
            if(data.equals(message)){
                System.out.println("Echo: "+data+" - bem sucedido.");
            }else{
                System.out.println("Enviado: "+message);
                System.out.println("Recebido: "+data);
            }
            
            String message2 = JOptionPane.showInputDialog("Digite outra mensagem para o Servidor: ");
            dataOutput.writeUTF(message2);
            String data2 = dataInput.readUTF();
                     
            

            if(data2.equals(message2)){
                System.out.println("Echo: "+data+" - bem sucedido.");
            }else{
                System.out.println("Enviado: "+message);
                System.out.println("Recebido: "+data);
            }
            socket.close();
        }catch(Exception e){
            System.err.println("An exception ocourred: "+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
