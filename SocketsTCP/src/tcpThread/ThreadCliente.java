/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ThreadCliente extends Thread{
    
    private Socket socket_clie;

  public ThreadCliente(Socket cliente) {

    this.socket_clie = cliente; 

  }
  public void run() {

    try {
        System.out.println("Recebendo mensagem de "+ socket_clie.getInetAddress().getHostName()+":"+socket_clie.getPort());
        DataInputStream dataInput = new DataInputStream(socket_clie.getInputStream());
        String data = dataInput.readUTF();
        System.out.println("Mensagem recebida do cliente: "+data);
        DataOutputStream dataOutput = new DataOutputStream(socket_clie.getOutputStream());
        System.out.println("Mensagem a ser enviada para o cliente (echo): "+data);
        dataOutput.writeUTF(data);
       }
    catch(Exception e) {
    System.out.println("Excecao ocorrida na thread: " + e.getMessage());            
       try {
         socket_clie.close();   
       }
       catch(Exception ec) {}     

    }
  }
    
}
