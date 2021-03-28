package Math2D;

public class Matriz3x3 {
    double[][] amatriz = new double[3][3];

    public void setIdentity() {
        amatriz[0][0] = 1;
        amatriz[0][1] = 0;
        amatriz[0][2] = 0;

        amatriz[1][0] = 0;
        amatriz[1][1] = 1;
        amatriz[1][2] = 0;

        amatriz[2][0] = 0;
        amatriz[2][1] = 0;
        amatriz[2][2] = 1;
    }

    public void setTranslate(double a, double b) {
        amatriz[0][0] = 1;
        amatriz[0][1] = 0;
        amatriz[0][2] = a;

        amatriz[1][0] = 0;
        amatriz[1][1] = 1;
        amatriz[1][2] = b;

        amatriz[2][0] = 0;
        amatriz[2][1] = 0;
        amatriz[2][2] = 1;
    }

    public Vetor2d multiplicaVetor(Vetor2d v) {
        Vetor2d saida = new Vetor2d(1, 1, 1);

        saida.x = v.x * amatriz[0][0] + v.y * amatriz[0][1] + v.w * amatriz[0][2];
        saida.y = v.x * amatriz[1][0] + v.y * amatriz[1][1] + v.w * amatriz[1][2];
        saida.w = v.x * amatriz[2][0] + v.y * amatriz[2][1] + v.w * amatriz[2][2];

        return saida;
    }

    public Vetor2d rotacionar(Vetor2d v, int ang) {
        Vetor2d saida = new Vetor2d(v.x, v.y, v.w);

        double angrad = Math.toRadians(ang);

        saida.x = (Math.cos(angrad)) - (Math.sin(angrad)) + (v.x * (1 - Math.cos(angrad)) + v.y * Math.sin(angrad));
        saida.y = (Math.sin(angrad)) + (Math.cos(angrad)) + (v.y * (1 - Math.cos(angrad)) - v.x * Math.sin(angrad));

        return saida;
    }
}
