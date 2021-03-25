package mcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JOptionPane;

public class Client {
    public static void main(String[] args) {
        
        try{
            String addrString = JOptionPane.showInputDialog("Digite o Endereço do Grupo de Multicast: ");
            InetAddress addr = InetAddress.getByName(addrString);
            String portString = JOptionPane.showInputDialog("Digite a Porta do ServidorMulticast: ");
            int port = Integer.parseInt(portString);
            String message = JOptionPane.showInputDialog("Digite uma mensagem para o Grupo: ");
            
            
            MulticastSocket socket = new MulticastSocket();

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
