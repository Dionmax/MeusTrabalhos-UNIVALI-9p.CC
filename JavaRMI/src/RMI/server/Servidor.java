package RMI.server;

import RMI.ContadorDistribuido;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por criar uma instância do objeto Contador e registrá-la
 * em um serviço de registro de objetos distribuídos
 *
 * http://docente.ifsc.edu.br/mello
 */
public class Servidor {

    // Constantes que indicam onde está sendo executado o serviço de registro,
    // qual porta e qual o nome do objeto distribuído
    private static final String IPSERVIDOR = "127.0.0.1";
    private static final int PORTA = 1234;
    private static final String NOMEOBJDIST = "MeuContador";

    public static void main(String[] args){
        try {
            // Criando
            Contador c = new Contador();

            // Definindo o hostname do servidor
            System.setProperty("java.rmi.server.hostname", IPSERVIDOR);

            ContadorDistribuido stub = (ContadorDistribuido) UnicastRemoteObject.exportObject(c, 0);

            // Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry(PORTA);

            // Registrando objeto distribuído
            registro.bind(NOMEOBJDIST, stub);

            System.out.println("Servidor pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");


        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
