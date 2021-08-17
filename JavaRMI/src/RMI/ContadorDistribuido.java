package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface que deve ser compartilhada por servidor e clientes
 *
 * http://docente.ifsc.edu.br/mello
 */
public interface ContadorDistribuido extends Remote{

    public void ComprarProduto(Pedido pedido) throws RemoteException;
    public String obtemValorTotal()  throws RemoteException;

}
