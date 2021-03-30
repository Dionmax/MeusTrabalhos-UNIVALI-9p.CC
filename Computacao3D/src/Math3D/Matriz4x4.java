package Math3D;

public class Matriz4x4 {
    double amatriz[][] = new double[4][4];

    public void setIdentity() {
        amatriz[0][0] = 1;
        amatriz[0][1] = 0;
        amatriz[0][2] = 0;
        amatriz[0][3] = 0;

        amatriz[1][0] = 0;
        amatriz[1][1] = 1;
        amatriz[1][2] = 0;
        amatriz[1][3] = 0;

        amatriz[2][0] = 0;
        amatriz[2][1] = 0;
        amatriz[2][2] = 1;
        amatriz[2][3] = 0;

        amatriz[3][0] = 0;
        amatriz[3][1] = 0;
        amatriz[3][2] = 0;
        amatriz[3][3] = 1;
    }

    public void setTranslate(double a, double b, double c) {
        amatriz[0][0] = 1;
        amatriz[0][1] = 0;
        amatriz[0][2] = 0;
        amatriz[0][3] = a;

        amatriz[1][0] = 0;
        amatriz[1][1] = 1;
        amatriz[1][2] = 0;
        amatriz[1][3] = b;

        amatriz[2][0] = 0;
        amatriz[2][1] = 0;
        amatriz[2][2] = 1;
        amatriz[2][3] = c;

        amatriz[3][0] = 0;
        amatriz[3][1] = 0;
        amatriz[3][2] = 0;
        amatriz[3][3] = 1;
    }

    public void setRotateX(double ang) {
        double angrad = Math.toRadians(ang);
        double sin = Math.sin(angrad);
        double cos = Math.cos(angrad);

        amatriz[0][0] = 1;
        amatriz[0][1] = 0;
        amatriz[0][2] = 0;
        amatriz[0][3] = 0;

        amatriz[1][0] = 0;
        amatriz[1][1] = cos;
        amatriz[1][2] = -sin;
        amatriz[1][3] = 0;

        amatriz[2][0] = 0;
        amatriz[2][1] = sin;
        amatriz[2][2] = cos;
        amatriz[2][3] = 0;

        amatriz[3][0] = 0;
        amatriz[3][1] = 0;
        amatriz[3][2] = 0;
        amatriz[3][3] = 1;
    }

    public Vetor3D multiplicaVetor(Vetor3D v) {
        Vetor3D saida = new Vetor3D(1, 1, 1, 1);

        saida.x = v.x * amatriz[0][0] + v.y * amatriz[0][1] + v.z * amatriz[0][2] + v.w * amatriz[0][3];
        saida.y = v.x * amatriz[1][0] + v.y * amatriz[1][1] + v.z * amatriz[1][2] + v.w * amatriz[1][3];
        saida.z = v.x * amatriz[2][0] + v.y * amatriz[2][1] + v.z * amatriz[2][2] + v.w * amatriz[2][3];
        saida.w = v.x * amatriz[3][0] + v.y * amatriz[3][1] + v.z * amatriz[3][2] + v.w * amatriz[3][3];

        return saida;
    }

    public static Vetor3D vetorUnitario(Vetor3D vector) {
        double magniture = Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2) + Math.pow(vector.z, 2));

        return new Vetor3D(vector.x / magniture, vector.y / magniture, vector.z / magniture, vector.w);
    }

    public void rotacionar3D(Vetor3D eixoRot, double angulo) {
        double angrad = Math.toRadians(angulo);
        double cos = Math.cos(angrad);
        double sin = Math.sin(angrad);

        amatriz[0][0] = (cos + (Math.pow(eixoRot.x, 2) * (1 - cos)));
        amatriz[0][1] = ((eixoRot.x * eixoRot.y) * (1 - cos) - eixoRot.z * sin);
        amatriz[0][2] = (eixoRot.x * eixoRot.z * (1 - cos + (eixoRot.y * sin)));
        amatriz[0][3] = 0;

        amatriz[1][0] = (eixoRot.y * eixoRot.x * (1 - cos) + eixoRot.z * sin);
        amatriz[1][1] = (cos + Math.pow(eixoRot.y, 2) * (1 - cos));
        amatriz[1][2] = (eixoRot.y * eixoRot.z * (1 - cos) - eixoRot.x * sin);
        amatriz[1][3] = 0;

        amatriz[2][0] = (eixoRot.z * eixoRot.x * (1 - cos) - eixoRot.y * sin);
        amatriz[2][1] = (eixoRot.z * eixoRot.y * (1 - cos) + eixoRot.x * sin);
        amatriz[2][2] = (cos + Math.pow(eixoRot.z, 2) * (1 - cos));
        amatriz[2][3] = 0;

        amatriz[3][0] = 0;
        amatriz[3][1] = 0;
        amatriz[3][2] = 0;
        amatriz[3][3] = 1;

    }
}