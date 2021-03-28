package Math3D;

import Main.MainCanvas;

public class Triangulo3d {
	Vetor3D p1;
	Vetor3D p2;
	Vetor3D p3;
	
	public Triangulo3d(Vetor3D p1, Vetor3D p2, Vetor3D p3) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public void desenhaSe(MainCanvas canvas, Matriz4x4 m) {
		/*Matriz4x4 m2 = new Matriz4x4();
		m2.setTranslate(0, -250, 0);
		
		Matriz4x4 m3 = new Matriz4x4();
		m3.setTranslate(0, 250, 0);
		
		Vetor3D p1l = m2.multiplicaVetor(p1);
		Vetor3D p2l = m2.multiplicaVetor(p2);
		Vetor3D p3l = m2.multiplicaVetor(p3);
		
		p1l = m.multiplicaVetor(p1l);
		p2l = m.multiplicaVetor(p2l);
		p3l = m.multiplicaVetor(p3l);
		
		p1l = m3.multiplicaVetor(p1l);
		p2l = m3.multiplicaVetor(p2l);
		p3l = m3.multiplicaVetor(p3l);*/
		
		Vetor3D p1l = m.multiplicaVetor(p1);
		Vetor3D p2l = m.multiplicaVetor(p2);
		Vetor3D p3l = m.multiplicaVetor(p3);
		
		canvas.drawLine((int)p1l.x,(int)p1l.y,(int)p2l.x,(int)p2l.y);
		canvas.drawLine((int)p2l.x,(int)p2l.y,(int)p3l.x,(int)p3l.y);
		canvas.drawLine((int)p3l.x,(int)p3l.y,(int)p1l.x,(int)p1l.y);
	}
}
