package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class Client {
    public static void main(String[] args) {
      

        try{
            String addrString = JOptionPane.showInputDialog("Digite o Endere�o IP do Servidor: ");
            InetAddress addr = InetAddress.getByName(addrString);
            String portString = JOptionPane.showInputDialog("Digite a Porta do Servidor: ");
            int port = Integer.parseInt(portString);
            String message = JOptionPane.showInputDialog("Digite uma mensagem para o Servidor: ");

            
            DatagramSocket socket = new DatagramSocket();
            
                        
            DatagramPacket datagram1 = new DatagramPacket(message.getBytes(),0,
              message.getBytes().length,addr,port);
            
            socket.send(datagram1);
            
            socket.close();
        }catch(Exception e){
            System.err.println("An exception ocourred: "+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
