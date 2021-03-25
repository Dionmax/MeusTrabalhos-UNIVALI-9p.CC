package udp2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class Client {
    public static void main(String[] args) {

        try{
            String addrString = JOptionPane.showInputDialog("Digite o Endereço IP do Servidor: ");
            InetAddress addr = InetAddress.getByName(addrString);
            String portString = JOptionPane.showInputDialog("Digite a Porta do Servidor: ");
            int port2 = Integer.parseInt(portString);
            String message = JOptionPane.showInputDialog("Digite uma mensagem para o Servidor: ");
            
            DatagramSocket socket = new DatagramSocket();

            DatagramPacket datagram1 = new DatagramPacket(message.getBytes(),0,
              message.getBytes().length,addr,port2);
            
            socket.send(datagram1);
            
            DatagramPacket datagram2 = new DatagramPacket(new byte[1024],1024);
            socket.receive(datagram2);
            
            String data = new String(datagram2.getData()).trim();
            
            socket.close();

            if(data.equals(message)){
                System.out.println("Echo: "+data+" - bem sucedido");
            }else{
                System.out.println("Enviado: "+message);
                System.out.println("Recebido: "+data);
            }
        }catch(Exception e){
            System.err.println("An exception ocourred: "+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
