package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

import Controller.ColorUtils;
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
	private ArrayList<SColor> paletteCopy;
	private int[] paletteWeights;
	private HashMap<String, Integer> colorToAngleMap;
	private HashMap<Integer, String> angleToColorMap;
	private int colorIndex = 0;
	private int stack = 0;
	private SColor main = ColorUtils.closestColor(Color.WHITE);
	private SColor sec = ColorUtils.closestColor(Color.WHITE);
	private SColor textC = ColorUtils.closestColor(Color.BLACK);

	/**
	 * Constructor for the handler.
	 */
	public ViewHandler()
	{
		io = SpectrumView.getInstance();
		currentColor = null;
		mixer = new ArrayList<SColor>();
		palette = new ArrayList<SColor>();
		colorToAngleMap = new HashMap<>();
		angleToColorMap = new HashMap<>();
		buildMaps();
	}

	/**
	 * Getter for the palette.
	 * 
	 * @return the palette.
	 */
	public List<SColor> getPalette()
	{
		return this.palette;
	}
	
	/**
	 * Setter for the palette.
	 * 
	 * @param color is the color to add to the palette.
	 */
	public void addPalette(SColor color) 
	{
		palette.add(color);
	}

	/**
	 * Handler for the posterize function.
	 */
	public void poster()
	{

		paletteCopy = new ArrayList<>(palette);

		if (paletteCopy.isEmpty())
		{

			JOptionPane.showMessageDialog(null, "No color selected");
		}

		else
		{

			io.displayPosterImage();
		}
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
			if (!mixer.contains(this.currentColor))
			{
				mixer.add(this.currentColor);
				io.buildMixPanel(mixer);
			} else
			{
				JOptionPane.showMessageDialog(null,
						"Mixer already contains this color.");
			}
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
			if (!palette.contains(this.currentColor))
			{
				palette.add(this.currentColor);
				io.buildPalettePanel(palette);
			} else
			{
				JOptionPane.showMessageDialog(null,
						"Palette already contains this color.");
			}
		}

	}

	/**
	 * Builds a color from the Palette.
	 */
	public void buildColor()
	{
		paletteCopy = new ArrayList<>(palette);
		paletteWeights = new int[palette.size()];
		if (currentColor != null && !paletteCopy.isEmpty())
		{
			colorIndex = 0;
			String mixture = reverseMix();
			palette = new ArrayList<>(paletteCopy);
			io.buildPalettePanel(palette);
			JOptionPane.showMessageDialog(null,
					"To make " + currentColor.toString()
							+ ", mix your palette in the following way:\n"
							+ mixture);
		}
		if (currentColor == null)
		{
			JOptionPane.showMessageDialog(null,
					"You must select a color to make from mixture");
		}
		if (paletteCopy.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "The palette is empty");
		}
	}

	/**
	 * Clears the Mix Panel.
	 */
	public void clearMix()
	{
		mixer.clear();
		io.buildMixPanel(mixer);
	}

	/**
	 * Clears the color palette.
	 */
	public void clearPalette()
	{
		palette.clear();
		io.buildPalettePanel(palette);

	}

	/**
	 * Changes the settings.
	 */
	public void changeSettings()
	{
		io.settingChanges(main, sec, textC);

	}

	/**
	 * Displays a popup next to the mouse that displays the name of the Color
	 * object the mouse is hovering over. This popup will disappear after
	 * roughly one second.
	 * 
	 * @param e     is the mouse event that causes this method to run. It is
	 *              used here to get the location of the mouse.
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
		popup = PopupFactory.getSharedInstance().getPopup(e.getComponent(),
				text, e.getXOnScreen() - 25, e.getYOnScreen() - 20);

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
	 * Used to display the harmonious colors display panel.
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
			case "palette":
				io.displayColorPalette();
				break;
			case "settings":
				io.displaySettings();
				break;
			case "test":
				io.displayTestColor();
				break;
			case "upload":
				io.displayImageUpload();
				break;
			case "Check":
				io.displayTestColor(true, false);
				break;
			case "New Color":
				io.displayTestColor(false, true);
				break;
			default:
				break;
		}
	}

	/**
	 * Handles the functionality of editing the image.
	 */
	public void editImage()
	{
		int value = 0;
		int chroma = 0;
		JTextField v = new JTextField(5);
		JTextField c = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Value:"));
		myPanel.add(v);
		myPanel.add(new JLabel("Chroma:"));
		myPanel.add(c);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Change Chroma or Value of image",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION)
		{
			if (v.getText().equals(""))
			{
				v.setText("0");
			}
			if (c.getText().equals(""))
			{
				c.setText("0");
			}
			try
			{
				value = Integer.parseInt(v.getText());
				chroma = Integer.parseInt(c.getText());

				io.editImageColorPanes(value, chroma);

			} catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null,
						"Please enter numbers within the range 1-256");
			}
		}
	}

	/**
	 * Displays a Panel for user to enter an RGB value and for it to display
	 * afterwards.
	 */
	public void findRGBPanel()
	{
		int red = 0;
		int blue = 0;
		int green = 0;
		JTextField r = new JTextField(5);
		JTextField g = new JTextField(5);
		JTextField b = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Red:"));
		myPanel.add(r);
		myPanel.add(new JLabel("Green:"));
		myPanel.add(g);
		myPanel.add(new JLabel("Blue:"));
		myPanel.add(b);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please Enter RGB Values", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION)
		{
			try
			{
				// the String to int conversion happens here
				red = Integer.parseInt(r.getText());
				green = Integer.parseInt(g.getText());
				blue = Integer.parseInt(b.getText());

				if (red > 256 || blue > 256 || green > 256)
				{
					JOptionPane.showMessageDialog(null,
							"Please enter numbers within the range 1-256");

				} else
				{
					Color color = new Color(red, green, blue);
					SColor finalColor = ColorUtils.closestColor(color);
					io.displayMixedColor(finalColor);
				}

			} catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null,
						"Please enter numbers within the range 1-256");
			}
		}
	}

	/**
	 * Mixes the stored colors.
	 * 
	 * @throws FileNotFoundException
	 */
	public void mix()
	{
		// loop through the mixer to mix the colors
		while (mixer.size() >= 2)
		{
			SColor newColor = ColorUtils.mixTwoColors(mixer.remove(0),
					mixer.remove(0), angleToColorMap, colorToAngleMap);
			mixer.add(newColor);
		}
		if (!mixer.isEmpty())
		{
			io.buildMixPanel(mixer);
			io.displayMixedColor(mixer.get(0));
		}
	}

	/**
	 * Mixes the palette colors.
	 * 
	 * @return the color resulting from this mixture.
	 * @throws FileNotFoundException
	 */
	public SColor mixPalette()
	{
		// loop through the mixer to mix the colors
		while (palette.size() >= 2)
		{
			SColor newColor = ColorUtils.mixTwoColors(palette.remove(0),
					palette.remove(0), angleToColorMap, colorToAngleMap);
			palette.add(newColor);
		}

		if (!palette.isEmpty())
		{
			return palette.get(0);
		} else
		{
			return null;
		}
	}
    
	/**
	 * Throws an error if nothing is selected.
	 */
	public void nothingSelected()
	{
		io.displaySelectError();
	}

	/**
	 * Determines how to mix colors to get to a desired color.
	 * 
	 * @return the weights of the colors to mix.
	 */
	public String reverseMix()
	{
		String retStr = "";
		SColor mixColor = mixPalette();
		stack++;
		if (currentColor.equals(mixColor))
		{
			for (int i = 0; i < paletteWeights.length; i++) 
			{
				paletteWeights[i] = 1;
			}
			retStr = mixture();
		} else if (stack > 500)
		{
			retStr = "mixture not possible";
			stack = 0;
		} else
		{
			double distance = reverseMixHelper(mixColor);

			if (colorIndex < paletteWeights.length)
			{
				return reverseMix();
			} else
			{
				if (distance < 10)
				{
					retStr = mixture();
				} else
				{
					colorIndex = 0;
					return reverseMix();
				}
			}
		}
		
		return retStr;
	}

	/**
	 * Handles functionality for right clicking a color in the program.
	 * 
	 * @param color        is the color that was right clicked.
	 * @param inColorPanel is the panel of the color that was clicked.
	 */
	public void rightClick(SColor color, JPanel inColorPanel)
	{
		if (this.colorPanel != null)
		{
			this.colorPanel.setBorder(null);
		}
		this.currentColor = color;
		this.colorPanel = inColorPanel;

		String[] options = {"Add selected to palette", "Clear palette"};
		int x = JOptionPane.showOptionDialog(null, "What would you like to do?",
				"Palette options", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if (x == 0)
		{
			if (!palette.contains(color))
			{
				palette.add(color);
			} else
			{
				JOptionPane.showMessageDialog(null,
						"Palette already contains this color.");
			}
		} else if (x == 1)
		{
			palette.clear();
		}

		io.buildPalettePanel(palette);
	}

	/**
	 * Saves the selected color.
	 * 
	 * @param color        selected by the mouse
	 * @param inColorPanel is the color panel to set a border to for
	 */
	public void selectedColor(SColor color, JPanel inColorPanel)
	{
		if (this.colorPanel != null)
		{
			this.colorPanel.setBorder(null);
		}
		this.currentColor = color;
		this.colorPanel = inColorPanel;
	}

	/**
	 * Updates settings of the GUI.
	 * 
	 * @param string command
	 */
	public void settings(String string)
	{
		if (currentColor == null)
		{
			nothingSelected();
		} else
		{

			switch (string)
			{
				case "main":
					main = this.currentColor;
					break;
				case "sec":
					sec = this.currentColor;
					break;
				case "text":
					textC = this.currentColor;
					break;
				default:
					break;
			}

		}
	}

	/**
	 * Starts the program by opening the GUI.
	 * 
	 * @throws IOException
	 */
	public void start()
	{
		io.displayOpeningPage();
	}

	/**
	 * Builds the maps used by the mixing methods.
	 */
	private void buildMaps()
	{
		List<String> colors = new ArrayList<String>();
		String[] colorNames = {"RP", "R", "YR", "Y", "GY", "G", "BG", "B",
			"PB", "P"};
		double hue = 10.0;
		int j = 0;

		for (int n = 0; n < 40; n++)
		{
			String colorName = "" + hue + colorNames[j];
			colors.add(colorName);
			if (hue == 10)
			{
				j++;
			}
			if (hue == 10)
			{
				hue = 0;
			}
			if (j == 10)
			{
				j = 0;
			}
			hue += 2.5;
		}

		int angleValue = 0;
		for (int i = 0; i < 40; i++)
		{
			colorToAngleMap.put(colors.get(i), angleValue);
			angleToColorMap.put(angleValue, colors.get(i));
			angleValue += 9;
		}
	}

	/**
	 * Compare given color to the target color and return the distance.
	 * 
	 * @param previous    color to compare
	 * @param targetColor target color
	 * @return color that is closest to the desired color
	 */
	private double compareToColor(SColor previous, SColor targetColor)
	{
		double angle1 = colorToAngleMap.get(previous.getName());
		double angle2 = colorToAngleMap.get(targetColor.getName());
		if (angle1 == 0)
		{
			angle1 = 360;
		}

		double hue = Math.pow(angle1 - angle2, 2);
		double value = Math.pow(previous.getValue() - targetColor.getValue(),
				2);
		double chroma = Math.pow(previous.getChroma() - targetColor.getChroma(),
				2);

		double result = Math.sqrt(hue + value + chroma);

		return result;
	}

	/**
	 * Builds and returns the string representation of the mixture needed to
	 * create a color.
	 * 
	 * @return the string representation of the mixture needed to create a
	 *         color.
	 */
	private String mixture()
	{
		String mixture = "";
		int i = 0;

		for (SColor color : paletteCopy)
		{
			mixture += paletteWeights[i] + " x " + color.toString() + "\n";
			i++;
		}

		return mixture;
	}

	/**
	 * Decides how to adjust weights for the reverseMixer.
	 * 
	 * @param color current mixColor to compare
	 * @return the distance of the color from the target
	 */
	private double reverseMixHelper(SColor color)
	{
		palette.clear();

		// Calculate the distance between the target color and the current color
		// mix
		double curDistance = compareToColor(color, currentColor);

		// set up palette weights array
		paletteWeights[colorIndex]++;

		// set up new palette using palette weights
		for (int w = 0; w < paletteWeights.length; w++)
		{
			for (int p = 0; p < paletteWeights[w]; p++)
			{
				palette.add(new SColor(paletteCopy.get(w)));
			}
		}

		// Make new color with updated weights.
		SColor newColor = mixPalette();

		double newDistance = compareToColor(newColor, currentColor);

		if (newDistance > curDistance)
		{
			paletteWeights[colorIndex]--;
			colorIndex++;
			palette.clear();
			for (int w = 0; w < paletteWeights.length; w++)
			{
				for (int p = 0; p < paletteWeights[w]; p++)
				{
					palette.add(new SColor(paletteCopy.get(w)));
				}
			}
			if (palette.isEmpty())
			{
				palette = new ArrayList<>(paletteCopy);
			}
		}
		return curDistance;
	}

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
