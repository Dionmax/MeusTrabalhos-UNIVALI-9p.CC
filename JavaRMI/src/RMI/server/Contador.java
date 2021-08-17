package RMI.server;

import RMI.ContadorDistribuido;
import RMI.Pedido;
import RMI.client.Cliente;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa a interface do objeto distribuído
 * <p>
 * http://docente.ifsc.edu.br/mello
 */
public class Contador implements ContadorDistribuido {

    public static List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void ComprarProduto(Pedido pedido) throws RemoteException {
        pedidos.add(pedido);
    }

    @Override
    public String obtemValorTotal() throws RemoteException {

        StringBuilder retorno = new StringBuilder();

        for (Pedido pedido : Contador.pedidos) {
            retorno.append(pedido.toString()).append("\n");
        }

        return retorno.toString();
    }
}
