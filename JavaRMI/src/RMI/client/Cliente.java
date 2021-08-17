package RMI.client;

import RMI.ContadorDistribuido;
import RMI.Pedido;
import RMI.server.Contador;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.objects.NativeJSON;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente de uma aplicação Java RMI
 * <p>
 * http://docente.ifsc.edu.br/mello
 */
public class Cliente {

    // Constantes que indicam onde está sendo executado o serviço de registro,
    // qual porta e qual o nome do objeto distribuído
    private static final String IPSERVIDOR = "127.0.0.1";
    private static final int PORTA = 1234;
    private static final String NOMEOBJDIST = "MeuContador";

    public static void main(String args[]) {

        InputStreamReader inputStr = new InputStreamReader(System.in);

        BufferedReader buf = new BufferedReader(inputStr);

        int quantidade = -1;
        String produto = "-1";

        try {
            // Obtendo referência do serviço de registro
            Registry registro = LocateRegistry.getRegistry(IPSERVIDOR, PORTA);

            // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
            ContadorDistribuido stub = (ContadorDistribuido) registro.lookup(NOMEOBJDIST);

            // Invocando métodos do objeto distribuído

            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            ObjectOutputStream out = null;

            do {
                System.out.println("Solicitando pedido");

                System.out.println("Insira nome pedido: ");
                produto = buf.readLine();

                System.out.println("Insira quantidade: ");
                quantidade = Integer.parseInt(buf.readLine());

                if (quantidade != -1 || !produto.equals("-1"))
                    stub.ComprarProduto(new Pedido(quantidade, produto));

            } while (quantidade != -1 || !produto.equals("-1"));

            System.out.println("Pedidos: " + stub.obtemValorTotal());

            StringBuilder retorno = new StringBuilder();

            for (Pedido pedido : Contador.pedidos) {
                retorno.append(pedido.toString()).append("\n");
            }

            System.out.println(retorno);

            System.out.println("Fim da execução do cliente!");

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
