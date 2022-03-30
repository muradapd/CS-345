package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.SColor;

/**
 * Gets the main calls from the GUI and sends them to the proper location.
 * 
 * @author Grant Perales
 * @version 10/22/19
 */
public class ViewListener
		implements ActionListener, WindowListener, MouseListener
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
			case "Add to Mixer":
				handle.addColor();
				break;
			case "Add to Palette":
				handle.addColorPalette();
				break;
			case "Posterize the Image":
				handle.poster();
				break;
			case "Apply":
				handle.changeSettings();
				break;
			case "Clear Mixer":
				handle.clearMix();
				break;
			case "Clear Palette":
				handle.clearPalette();
				break;
			case "Edit Image":
				handle.editImage();
				break;
			case "Make New Colors":
				handle.guiChange("palette");
				break;
			case "Find RGB":
				handle.findRGBPanel();
				break;
			case "View Image Colors":
				handle.guiChange("image");
				break;
			case "Make Color":
				handle.buildColor();
				break;
			case "Mix Colors":
				handle.guiChange("mix");
				break;
			case "Mix!":
				handle.mix();
				break;
			case "New Image":
				handle.guiChange("image");
				break;
			case "Return":
			case "Start!":
				handle.guiChange("menu");
				break;
			case "Set Main Color":
				handle.settings("main");
				break;
			case "Set Secondary Color":
				handle.settings("sec");
				break;
			case "Set Text Color":
				handle.settings("text");
				break;
			case "Settings":
				handle.guiChange("settings");
				break;
			case "Test Your Skills":
				handle.guiChange("New Color");
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
			case "View Harmonious Colors":
				handle.displayHarmoniousColors();
				break;
			case "Check":
				handle.guiChange("Check");
				break;
			case "New Color":
				handle.guiChange("New Color");
				break; 
			case "Quit":
				System.exit(0);
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
		try
		{
			SColor color = (SColor) colorPanel.getComponents()[0];
			handle.displayColorString(e, color);
		} catch (ArrayIndexOutOfBoundsException ex)
		{
			handle.displayColorString(e, null);
		}
	}

	/**
	 * Creates output when color is selected.
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		JPanel colorPanel = (JPanel) e.getSource();
		colorPanel.setBorder(BorderFactory.createLineBorder(Color.cyan));
		SColor color = (SColor) colorPanel.getComponents()[0];
		if (SwingUtilities.isRightMouseButton(e))
		{
			handle.rightClick(color, colorPanel);
		} else
		{
			handle.selectedColor(color, colorPanel);
		}
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// unimplemented
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// unimplemented
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// unimplemented
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// unimplemented
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// unimplemented
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// unimplemented
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// unimplemented
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// unimplemented
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// unimplemented
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
