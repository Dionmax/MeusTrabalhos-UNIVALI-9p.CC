package Main;
import Math3D.Matriz4x4;
import Math3D.Triangulo3d;
import Math3D.Vetor3D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Random;

import javax.swing.JPanel;

import static Math3D.Matriz4x4.vetorUnitario;

public class MainCanvas extends JPanel implements Runnable {

    Thread runner;
    boolean ativo = true;
    int paintcounter = 0;

    BufferedImage imageBuffer;
    byte[] bufferDeVideo;

    Random rand = new Random();

    short[][] paleta;

    int framecount = 0;
    int fps = 0;

    Font f = new Font("", Font.PLAIN, 30);

    int clickX = 0;
    int clickY = 0;
    int mouseX = 0;
    int mouseY = 0;

    int Largura = 640;
    int Altura = 480;

    Triangulo3d t1;
    Triangulo3d t2;
    Triangulo3d t3;
    Triangulo3d t4;
    double angulo = 0;

    Vetor3D vetorRotacao = new Vetor3D(1, 1, 1, 1);

    public MainCanvas() {
        setSize(Largura, Altura);

        Vetor3D p1 = new Vetor3D(300, 200, 0, 1);
        Vetor3D p2 = new Vetor3D(400, 300, 0, 1);
        Vetor3D p3 = new Vetor3D(200, 300, 0, 1);
        Vetor3D p4 = new Vetor3D(300, 250, 100, 1);

        t1 = new Triangulo3d(p1, p2, p3);
        t2 = new Triangulo3d(p1, p2, p4);
        t3 = new Triangulo3d(p2, p3, p4);
        t4 = new Triangulo3d(p3, p1, p4);

        imageBuffer = new BufferedImage(Largura, Altura, BufferedImage.TYPE_4BYTE_ABGR);

        bufferDeVideo = ((DataBufferByte) imageBuffer.getRaster().getDataBuffer()).getData();

        paleta = new short[255][3];

        for (int i = 0; i < 255; i++) {
            paleta[i][0] = (short) rand.nextInt(255);
            paleta[i][1] = (short) rand.nextInt(255);
            paleta[i][2] = (short) rand.nextInt(255);
        }

        MainClass.frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    angulo += 15;
                }

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    angulo -= 15;
                }

                if (e.getKeyCode() == KeyEvent.VK_X) {
                    vetorRotacao = vetorUnitario(p1);
                }

                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    vetorRotacao = vetorUnitario(p2);
                }

                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    vetorRotacao = vetorUnitario(p3);
                }
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                clickX = e.getX();
                clickY = e.getY();

                vetorRotacao = vetorUnitario(new Vetor3D(clickX, clickY, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent arg0) {
                // TODO Auto-generated method stub
                mouseX = arg0.getX();
                mouseY = arg0.getY();
            }

            @Override
            public void mouseDragged(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(f);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Largura, 480);

        Matriz4x4 m = new Matriz4x4();
        m.rotacionar3D(vetorRotacao, angulo);

        t1.desenhaSe(this, m);
        t2.desenhaSe(this, m);
        t3.desenhaSe(this, m);
        t4.desenhaSe(this, m);

        g.drawImage(imageBuffer, 0, 0, null);
        g.setColor(Color.black);
        g.drawString("FPS " + fps, 10, 25);
    }

    public void clearVideoBuffer(Color cor) {
        for (int cont = 0; cont < (640 * 480); cont++) {
            bufferDeVideo[(cont * 4)] = (byte) cor.getAlpha();
            bufferDeVideo[(cont * 4) + 1] = (byte) cor.getBlue();
            bufferDeVideo[(cont * 4) + 2] = (byte) cor.getGreen();
            bufferDeVideo[(cont * 4) + 3] = (byte) cor.getRed();
        }
    }

    public void printPixel(int x, int y, Color color) {
        int pos = (x * 4) + ((y * 4) * Largura);

        if ((x >= 0 && x < Largura) && (y >= 0 && y < Altura)) {
            bufferDeVideo[pos] = (byte) color.getAlpha();
            bufferDeVideo[pos + 1] = (byte) color.getBlue();
            bufferDeVideo[pos + 2] = (byte) color.getGreen();
            bufferDeVideo[pos + 3] = (byte) color.getRed();
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;

        int deltaY = y2 - y1;

        int eixoMaior, eixoMenor;

        int incrementoX0 = 0, incrementoY0 = 0, incrementoX1 = 0, incrementoY1 = 0, numerador;

        if (deltaX > 0) {
            incrementoX0 = incrementoX1 = 1;
        }

        if (deltaX < 0) {
            incrementoX0 = incrementoX1 = -1;
        }

        if (deltaY > 0) {
            incrementoY0 = 1;
        }

        if (deltaY < 0) {
            incrementoY0 = -1;
        }

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            eixoMaior = Math.abs(deltaX);
            eixoMenor = Math.abs(deltaY);
        } else {
            eixoMaior = Math.abs(deltaY);
            eixoMenor = Math.abs(deltaX);
            if (deltaY > 0) {
                incrementoY1 = 1;
            }
            if (deltaY < 0) {
                incrementoY1 = -1;
            }
            incrementoX1 = 0;
        }

        numerador = eixoMaior / 2;

        for (int i = 0; i <= eixoMaior; i++) {
            printPixel(x1, y1, Color.BLACK);
            numerador += eixoMenor;
            if (numerador > eixoMaior) {
                numerador -= eixoMaior;
                x1 += incrementoX0;
                y1 += incrementoY0;
            } else {
                x1 += incrementoX1;
                y1 += incrementoY1;
            }
        }
    }

    public void start() {
        runner = new Thread(this);
        runner.start();
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        long segundo = time / 1000;

        while (ativo) {
            paintImmediately(0, 0, Largura, Altura);

            paintcounter += 100;

            clearVideoBuffer(Color.WHITE);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newtime = System.currentTimeMillis();
            long novoSegundo = newtime / 1000;
            framecount++;

            if (novoSegundo != segundo) {
                fps = framecount;
                framecount = 0;
                segundo = novoSegundo;
            }
        }
    }
}
