package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

import Model.SColor;

/**
 * Handles the different changes in the GUI, deals with user input as well.
 */
public class ViewHandler
{
	private static ViewHandler handle;
	private SpectrumView io;
	private Popup popup;
	private JPanel colorPanel;
	private Timer timer;
	private SColor currentColor;
	private ArrayList<SColor> mixer;
	private ArrayList<SColor> palette;

	/**
	 * Constructor for the handler.
	 */
	public ViewHandler()
	{
		io = SpectrumView.getInstance();
		currentColor = null;
		mixer = new ArrayList<SColor>();
		palette = new ArrayList<SColor>();
	}

	/**
	 * Adds a color to the mixer.
	 */
	public void addColor()
	{
		if (currentColor == null)
		{
			nothingSelected();
		} else
		{
			mixer.add(currentColor);
			io.buildMixPanel(mixer);
		}

	}

	/**
	 * Adds Colors to the Palette.
	 */
	public void addColorPalette()
	{
		if (currentColor == null)
		{
			nothingSelected();
		} else
		{
			palette.add(this.currentColor);
			io.buildPalettePanel(palette);
		}

	}

	/**
	 * Builds a color from the Palette
	 */
	public void buildColor()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Clears the Mix Panel.
	 */
	public void clearMix()
	{
		mixer.clear();
		io.clearMix();

	}

	/**
	 * Clears the color palette.
	 */
	public void clearPalette()
	{
		palette.clear();
		io.clearPalette();

	}

	/**
	 * Displays a popup next to the mouse that displays the name of the Color object
	 * the mouse is hovering over. This popup will disappear after roughly one
	 * second.
	 * 
	 * @param e     is the mouse event that causes this method to run. It is used
	 *              here to get the location of the mouse.
	 * @param color is the color who's name is being displayed.
	 */
	public void displayColorString(MouseEvent e, SColor color)
	{
		if (popup != null)
		{
			popup.hide();
			timer.stop();
		}

		JLabel text;

		if (color == null)
		{
			text = new JLabel("Cannot ID color");
		} else
		{
			text = new JLabel(color.toString());
		}
		popup = PopupFactory.getSharedInstance().getPopup(e.getComponent(), text, e.getXOnScreen() - 25,
				e.getYOnScreen() - 20);

		popup.show();

		// Timer that is used to wipe the popup after roughly one second
		timer = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				popup.hide();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Insert description
	 * 
	 * @param color
	 */
	public void displayHarmoniousColors()
	{
		io.displayHarmoniousColors(this.currentColor);
	}

	/**
	 * Navigates the UI to the correct page.
	 * 
	 * @param button that was pressed
	 */
	public void guiChange(String button)
	{
		switch (button)
		{
		case "menu":
			io.displayMainPage();
			break;
		case "hue":
			io.displayColors("Hue");
			break;
		case "chroma":
			io.displayColors("Chroma");
			break;
		case "mix":
			io.displayMixColors();
			break;
		case "image":
			io.displayImageUpload();
			break;
		case "model":
			io.displayColorModel();
			break;
		case "palette":
			io.displayColorPalette();
			break;
		case "test":
			io.displayTestColor();
			break;
		case "upload":
			io.displayImageUpload();
			break;
		}
	}

	/**
	 * Saves the selected color.
	 * 
	 * @param color selected by the mouse
	 */
	public void selectedColor(SColor color, JPanel colorPanel)
	{
		if (this.colorPanel != null)
		{
			this.colorPanel.setBorder(null);
		}
		this.currentColor = color;
		this.colorPanel = colorPanel;
	}

	/**
	 * Mixes the stored colors.
	 */
	public void mix()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Throws an error if nothing is selected.
	 */
	public void nothingSelected()
	{
		io.displaySelectError();
	}

	/**
	 * Starts the program by opening the GUI.
	 */
	public void start()
	{
		io.displayMainPage();
	}

	/******************* Static Methods ****************/

	/**
	 * Gets Instance of view handler.
	 * 
	 * @return instance of handler
	 */
	public static ViewHandler getInstance()
	{
		if (handle == null)
		{
			handle = new ViewHandler();
		}
		return handle;
	}

}
