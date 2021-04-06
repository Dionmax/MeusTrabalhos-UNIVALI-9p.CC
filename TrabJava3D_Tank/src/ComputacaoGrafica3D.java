import java.applet.*;
import java.awt.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.behaviors.keyboard.*;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.*;

class Tank3D extends Applet implements KeyListener {

    private SimpleUniverse universe = null;
    private Canvas3D canvas = null;
    private TransformGroup viewtrans = null;

    private TransformGroup tg = null;
    private Transform3D t3d = null;
    private Transform3D t3dstep = new Transform3D();
    private Matrix4d matrix = new Matrix4d();

    private MovingTank tank = null;

    public Tank3D() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        canvas = new Canvas3D(config);
        add("Center", canvas);
        universe = new SimpleUniverse(canvas);

        BranchGroup scene = createSceneGraph();
        universe.getViewingPlatform().setNominalViewingTransform();

        universe.getViewer().getView().setBackClipDistance(100.0);

        canvas.addKeyListener(this);

        universe.addBranchGraph(scene);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);

        viewtrans = universe.getViewingPlatform().getViewPlatformTransform();

        KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(viewtrans);
        keyNavBeh.setSchedulingBounds(bounds);
        PlatformGeometry platformGeom = new PlatformGeometry();
        platformGeom.addChild(keyNavBeh);
        universe.getViewingPlatform().setPlatformGeometry(platformGeom);

        objRoot.addChild(createTank());

        Background background = new Background();
        background.setColor(0.9f, 0.9f, 0.9f);
        background.setApplicationBounds(bounds);
        objRoot.addChild(background);

        return objRoot;
    }

    private BranchGroup createTank() {

        BranchGroup objRoot = new BranchGroup();
        tg = new TransformGroup();
        t3d = new Transform3D();

        t3d.setTranslation(new Vector3d(0.0, -0.8, -10.0));
        t3d.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(90)));
        t3d.setScale(0.01);

        tg.setTransform(t3d);

        tank = new MovingTank("assets/TankObj/tank.obj");
        tg.addChild(tank.tg);

        tg.addChild(tank);

        objRoot.addChild(tg);
        objRoot.addChild(createLight());

        objRoot.compile();

        return objRoot;

    }

    private Light createLight() {
        DirectionalLight light = new DirectionalLight(true, new Color3f(1.0f, 1.0f, 1.0f),
                new Vector3f(-0.3f, 0.2f, -1.0f));

        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));

        return light;
    }

    public static void main(String[] args) {
        Tank3D applet = new Tank3D();
        Frame frame = new MainFrame(applet, 800, 600);
    }

    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();

        if (key == 'a') {

            t3dstep.rotY(Math.PI / 32);
            tank.tg.getTransform(tank.t3d);
            tank.t3d.get(matrix);
            tank.t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            tank.t3d.mul(t3dstep);
            tank.t3d.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
            tank.tg.setTransform(tank.t3d);

        }

        if (key == 'd') {

            t3dstep.rotY(-Math.PI / 32);
            tank.tg.getTransform(tank.t3d);
            tank.t3d.get(matrix);
            tank.t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            tank.t3d.mul(t3dstep);
            tank.t3d.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
            tank.tg.setTransform(tank.t3d);

        }

        if (key == 'w') {

            Transform3D t3dstep = new Transform3D();
            t3dstep.set(new Vector3d(10.0f, 0.0, 0.0));

            tank.tg.getTransform(tank.t3d);
            tank.t3d.mul(t3dstep);
            tank.tg.setTransform(tank.t3d);

        }

        if (key == 's') {

            Transform3D t3dstep = new Transform3D();
            t3dstep.set(new Vector3d(-10.0f, 0.0, 0.0));

            tank.tg.getTransform(tank.t3d);
            tank.t3d.mul(t3dstep);
            tank.tg.setTransform(tank.t3d);

        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    static class MovingTank extends Behavior {

        public TransformGroup tg = null;
        public Transform3D t3d = null;
        private Transform3D t3dstep = new Transform3D();
        private WakeupOnElapsedFrames wakeFrame = null;

        public MovingTank(String filename) {

            tg = new TransformGroup();
            t3d = new Transform3D();

            t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            t3d.setScale(1.0);
            tg.setTransform(t3d);

            ObjectFile loader = new ObjectFile();
            Scene s = null;

            try {
                s = loader.load(filename);
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }

            tg.addChild(s.getSceneGroup());

            tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 500.0);
            this.setSchedulingBounds(bounds);
        }

        public void initialize() {
            wakeFrame = new WakeupOnElapsedFrames(0);
            wakeupOn(wakeFrame);
        }

        public void processStimulus(Enumeration criteria) {
            t3dstep.set(new Vector3d(0.0f, 0.0, 0.0));
            tg.getTransform(t3d);
            t3d.mul(t3dstep);
            tg.setTransform(t3d);

            wakeupOn(wakeFrame);
        }
    }
}