package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Model.SColor;

/**
 * Gets the main calls from the GUI and sends them to the proper location
 * 
 * @author Grant Perales
 * @version 10/22/19
 */
public class ViewListener implements ActionListener, WindowListener, MouseListener
{
	private static ViewListener listen;
	private ViewHandler handle;

	/**
	 * Constructor for the Listener.
	 */
	public ViewListener()
	{
		handle = ViewHandler.getInstance();
	}

	/**
	 * Listens to button pushes.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();

		switch (btn.getText())
		{

		case "False":
			break;
		case "Find Color on Palette":
			handle.guiChange("palette");
			break;
		case "Find Image Color":
			handle.guiChange("image");
			break;
		case "Mix Colors":
			handle.guiChange("mix");
			break;	
		case "Mix!":
			break;
		case "Return":
			handle.guiChange("menu");
			break;
		case "Select Palette Colors":
			break;
		case "Test Your Skills":
			handle.guiChange("test");
			break;
		case "True":
			break;
		case "Upload Image":
			break;
		case "View 3D Model":
			handle.guiChange("model");
			break;
		case "View Colors":
			handle.guiChange("hue");
			break;
		case "View Colors by Hue":
			handle.guiChange("hue");
			break;
		case "View Colors by Chroma":
			handle.guiChange("chroma");
			break;
		default:
			System.exit(0);
		}
	}
	
	/**
	 * Creates output when mouse hovers over a color.
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		JPanel colorPanel = (JPanel) e.getSource();
		SColor color = (SColor) colorPanel.getComponents()[0];
		handle.displayColorString(e, color);
	}

	/**
	 * Creates output when color is selected.
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		JPanel colorPanel = (JPanel) e.getSource();
		SColor color = (SColor) colorPanel.getComponents()[0];
		handle.displayHarmoniousColors(color);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Gets the instance of the IOListener class.
	 * 
	 * @return instance of this class
	 */
	public static ViewListener getInstance()
	{
		if (listen == null)
		{
			listen = new ViewListener();
		}
		return listen;
	}
}
