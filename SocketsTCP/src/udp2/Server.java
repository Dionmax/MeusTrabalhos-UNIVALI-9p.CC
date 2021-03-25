package udp2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;

public class Server {
    public static void main(String[] args) {
        
        try{
            String portString = JOptionPane.showInputDialog("Digite a Porta do Servidor: ");
            int port = Integer.parseInt(portString);

            DatagramSocket socket = new DatagramSocket(port);

            System.out.println("Servidor UDP2 escutando na porta "+port);
            
            boolean exit = false;
            do{
                DatagramPacket datagram1 = new DatagramPacket(new byte[1024],1024);
                socket.receive(datagram1);
                
                String data = new String(datagram1.getData()).trim();
                
                if(data.equalsIgnoreCase("exit")) {
                    exit = true;
                }
                
                System.out.println("Ecoando: "+data);
                
                DatagramPacket datagram2 = new DatagramPacket(data.getBytes(),
                  data.getBytes().length,datagram1.getAddress(),datagram1.getPort());
                
                socket.send(datagram2);
            }while(!exit);
            
            socket.close();
        }catch(Exception e){
            System.err.println("An exception ocourred: "+e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
