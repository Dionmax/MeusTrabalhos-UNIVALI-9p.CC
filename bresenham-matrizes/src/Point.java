
public class Point {
	float x;
	float y;
	
	public Point(float _x,float _y) {
		x = _x;
		y = _y;
	}
	
	public void translate(float a, float b) {
		x = x+a;
		y = y+b;
	}
	public void scale(float a, float b) {
		x = x*a;
		y = y*b;
	}
	
	public void rotate(float ang) {
		double angrad = Math.toRadians(ang);
		float xold = x;
		float yold = y;
		
		x = (float)((xold*Math.cos(angrad))+(yold*Math.sin(angrad)));
		y = (float)((yold*Math.cos(angrad))-(xold*Math.sin(angrad)));
	}
}
