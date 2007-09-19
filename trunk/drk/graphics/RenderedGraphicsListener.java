public class RenderedGraphicsListener implements GLEventListener
{

	public void main(String [] args)
	{
		JFrame jf=new JFrame("Dr. Karnaugh's Lab");
		jf.add(new GLJPanel(new RenderedGraphicsListener()));
		jf
	}
}
