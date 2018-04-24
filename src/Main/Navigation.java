package Main;

public interface Navigation {

	public void update (double dT);
	
	public void render ();
	
	public void leftClick (int x, int y);
	
	public void rightClick (int x, int y);
}
