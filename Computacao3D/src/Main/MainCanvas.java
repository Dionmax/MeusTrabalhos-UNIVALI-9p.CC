package Main;
import Math2D.*;

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
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainCanvas extends JPanel implements Runnable {

    Thread runner;
    boolean ativo = true;
    int paintcounter = 0;

    BufferedImage imageBuffer;
    byte[] bufferDeVideo;

    Random rand = new Random();

    byte[] memoriaPlacaVideo;
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
    int pixelSize = Largura * Altura;

    Point[] rect = new Point[4];
    Vetor2d[] rect2 = new Vetor2d[4];

    public MainCanvas() {
        setSize(Largura, Altura);

        rect[0] = new Point(10, 10);
        rect[1] = new Point(110, 10);
        rect[2] = new Point(110, 110);
        rect[3] = new Point(10, 110);

        rect2[0] = new Vetor2d(100, 100, 1);
        rect2[1] = new Vetor2d(200, 100, 1);
        rect2[2] = new Vetor2d(200, 200, 1);
        rect2[3] = new Vetor2d(100, 200, 1);

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
                Matriz3x3 mat = new Matriz3x3();
                mat.setIdentity();

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    mat.setTranslate(10, 0);
                    for (int i = 0; i < rect2.length; i++) {
                        rect2[i] = mat.multiplicaVetor(rect2[i]);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mat.setTranslate(-10, 0);
                    for (int i = 0; i < rect2.length; i++) {
                        rect2[i] = mat.multiplicaVetor(rect2[i]);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    mat.setTranslate(0, -10);
                    for (int i = 0; i < rect2.length; i++) {
                        rect2[i] = mat.multiplicaVetor(rect2[i]);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    mat.setTranslate(0, 10);
                    for (int i = 0; i < rect2.length; i++) {
                        rect2[i] = mat.multiplicaVetor(rect2[i]);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_R) {

                    for (int i = 0; i < rect2.length; i++) {
                        mat.setTranslate(-clickX, -clickY);
                        rect2[i] = mat.multiplicaVetor(rect2[i]);

                        rect2[i] = mat.rotacionar(rect2[i], 300);

                        mat.setTranslate(clickX, clickY);
                        rect2[i] = mat.multiplicaVetor(rect2[i]);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_E) {

                    for (int i = 0; i < rect2.length; i++) {
                        mat.setTranslate(-clickX, -clickY);
                        rect2[i] = mat.multiplicaVetor(rect2[i]);

                        rect2[i] = mat.rotacionar(rect2[i], -300);

                        mat.setTranslate(clickX, clickY);
                        rect2[i] = mat.multiplicaVetor(rect2[i]);
                    }
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

        //variaveis auxiliares para alteração dos pixels

        int deltaX = x2 - x1;

        int deltaY = y2 - y1;

        //armazenamento dos eixos

        int eixoMaior, eixoMenor;

        int incrementoX0 = 0, incrementoY0 = 0, incrementoX1 = 0, incrementoY1 = 0, numerador;

        //testes para analisar se os eixos estão crescendo ou decrescendo de acordo com o delta

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

            drawLine((int) rect2[0].x, (int) rect2[0].y, (int) rect2[1].x, (int) rect2[1].y);
            drawLine((int) rect2[1].x, (int) rect2[1].y, (int) rect2[2].x, (int) rect2[2].y);
            drawLine((int) rect2[2].x, (int) rect2[2].y, (int) rect2[3].x, (int) rect2[3].y);
            drawLine((int) rect2[3].x, (int) rect2[3].y, (int) rect2[0].x, (int) rect2[0].y);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
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
