package mcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JOptionPane;

public class Server {
    public static void main(String[] args) {
  
        try{
            String portString = JOptionPane.showInputDialog("Digite a Porta do Servidor Multicast: ");
            int port = Integer.parseInt(portString);
            String addrGroup = JOptionPane.showInputDialog("Digite o Endere�o de Multicast do grupo: ");
            InetAddress addr = InetAddress.getByName(addrGroup);

            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(addr);

            System.out.println("Escutando na "+port+" para o grupo  "+addr.getHostAddress());
            
            boolean exit = false;
            do{
                DatagramPacket datagram = new DatagramPacket(new byte[1024],1024);
                socket.receive(datagram);
                
                String message = new String(datagram.getData()).trim();
                
                if(message.equalsIgnoreCase("exit")) {
                    exit = true;
                }
                
                System.out.println("Foi recebida a mensagem: "+message+" de "+
                  datagram.getAddress()+":"+datagram.getPort());
            }while(!exit);
            
            socket.leaveGroup(addr);
            
            socket.close();
        }catch(Exception e){
            System.err.println("An exception ocourred: "+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
