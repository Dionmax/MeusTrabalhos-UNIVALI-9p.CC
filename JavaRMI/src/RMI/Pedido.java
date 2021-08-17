package RMI;

import java.io.Serializable;

public class Pedido implements Serializable {

    private static final long serialVersionUID = 20120731125400L;
    private int quantidade;
    private String nome;

    public Pedido() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Pedido(int quantidade, String nome) {
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    @Override
    public String toString() {
        return "Nome: " + nome + " " + quantidade;
    }
}
