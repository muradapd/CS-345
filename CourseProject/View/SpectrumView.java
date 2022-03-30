package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Controller.ColorUtils;
import Model.SColor;
import Model.SColorSpectrum;

/**
 * Class to create to user interface.
 * 
 * @author Grant Perales
 * @version 10/21/19
 *
 */
@SuppressWarnings("serial")
public class SpectrumView extends JFrame
{
	private static SpectrumView view;
	private static ViewListener listen;
	private SColorSpectrum spectrum;
	private JPanel mainPanel;
	private JPanel titlePanel;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel mixPanel;
	private JPanel palettePanel;

	/**
	 * Default constructor for the GUI.
	 */
	public SpectrumView()
	{
		// Build the Main Menu Panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);

		// Build Color Panel
		titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);

		// Build Button Panel
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		colorPanel = new JPanel();
		colorPanel.setBackground(Color.WHITE);

		mixPanel = new JPanel();
		mixPanel.setBackground(Color.WHITE);

		palettePanel = new JPanel();
		palettePanel.setBackground(Color.WHITE);

		spectrum = new SColorSpectrum();

		setTitle("Spectrum");
	}

	/**
	 * Displays the main menu.
	 */
	public void displayMainPage()
	{
		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();

		listen = ViewListener.getInstance();

		// set screen size
		setSize(600, 400);

		buttonPanel.setLayout(new GridLayout(3, 2));
		titlePanel.setLayout(new GridLayout(2, 1));

		JLabel titleLabel = new JLabel("Spectrum Color Interface",
				JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		// Add titles to Grid
		titlePanel.add(titleLabel);
		addLabel("Choose option below", titlePanel);

		// Add Buttons to Grid
		addButton("View Colors", buttonPanel);
		addButton("Mix Colors", buttonPanel);
		addButton("Find Color on Palette", buttonPanel);
		addButton("Find Image Color", buttonPanel);
		addButton("View 3D Model", buttonPanel);
		addButton("Test Your Skills", buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color Choice Menu.
	 */
	public void displayColors(String sort)
	{
		int rows = 12;
		int cols = 42;

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();

		listen = ViewListener.getInstance();

		JPanel sidePanel = new JPanel();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(5, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));
		JPanel[][] colorPanes = new JPanel[rows][cols];

		buildDefaultColorPanes(colorPanes, rows, cols, sort);

		// Add titles to Grid
		addLabel("Spectrum Color View", titlePanel);
		addLabel("Choose colors below", titlePanel);

		// Add Buttons to Grid
		addButton("View Colors by Hue", buttonPanel);
		addButton("View Colors by Chroma", buttonPanel);
		addButton("View Harmonious Colors", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("Munsell Colors by " + sort,
				JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(colorPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color mix screen.
	 */
	public void displayMixColors()
	{
		int rows = 12;
		int cols = 42;

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();

		listen = ViewListener.getInstance();

		JPanel sidePanel = new JPanel();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(5, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));
		JPanel[][] colorPanes = new JPanel[rows][cols];

		buildDefaultColorPanes(colorPanes, rows, cols, "Hue");

		// Add titles to Grid
		addLabel("Spectrum Color Mix", titlePanel);
		addLabel("Choose colors below", titlePanel);

		// Add Buttons to Grid
		addButton("Add to Mixer", buttonPanel);
		addButton("Mix!", buttonPanel);
		addButton("Clear Mixer", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("Mix Munsell Colors", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(mixPanel, BorderLayout.SOUTH);
		mainPanel.add(colorPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color image upload screen.
	 */
	public void displayFindImageColors(File image)
	{
		BufferedImage img = null;

		try
		{
			img = ImageIO.read(image);
		} catch (IOException e)
		{
			System.out.println("Bad file. Add functionality here");
			System.exit(0);
		}

		int rows = img.getHeight();
		int cols = img.getWidth();

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();

		listen = ViewListener.getInstance();

		JPanel sidePanel = new JPanel();

		buttonPanel.setLayout(new GridLayout(3, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));
		JPanel[][] imagePanes = new JPanel[rows][cols];

		buildImageColorPanes(imagePanes, rows, cols, img);

		// Add titles to Grid
		addLabel("Spectrum Color Identifier", titlePanel);
		addLabel("Upload Image", titlePanel);

		// Add Buttons to Grid
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("Find Color on Image", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(colorPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setSize(750, 650);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color eye test.
	 */
	public void displayTestColor()
	{
		int rows = 12;
		int cols = 42;

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();

		listen = ViewListener.getInstance();

		JPanel sidePanel = new JPanel();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(4, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));

		// Add titles to Grid
		addLabel("Spectrum Color Tester", titlePanel);
		addLabel("Take Test", titlePanel);

		// Add Buttons to Grid
		addButton("True", buttonPanel);
		addButton("False", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("Take Color Test", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(colorPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void displayHarmoniousColors(SColor color)
	{
		if (color == null)
		{
			displaySelectError();
		} else
		{
			String[] colorOptions = { "Complementary Color", "Analogous Colors", "Triad Colors", "Tetrad Colors",
					"Square Colors" };

			JFrame colorFrame = new JFrame();
			JPanel container = new JPanel();
			JPanel comboPane = new JPanel();
			JPanel image = new JPanel();
			JPanel main = new JPanel(new CardLayout());
			JLabel label = new JLabel("Complement", SwingConstants.CENTER);

			image.setLayout(new GridLayout());

			container.setLayout(new BorderLayout());
			JComboBox<String> dropdown = new JComboBox<String>(colorOptions);

			// Create listener to set the color display.
			ActionListener listener = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent event)
				{
					if (dropdown.getSelectedItem() == "Complementary Color")
					{
						label.setText("Complementary");
						SColor scolor = ColorUtils.complementaryColor(color);
						Color newColor = new Color(scolor.getRed(), scolor.getGreen(), scolor.getBlue());
						image.setBackground(newColor);
					} else if (dropdown.getSelectedItem() == "Analogous Colors")
					{
						label.setText("Analogous");
						JPanel pane1 = new JPanel();
						JPanel pane2 = new JPanel();
						List<SColor> analogList = ColorUtils.analogousColors(color);
						Color analogColor1 = new Color(analogList.get(0).getRed(), analogList.get(0).getGreen(),
								analogList.get(0).getBlue());
						Color analogColor2 = new Color(analogList.get(1).getRed(), analogList.get(1).getGreen(),
								analogList.get(1).getBlue());
						image.setBackground(null);
						image.add(pane1);
						image.add(pane2);
						pane1.setBackground(analogColor1);
						pane2.setBackground(analogColor2);

					} else if (dropdown.getSelectedItem() == "Triad Colors")
					{
						label.setText("Triad");
					} else if (dropdown.getSelectedItem() == "Tetrad Colors")
					{
						label.setText("Tetrad");
					} else
					{
						label.setText("Square");
					}
				}
			};

			listener.actionPerformed(null);
			dropdown.addActionListener(listener);
			comboPane.add(dropdown);

			colorFrame.add(container);
			container.add(comboPane, BorderLayout.PAGE_START);
			container.add(main);
			main.add(image, BorderLayout.CENTER);
			main.add(label, BorderLayout.LINE_END);
			colorFrame.setSize(500, 400);
			colorFrame.setLocationRelativeTo(null);
			colorFrame.setVisible(true);

			this.colorPanel.setBorder(null);
		}
	}

	/**
	 * Displays the 3D Color model.
	 */
	public void displayColorModel()
	{
		int rows = 12;
		int cols = 42;

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();

		listen = ViewListener.getInstance();

		JPanel sidePanel = new JPanel();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(2, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));

		// Add titles to Grid
		addLabel("Spectrum Color 3D Model", titlePanel);
		addLabel("View Model", titlePanel);

		// Add Buttons to Grid
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("View 3D Model", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(colorPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color Palette Screen.
	 */
	public void displayColorPalette()
	{
		int rows = 12;
		int cols = 42;

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();

		listen = ViewListener.getInstance();

		JPanel sidePanel = new JPanel();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(3, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));

		// Add titles to Grid
		addLabel("Spectrum Palette Helper", titlePanel);
		addLabel("Make Colors", titlePanel);

		// Add Buttons to Grid
		addButton("Add to Palette", buttonPanel);
		addButton("Make Color", buttonPanel);
		addButton("Clear Palette", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("Palette Helper", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(colorPanel);

		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void displayImageUpload()
	{
		JFrame uploadFrame = new JFrame();
		JTextArea input = new JTextArea("Type image filepath here");
		JButton getInput = new JButton("Upload Image");

		uploadFrame.setLayout(new BorderLayout());
		uploadFrame.add(input, BorderLayout.NORTH);
		uploadFrame.add(getInput, BorderLayout.CENTER);
		uploadFrame.add(new JLabel("                              "),
				BorderLayout.WEST);
		uploadFrame.add(new JLabel("                              "),
				BorderLayout.EAST);

		ActionListener listener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null,
						"Allow up to a minute to load.");
				displayFindImageColors(new File(input.getText()));
				uploadFrame.dispose();
			}
		};

		getInput.addActionListener(listener);

		uploadFrame.setSize(400, 75);
		uploadFrame.setLocationRelativeTo(null);
		uploadFrame.setVisible(true);
	}

	/**
	 * Clears the mixer.
	 */
	public void clearMix()
	{
		mixPanel.removeAll();

	}

	/**
	 * Clears the palette.
	 */
	public void clearPalette()
	{
		palettePanel.removeAll();

	}

	/**
	 * Builds an input image out of JPanels and adds/assigns SColors and
	 * backgrounds to those JPanels. At the end, these JPanels end up
	 * representing the pixels in the input image.
	 * 
	 * @param colorPanes is the 2D array to use to represent the image as a
	 *                   'table' of JPanels.
	 * @param rows       is the number of rows in the SD array colorPanes.
	 * @param cols       is the number of columns in the SD array colorPanes.
	 * @param img        is the input image represented as a BufferedImage.
	 */
	private void buildImageColorPanes(JPanel[][] colorPanes, int rows, int cols,
			BufferedImage img)
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				colorPanes[i][j] = new JPanel();
				colorPanel.add(colorPanes[i][j]);
			}
		}

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				Color imageColor = new Color(img.getRGB(j, i));
				colorPanes[i][j].add(closestColor(imageColor));
				colorPanes[i][j].setBackground(imageColor);
				colorPanes[i][j].addMouseListener(listen);
			}
		}
	}

	/**
	 * Finds the SColor that is closest in euclidean distance (in the RGB color
	 * space) to the given java Color object.
	 * 
	 * @param imageColor is the java Color to find a close SColor match for.
	 * @return the SColor that is most similar to the given java color object.
	 */
	private SColor closestColor(Color imageColor)
	{
		SColor closestColor = null;
		double closestDistance = Integer.MAX_VALUE;

		for (SColor color : spectrum.getSpectrum())
		{
			double red = Math.pow(imageColor.getRed() - color.getRed(), 2);
			double green = Math.pow(imageColor.getGreen() - color.getGreen(),
					2);
			double blue = Math.pow(imageColor.getBlue() - color.getBlue(), 2);
			double result = red + green + blue;
			if (closestDistance > result)
			{
				closestDistance = result;
				closestColor = color;
			}
		}
		return new SColor(closestColor);
	}

	/**
	 * Builds the colorPanels to display the colors as a default when the 2D
	 * display is opened.
	 * 
	 * @param colorPanes is the array of color panes that we will use to display
	 *                   colors.
	 * @param row        is the number of rows in our color display.
	 * @param col        is the number of columns in our color display.
	 */
	private void buildDefaultColorPanes(JPanel[][] colorPanes, int rows,
			int cols, String sort)
	{
		// Builds a table of JPanels to hold color objects and puts them in
		// place
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				colorPanes[i][j] = new JPanel();
				colorPanel.add(colorPanes[i][j]);
			}
		}

		buildValues(colorPanes);
		switch (sort)
		{
		case "Hue":
			buildColorsHue(colorPanes, rows, cols);
			break;
		case "Chroma":
			buildColorsChroma(colorPanes, rows, cols);
			break;
		default:
			System.exit(0);
		}

	}

	/**
	 * Builds the spectrum of colors and adds them to the full pane.
	 * 
	 * @param colorPanes is the array of components to add values to.
	 * @param rows       is the number of rows in the full pane.
	 * @param cols       is the number of columns in the full pane.
	 */
	private void buildColorsChroma(JPanel[][] colorPanes, int rows, int cols)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Builds the spectrum of pure values (Black-White) and adds them to the
	 * full pane.
	 * 
	 * @param colorPanes is the array of components to add values to.
	 */
	private void buildValues(JPanel[][] colorPanes)
	{
		int row = 11;

		for (int v = 0; v <= 10; v++)
		{
			for (SColor color : spectrum.getSpectrum())
			{

				if (color.getValue() == v && color.getChroma() == 0)
				{
					Color addColor = new Color(color.getRed(), color.getGreen(),
							color.getBlue());
					colorPanes[row][0].setBackground(addColor);
					JLabel label = new JLabel();
					label.setText("" + (v));
					colorPanes[row][1].add(label);
					colorPanes[row][0].add(color);
					colorPanes[row][0].addMouseListener(listen);
					row--;
				}
			}
		}
	}

	/**
	 * Builds the spectrum of colors and adds them to the full pane.
	 * 
	 * @param colorPanes is the array of components to add values to.
	 * @param rows       is the number of rows in the full pane.
	 * @param cols       is the number of columns in the full pane.
	 */
	private void buildColorsHue(JPanel[][] colorPanes, int rows, int cols)
	{
		String[] nameIDs = { "R", "YR", "Y", "GY", "G", "BG", "B", "PB", "P",
				"RP" };
		double hue = 2.5;
		int value = 1;
		int chroma = 4;
		int startCol = 2;
		int row = 10;
		int id = 0;

		for (int col = startCol; col < cols; col++)
		{
			value = 1;
			row = 10;

			if (hue > 10.0)
			{
				hue = 2.5;
				id++;
			}

			for (SColor color : spectrum.getSpectrum())
			{
				if (row == 1)
				{
					break;
				}
				String name = color.getName();
				boolean goodName = name.substring(name.length() - 1)
						.equals(nameIDs[id])
						|| name.substring(name.length() - 2)
								.equals(nameIDs[id]);
				if (color.getHue() == hue && color.getValue() == value
						&& goodName)
				{
					Color addColor = new Color(color.getRed(), color.getGreen(),
							color.getBlue());
					colorPanes[row][col].setBackground(addColor);
					colorPanes[row][col].add(color);
					colorPanes[row][col].addMouseListener(listen);
					value++;
					row--;
				}
			}
			JLabel label = new JLabel();
			label.setText("" + hue);
			if (hue == 10.0)
			{
				label.setText("" + 10);
			}
			colorPanes[0][col].add(label);
			hue += 2.5;
		}
	}

	/**
	 * Builds the mixer panel
	 * 
	 * @param mixer
	 */
	public void buildMixPanel(ArrayList<SColor> mixer)
	{

		mixPanel.removeAll();

		JPanel[] panels = new JPanel[mixer.size()];

		for (int i = 0; i < mixer.size(); i++)
		{
			if (mixer.get(i) instanceof SColor)
			{
				SColor color = mixer.get(i);
				Color addColor = new Color(color.getRed(), color.getBlue(),
						color.getGreen());
				panels[i].setBackground(addColor);
				panels[i].add(color);
				panels[i].addMouseListener(listen);

			}
			mixPanel.add(panels[i]);

		}
		displayMixColors();

	}

	public void buildPalettePanel(ArrayList<SColor> palette)
	{
		// TODO Auto-generated method stub

	}

	/********************** Static Methods ********************/

	/**
	 * Adds a button to the container.
	 * 
	 * @param text      to be added
	 * @param container to add button in
	 */
	private static void addButton(String text, JPanel container)
	{
		JButton button = new JButton(text);
		container.add(button);
		button.addActionListener(listen);
	}

	/**
	 * Adds a button to the container.
	 * 
	 * @param text      to be added
	 * @param container to add button in
	 */
	private static void addLabel(String text, JPanel container)
	{
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		container.add(label);
	}

	/**
	 * Ensures a single instance of the object.
	 * 
	 * @return an instance of the view
	 */
	public static SpectrumView getInstance()
	{
		if (view == null)
		{
			view = new SpectrumView();
		}
		return view;
	}

}
