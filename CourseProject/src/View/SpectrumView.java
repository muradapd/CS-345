package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.ColorUtils;
import Model.SColor;
import Model.SColorSpectrum;

/**
 * Main user interface class.
 * 
 * @author Grant Perales
 * @version 10/21/19
 *
 */
@SuppressWarnings("serial")
public class SpectrumView extends JFrame
{

	private static final int ROWH = 12;
	private static final int COLH = 42;

	private static SpectrumView view;
	private static ViewListener listen;
	private SColorSpectrum spectrum;
	private JPanel mainPanel;
	private JPanel titlePanel;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel mixPanel;
	private JPanel palettePanel;
	private JPanel sidePanel;
	private JPanel bottomPanel;

	private Color mainColor;
	private Color secondaryColor;
	private Color textColor;

	// used to keep track of what page we are on for the palette
	private String[] pages = {"Main", "View Colors", "Mix Colors",
		"Palette Colors", "Image Colors", "Test Skills", "Settings"};
	private String currentPage;
	private String sort;
	private File image;
	private BufferedImage logo;

	// Color Test stuff
	private int correct;
	private SColor[] c = new SColor[4];

	/**
	 * Default constructor for the GUI.
	 */
	public SpectrumView()
	{
		// Sets the default colors for the UI
		mainColor = Color.WHITE;
		secondaryColor = Color.WHITE;

		// Builds each of the different global panels
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);

		titlePanel = new JPanel();
		titlePanel.setBackground(mainColor);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(mainColor);

		colorPanel = new JPanel();
		colorPanel.setBackground(mainColor);

		mixPanel = new JPanel();
		mixPanel.setBackground(mainColor);

		palettePanel = new JPanel();
		palettePanel.setBackground(mainColor);

		sidePanel = new JPanel();
		sidePanel.setBackground(secondaryColor);

		bottomPanel = new JPanel();
		bottomPanel.setBackground(mainColor);

		// Makes the Color Spectrum
		spectrum = new SColorSpectrum();

		setTitle("Spectrum");
	}

	/**
	 * Opening page with logo.
	 */
	public void displayOpeningPage()
	{

		prepPanels();

		// Set screen size
		setSize(650, 650);
		JLabel picLabel;

		try
		{
			logo = ImageIO
					.read(getClass().getResource("/itten-color-prism.jpg"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picLabel = new JLabel(new ImageIcon(logo));
		add(picLabel);

		// file:///Users/GrantsMAC/eclipse-workspace/cs345/Spectrum/src/itten-color-prism.jpg

		addButton("Start!", buttonPanel);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(picLabel);

		// Displays
		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the main menu.
	 */
	public void displayMainPage()
	{
		// Marks the current page
		currentPage = pages[0];

		// Sets base for panels
		prepPanels();
		buttonPanel.setLayout(new GridLayout(3, 2));
		titlePanel.setLayout(new GridLayout(2, 1));
		bottomPanel.setLayout(new GridLayout(1, 2));

		// Set screen size
		setSize(600, 400);

		// Sets up Title Panel
		JLabel titleLabel = new JLabel("Spectrum Color Interface",
				JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		titlePanel.add(titleLabel);
		addLabel("A Color Tool for Painters", titlePanel);
		titleLabel.setForeground(textColor);

		// Sets up button panels
		addButton("View Colors", buttonPanel);
		addButton("Mix Colors", buttonPanel);
		addButton("Make New Colors", buttonPanel);
		addButton("View Image Colors", buttonPanel);
		addButton("Test Your Skills", buttonPanel);
		addButton("Settings", bottomPanel);
		addButton("Quit", bottomPanel);

		// Makes final panel
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		// Displays
		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color Choice Menu.
	 * 
	 * @param inSort is the type of sort to perform (for viewing the spectrum in
	 *               different ways.)
	 */
	public void displayColors(String inSort)
	{
		currentPage = pages[1];
		this.sort = inSort;

		// Preps the different panels
		prepPanels();
		buttonPanel.setLayout(new GridLayout(6, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(ROWH, COLH));
		JPanel[][] colorPanes = new JPanel[ROWH][COLH];
		buildDefaultColorPanes(colorPanes, ROWH, COLH, sort);

		// Set screen size
		setSize(1050, 400);

		// Add titles to Grid
		addLabel("Spectrum Color View", titlePanel);
		addLabel("Choose colors below", titlePanel);

		// Add Buttons to Grid
		addButton("View Colors by Hue", buttonPanel);
		addButton("View Colors by Chroma", buttonPanel);
		addButton("View Harmonious Colors", buttonPanel);
		addButton("Find RGB", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		// Builds side panel
		JLabel sideLabel = new JLabel("Munsell Colors by " + sort,
				JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		// Builds main panel
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(palettePanel, BorderLayout.SOUTH);
		mainPanel.add(colorPanel);

		// if the palette is empty we still want to display 'Palette: '
		if (palettePanel.getComponents().length == 0)
		{
			buildPalettePanel(new ArrayList<>());
		}

		// Displays
		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Find Color in image screen.
	 * 
	 * @param inImage is the image that the user would like to display and find
	 *                colors in.
	 */
	public void displayFindImageColors(File inImage)
	{
		currentPage = pages[4];
		this.image = inImage;
	
		// Deals with the image
		BufferedImage img = null;
	
		try
		{
			img = ImageIO.read(image);
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Cannot read file!");
			System.exit(0);
		}
	
		int rows = img.getHeight();
		int cols = img.getWidth();
	
		// Builds the panels
		prepPanels();
	
		JPanel thisSidePanel = new JPanel();
	
		buttonPanel.setLayout(new GridLayout(3, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		thisSidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));
		JPanel[][] imagePanes = new JPanel[rows][cols];
	
		buildImageColorPanes(imagePanes, rows, cols, img);
	
		// Add titles to Grid
		addLabel("Spectrum Color Identifier", titlePanel);
		addLabel("Upload Image", titlePanel);
	
		// Add Buttons to Grid
		addButton("New Image", buttonPanel);
		addButton("Edit Image", buttonPanel);
		addButton("Posterize the Image", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);
	
		JLabel sideLabel = new JLabel("Find Color on Image", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		thisSidePanel.add(sideLabel);
		thisSidePanel.add(buttonPanel);
	
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(thisSidePanel, BorderLayout.EAST);
		mainPanel.add(palettePanel, BorderLayout.SOUTH);
		mainPanel.add(colorPanel);
	
		// if the palette is empty we still want to display 'Palette: '
		if (palettePanel.getComponents().length == 0)
		{
			buildPalettePanel(new ArrayList<>());
		}
	
		setContentPane(mainPanel);
		addWindowListener(listen);
		setSize(750, 650);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Display the posterized image.
	 * 
	 * @param palette
	 */
	public void displayPosterImage()
	{
		currentPage = pages[4];
		colorPanel.removeAll();
	
		// Deals with the image
		BufferedImage img = null;
	
		try
		{
			img = ImageIO.read(image);
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Cannot read file!");
			System.exit(0);
		}
	
		int rows = img.getHeight();
		int cols = img.getWidth();
	
		// Builds the panels
		prepPanels();
	
		JPanel thisSidePanel = new JPanel();
	
		buttonPanel.setLayout(new GridLayout(3, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		thisSidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(rows, cols));
		JPanel[][] imagePanes = new JPanel[rows][cols];
	
		buildPosterImage(imagePanes, rows, cols, img);
	
		// Add titles to Grid
		addLabel("Spectrum Color Identifier", titlePanel);
		addLabel("Upload Image", titlePanel);
	
		// Add Buttons to Grid
		addButton("New Image", buttonPanel);
		addButton("Edit Image", buttonPanel);
		addButton("Posterize the Image", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);
	
		JLabel sideLabel = new JLabel("Find Color on Image", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		thisSidePanel.add(sideLabel);
		thisSidePanel.add(buttonPanel);
	
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(thisSidePanel, BorderLayout.EAST);
		mainPanel.add(palettePanel, BorderLayout.SOUTH);
		mainPanel.add(colorPanel);
	
		// if the palette is empty we still want to display 'Palette: '
		if (palettePanel.getComponents().length == 0)
		{
			buildPalettePanel(new ArrayList<>());
		}
	
		setContentPane(mainPanel);
		addWindowListener(listen);
		setSize(750, 650);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays the Color mix screen.
	 */
	public void displayMixColors()
	{
		currentPage = pages[2];

		// Sets up the main panels
		prepPanels();
		buttonPanel.setLayout(new GridLayout(5, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(ROWH, COLH));
		bottomPanel.setLayout(new GridLayout(2, 1));
		JPanel[][] colorPanes = new JPanel[ROWH][COLH];
		buildDefaultColorPanes(colorPanes, ROWH, COLH, "Hue");

		// Set screen size
		setSize(1050, 350);

		// Add titles to Grid
		addLabel("Spectrum Color Mix", titlePanel);
		addLabel("Choose colors below", titlePanel);

		// Add Buttons to Grid
		addButton("Add to Mixer", buttonPanel);
		addButton("Mix!", buttonPanel);
		addButton("Clear Mixer", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		// Builds side panel
		JLabel sideLabel = new JLabel("Mix Munsell Colors", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		// Builds bottom panel
		bottomPanel.add(mixPanel);
		bottomPanel.add(palettePanel);

		// Builds main panel
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.add(colorPanel);

		// if the palette is empty we still want to display 'Palette: '
		if (palettePanel.getComponents().length == 0)
		{
			buildPalettePanel(new ArrayList<>());
		}
		// if the mixer is empty we still want to display 'mixer: '
		if (mixPanel.getComponents().length == 0)
		{
			buildMixPanel(new ArrayList<>());
		}

		// Displays
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
		currentPage = pages[3];

		// Builds panels
		prepPanels();
		buttonPanel.setLayout(new GridLayout(3, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(ROWH, COLH));
		JPanel[][] colorPanes = new JPanel[ROWH][COLH];
		buildDefaultColorPanes(colorPanes, ROWH, COLH, "Hue");

		// Set screen size
		setSize(1050, 350);

		// Add titles to Grid
		addLabel("Spectrum Palette Helper", titlePanel);
		addLabel("Make Colors", titlePanel);

		// Add Buttons to Grid
		addButton("Add to Palette", buttonPanel);
		addButton("Make Color", buttonPanel);
		addButton("Clear Palette", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		// Builds side label
		JLabel sideLabel = new JLabel("Palette Helper", JLabel.CENTER);
		sideLabel.setFont(new Font("Serif", Font.BOLD, 16));
		sidePanel.add(sideLabel);
		sidePanel.add(buttonPanel);

		// Builds main panels
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(sidePanel, BorderLayout.EAST);
		mainPanel.add(palettePanel, BorderLayout.SOUTH);
		mainPanel.add(colorPanel);

		// if the palette is empty we still want to display 'Palette: '
		if (palettePanel.getComponents().length == 0)
		{
			buildPalettePanel(new ArrayList<>());
		}

		// Displays
		setContentPane(mainPanel);
		addWindowListener(listen);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Displays a color used by the tester.
	 * 
	 * @param visible is true if the color is visible.
	 * @param newCol is true if a new color is needed.
	 */
	public void displayTestColor(boolean visible, boolean newCol)
	{
		// if new panel generate new random colors and Correct color
		if (newCol)
			genNewCol();

		// take colors from SColr and get name arr and COLOR arr
		Color[] cc = new Color[4];
		String[] cName = new String[4];

		for (int i = 0; i < 4; i++)
		{
			cc[i] = new Color(c[i].getRed(), c[i].getGreen(), c[i].getBlue());
			cName[i] = c[i].getName();
		}
		// GUI STUFF BOI

		currentPage = pages[6];

		prepPanels();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(4, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(4, 2));

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JLabel l1 = new JLabel();
		l1.setText(cName[0]);
		l1.setBackground(Color.WHITE);
		l1.setOpaque(true);
		p3.add(l1);

		JPanel p4 = new JPanel();
		JLabel l2 = new JLabel();
		l2.setText(cName[1]);
		l2.setBackground(Color.WHITE);
		l2.setOpaque(true);
		p4.add(l2);

		JPanel p5 = new JPanel();
		JLabel l3 = new JLabel();
		l3.setText(cName[2]);
		l3.setBackground(Color.WHITE);
		l3.setOpaque(true);
		p5.add(l3);

		JPanel p6 = new JPanel();
		JLabel l4 = new JLabel();
		l4.setText(cName[3]);
		l4.setBackground(Color.WHITE);
		l4.setOpaque(true);
		p6.add(l4);

		JPanel p7 = new JPanel();
		addButton("Check", p7);
		JPanel p8 = new JPanel();
		addButton("New Color", p8);

		// Always visible
		p1.setBackground(cc[correct]);
		p2.setBackground(cc[correct]);

		if (visible)
		{
			p3.setBackground(cc[0]);
			p4.setBackground(cc[1]);
			p5.setBackground(cc[2]);
			p6.setBackground(cc[3]);
		} else
		{
			p3.setBackground(Color.WHITE);
			p4.setBackground(Color.WHITE);
			p5.setBackground(Color.WHITE);
			p6.setBackground(Color.WHITE);
		}

		addLabel("Can you ", p1);
		addLabel("Guess this Color?", p2);

		colorPanel.add(p1);
		colorPanel.add(p2);
		colorPanel.add(p3);
		colorPanel.add(p4);
		colorPanel.add(p5);
		colorPanel.add(p6);
		colorPanel.add(p7);
		colorPanel.add(p8);

		// Add titles to Grid
		addLabel("Spectrum Color Tester", titlePanel);
		addLabel("Take Test", titlePanel);

		// Add Buttons to Grid
		addButton("Check", buttonPanel); // should set background colors to
											// visible
		addButton("New Color", buttonPanel); // refreshes page to generate new
												// color selection
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

	/**
	 * Displays the Color eye test.
	 */
	public void displayTestColor()
	{
		SColor colAr;
		List<SColor> spec = spectrum.getSpectrum();
		int size = spec.size();
		Color[] col = new Color[4];
		String[] name = new String[4];
		for (int i = 0; i < 4; i++)
		{
			// get random color
			int rand = (int) (Math.random() * size);
			colAr = spec.get(rand);
			name[i] = colAr.getName();
			col[i] = new Color(colAr.getRed(), colAr.getGreen(), colAr.getBlue());
		}

		int isCorrect = (int) Math.random() * 4;

		currentPage = pages[5];

		prepPanels();

		// set screen size
		setSize(1050, 350);

		buttonPanel.setLayout(new GridLayout(4, 1));
		titlePanel.setLayout(new GridLayout(2, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(3, 2));

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		addButton("1", p3);
		addLabel(name[0], p3);
		JPanel p4 = new JPanel();
		addLabel(name[1], p4);
		addButton("2", p4);
		JPanel p5 = new JPanel();
		addLabel(name[2], p5);
		addButton("3", p5);
		JPanel p6 = new JPanel();
		addLabel(name[3], p6);
		addButton("4", p6);

		// Always visible
		p1.setBackground(col[isCorrect]);
		p2.setBackground(col[isCorrect]);

		// set to invisible until check button is pressed
		p3.setBackground(col[0]);
		p4.setBackground(col[1]);
		p5.setBackground(col[2]);
		p6.setBackground(col[3]);

		addLabel("Color", p1);
		addLabel("Goes here", p2);

		colorPanel.add(p1);
		colorPanel.add(p2);
		colorPanel.add(p3);
		colorPanel.add(p4);
		colorPanel.add(p5);
		colorPanel.add(p6);

		// Add titles to Grid
		addLabel("Spectrum Color Tester", titlePanel);
		addLabel("Take Test", titlePanel);

		// Add Buttons to Grid
		addButton("Check", buttonPanel); // should set background colors to
											// visible
		addButton("New Color", buttonPanel); // refreshes page to generate new
												// color selection
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

	/**
	 * Displays the color that is the result of a mixture.
	 * 
	 * @param newColor is the color to display.
	 */
	public void displayMixedColor(SColor newColor)
	{

		JFrame popup = new JFrame();
		JPanel color = new JPanel();
		color.setBackground(new Color(newColor.getRed(), newColor.getGreen(),
				newColor.getBlue()));
		color.add(newColor);
		color.addMouseListener(listen);
		popup.add(color);
		popup.setSize(500, 400);
		popup.setLocationRelativeTo(null);
		popup.setVisible(true);
	}

	/**
	 * Display panel for the user to select between and view different sets of
	 * colors that are harmonious with the currently selected color.
	 * 
	 * @param color is the currently selected color.
	 */
	public void displayHarmoniousColors(SColor color)
	{
		if (color == null)
		{
			displaySelectError();
		} else
		{
			String[] colorOptions = {"Complementary Color", "Analogous Colors",
				"Triad Colors", "Tetrad Colors", "Square Colors"};

			JFrame colorFrame = new JFrame();
			JPanel container = new JPanel();
			JPanel comboPane = new JPanel();
			JPanel inset = new JPanel();
			JPanel main = new JPanel(new CardLayout());
			JLabel label = new JLabel("Complement", SwingConstants.CENTER);
			JTextField input = new JTextField("Input analog distance here");

			inset.setLayout(new GridLayout());

			container.setLayout(new BorderLayout());
			JComboBox<String> dropdown = new JComboBox<String>(colorOptions);

			// Create listener to set the color display.
			ActionListener listener = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent event)
				{
					if ("Complementary Color"
							.equals(dropdown.getSelectedItem()))
					{
						inset.removeAll();
						label.setText("Complementary");
						SColor scolor = ColorUtils.complementaryColor(color);
						Color newColor = new Color(scolor.getRed(),
								scolor.getGreen(), scolor.getBlue());
						inset.setBackground(newColor);
						inset.add(new SColor(scolor));
						inset.addMouseListener(listen);
					} else if ("Analogous Colors"
							.equals(dropdown.getSelectedItem()))
					{
						inset.removeAll();
						inset.removeMouseListener(listen);
						label.setText("Analogous");
						JPanel pane1 = new JPanel();
						JPanel pane2 = new JPanel();
						int distance;
						List<SColor> analogList = new ArrayList<>();

						try
						{
							distance = Integer.parseInt(input.getText());
							if (distance > 19 || distance < 1)
							{
								input.setText(
										"Input a whole number between 1 and 19 (inclusive)");
							} else
							{
								analogList = ColorUtils.analogousColors(color,
										distance);
							}
						} catch (NumberFormatException nfe)
						{
							input.setText(
									"Input a whole number between 1 and 19 (inclusive)");
						}

						if (analogList.size() != 0)
						{
							SColor leftColor = analogList.get(0);
							SColor rightColor = analogList.get(1);

							Color analogColor1 = new Color(leftColor.getRed(),
									leftColor.getGreen(), leftColor.getBlue());
							Color analogColor2 = new Color(rightColor.getRed(),
									rightColor.getGreen(),
									rightColor.getBlue());

							inset.setBackground(null);
							inset.add(pane1);
							inset.add(pane2);
							pane1.setBackground(analogColor1);
							pane1.add(new SColor(leftColor));
							pane1.addMouseListener(listen);
							pane2.setBackground(analogColor2);
							pane2.add(new SColor(rightColor));
							pane2.addMouseListener(listen);
						}
					} else if ("Triad Colors"
							.equals(dropdown.getSelectedItem()))
					{
						inset.removeAll();
						inset.removeMouseListener(listen);
						label.setText("Triad");
						JPanel pane1 = new JPanel();
						JPanel pane2 = new JPanel();
						List<SColor> triadList = ColorUtils.triadColors(color);
						Color triadColor1 = new Color(triadList.get(0).getRed(),
								triadList.get(0).getGreen(),
								triadList.get(0).getBlue());
						Color triadColor2 = new Color(triadList.get(1).getRed(),
								triadList.get(1).getGreen(),
								triadList.get(1).getBlue());
						inset.setBackground(null);
						inset.add(pane1);
						inset.add(pane2);
						pane1.setBackground(triadColor1);
						pane1.add(new SColor(triadList.get(0)));
						pane1.addMouseListener(listen);
						pane2.setBackground(triadColor2);
						pane2.add(new SColor(triadList.get(1)));
						pane2.addMouseListener(listen);
					} else if ("Tetrad Colors"
							.equals(dropdown.getSelectedItem()))
					{
						inset.removeAll();
						inset.removeMouseListener(listen);
						label.setText("Tetrad");
						JPanel pane1 = new JPanel();
						JPanel pane2 = new JPanel();
						JPanel pane3 = new JPanel();
						List<SColor> tetradList = ColorUtils
								.tetradColors(color);
						Color tetradColor1 = new Color(
								tetradList.get(0).getRed(),
								tetradList.get(0).getGreen(),
								tetradList.get(0).getBlue());
						Color triadColor2 = new Color(
								tetradList.get(1).getRed(),
								tetradList.get(1).getGreen(),
								tetradList.get(1).getBlue());
						Color triadColor3 = new Color(
								tetradList.get(2).getRed(),
								tetradList.get(2).getGreen(),
								tetradList.get(2).getBlue());
						inset.setBackground(null);
						inset.add(pane1);
						inset.add(pane2);
						inset.add(pane3);
						pane1.setBackground(tetradColor1);
						pane1.add(new SColor(tetradList.get(0)));
						pane1.addMouseListener(listen);
						pane2.setBackground(triadColor2);
						pane2.add(new SColor(tetradList.get(1)));
						pane2.addMouseListener(listen);
						pane3.setBackground(triadColor3);
						pane3.add(new SColor(tetradList.get(2)));
						pane3.addMouseListener(listen);
					} else
					{
						inset.removeAll();
						inset.removeMouseListener(listen);
						label.setText("Square");
						JPanel pane1 = new JPanel();
						JPanel pane2 = new JPanel();
						JPanel pane3 = new JPanel();
						List<SColor> squareList = ColorUtils
								.squareColors(color);
						Color squareColor1 = new Color(
								squareList.get(0).getRed(),
								squareList.get(0).getGreen(),
								squareList.get(0).getBlue());
						Color triadColor2 = new Color(
								squareList.get(1).getRed(),
								squareList.get(1).getGreen(),
								squareList.get(1).getBlue());
						Color triadColor3 = new Color(
								squareList.get(2).getRed(),
								squareList.get(2).getGreen(),
								squareList.get(2).getBlue());
						inset.setBackground(null);
						inset.add(pane1);
						inset.add(pane2);
						inset.add(pane3);
						pane1.setBackground(squareColor1);
						pane1.add(new SColor(squareList.get(0)));
						pane1.addMouseListener(listen);
						pane2.setBackground(triadColor2);
						pane2.add(new SColor(squareList.get(1)));
						pane2.addMouseListener(listen);
						pane3.setBackground(triadColor3);
						pane3.add(new SColor(squareList.get(2)));
						pane3.addMouseListener(listen);
					}
				}
			};

			listener.actionPerformed(null);
			dropdown.addActionListener(listener);
			comboPane.add(dropdown);
			comboPane.add(input);

			colorFrame.add(container);
			container.add(comboPane, BorderLayout.PAGE_START);
			container.add(main);
			main.add(inset, BorderLayout.CENTER);
			main.add(label, BorderLayout.LINE_END);
			colorFrame.setSize(500, 400);
			colorFrame.setLocationRelativeTo(null);
			colorFrame.setVisible(true);

			this.colorPanel.setBorder(null);
		}
	}

	/**
	 * Displays error message if color is not selected.
	 */
	public void displaySelectError()
	{
		JPanel pane = new JPanel();
		JOptionPane.showMessageDialog(pane, "Please Select Color");
	}

	/**
	 * Displays the settings panel.
	 */
	public void displaySettings()
	{
		currentPage = pages[6];

		prepPanels();

		// set screen size
		setSize(450, 450);

		buttonPanel.setLayout(new GridLayout(6, 1));
		titlePanel.setLayout(new GridLayout(1, 1));
		sidePanel.setLayout(new GridLayout(2, 1));
		colorPanel.setLayout(new GridLayout(5, 2));
		JPanel[] colorPanes = new JPanel[10];

		for (int i = 0; i < colorPanes.length; i++)
		{
			colorPanes[i] = new JPanel();
			switch (i)
			{
				case 0:
					colorPanes[i].setBackground(Color.RED);
					colorPanes[i].add(ColorUtils.closestColor(Color.RED));
					break;
				case 1:
					colorPanes[i].setBackground(Color.ORANGE);
					colorPanes[i].add(ColorUtils.closestColor(Color.ORANGE));
					break;
				case 2:
					colorPanes[i].setBackground(Color.YELLOW);
					colorPanes[i].add(ColorUtils.closestColor(Color.YELLOW));
					break;
				case 3:
					colorPanes[i].setBackground(Color.GREEN);
					colorPanes[i].add(ColorUtils.closestColor(Color.GREEN));
					break;
				case 4:
					colorPanes[i].setBackground(Color.BLUE);
					colorPanes[i].add(ColorUtils.closestColor(Color.BLUE));
					break;
				case 5:
					colorPanes[i].setBackground(Color.MAGENTA);
					colorPanes[i].add(ColorUtils.closestColor(Color.MAGENTA));
					break;
				case 6:
					colorPanes[i].setBackground(Color.PINK);
					colorPanes[i].add(ColorUtils.closestColor(Color.PINK));
					break;
				case 7:
					colorPanes[i].setBackground(Color.BLACK);
					colorPanes[i].add(ColorUtils.closestColor(Color.BLACK));
					break;
				case 8:
					colorPanes[i].setBackground(Color.GRAY);
					colorPanes[i].add(ColorUtils.closestColor(Color.GRAY));
					break;
				case 9:
					colorPanes[i].setBackground(Color.WHITE);
					colorPanes[i].add(ColorUtils.closestColor(Color.WHITE));
					break;
				default:
					break;
			}
			colorPanes[i].addMouseListener(listen);
			colorPanel.add(colorPanes[i]);
		}

		// Add titles to Grid
		addLabel("Settings", titlePanel);

		// Add Buttons to Grid
		addButton("Set Main Color", buttonPanel);
		addButton("Set Secondary Color", buttonPanel);
		addButton("Set Text Color", buttonPanel);
		addButton("Apply", buttonPanel);
		addButton("Return", buttonPanel);
		addButton("Quit", buttonPanel);

		JLabel sideLabel = new JLabel("Settings", JLabel.CENTER);
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
	 * Displays the image upload screen. Allows user to specify location of an
	 * image to upload.
	 */
	public void displayImageUpload()
	{
		JOptionPane.showMessageDialog(null,
				"Allow time for image to upload\nMax image size: 200x200 pix");

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & PNG Images", "jpg", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = chooser.getSelectedFile();
			displayFindImageColors(selectedFile);
		}

	}

	/**
	 * Builds the mixer panel.
	 * 
	 * @param mixer is a list that holds all the colors the user wishes to mix.
	 */
	public void buildMixPanel(ArrayList<SColor> mixer)
	{
		mixPanel.removeAll();
		mixPanel.setLayout(new GridBagLayout());

		JPanel mixColors = new JPanel();
		mixColors.setBackground(Color.WHITE);
		JLabel mixLabel = new JLabel("Mixer: ", JLabel.CENTER);
		GridBagConstraints col = new GridBagConstraints();

		JPanel[] panels = new JPanel[mixer.size()];

		for (int i = 0; i < mixer.size(); i++)
		{
			if (mixer.get(i) instanceof SColor)
			{
				SColor color = mixer.get(i);
				Color addColor = new Color(color.getRed(), color.getGreen(),
						color.getBlue());
				panels[i] = new JPanel();
				panels[i].setBackground(addColor);
				panels[i].add(new SColor(color));
				panels[i].addMouseListener(listen);

			}

			mixColors.add(panels[i]);

		}

		col.fill = GridBagConstraints.HORIZONTAL;
		col.gridx = 0;
		col.gridy = 0;
		col.anchor = GridBagConstraints.FIRST_LINE_START;
		mixPanel.add(mixLabel, col);
		col.fill = GridBagConstraints.HORIZONTAL;
		col.weightx = 0.5;
		col.gridwidth = 30;
		col.gridx = 2;
		col.gridy = 0;
		mixPanel.add(mixColors, col);

		displayMixColors();

	}

	/**
	 * Builds the palette panel which the user can add colors to.
	 * 
	 * @param palette is the list that holds the colors the user has added to
	 *                the palette.
	 */
	public void buildPalettePanel(ArrayList<SColor> palette)
	{

		palettePanel.removeAll();
		palettePanel.setLayout(new GridBagLayout());

		JPanel paletteColors = new JPanel();
		paletteColors.setBackground(Color.WHITE);
		JLabel paletteLabel = new JLabel("Palette:", JLabel.CENTER);
		GridBagConstraints col = new GridBagConstraints();

		JPanel[] panels = new JPanel[palette.size()];

		for (int i = 0; i < palette.size(); i++)
		{
			if (palette.get(i) instanceof SColor)
			{
				SColor color = palette.get(i);
				Color addColor = new Color(color.getRed(), color.getGreen(),
						color.getBlue());
				panels[i] = new JPanel();
				panels[i].setBackground(addColor);
				panels[i].add(new SColor(color));
				panels[i].addMouseListener(listen);

			}
			paletteColors.add(panels[i]);

		}

		col.fill = GridBagConstraints.HORIZONTAL;
		col.gridx = 0;
		col.gridy = 0;
		col.anchor = GridBagConstraints.FIRST_LINE_START;
		palettePanel.add(paletteLabel, col);
		col.fill = GridBagConstraints.HORIZONTAL;
		col.weightx = 0.5;
		col.gridwidth = 30;
		col.gridx = 2;
		col.gridy = 0;
		col.anchor = GridBagConstraints.PAGE_START;
		palettePanel.add(paletteColors, col);

		updatePalettePanel();
	}

	/**
	 * Rebuilds the image with the edits.
	 * 
	 * @param value  is the value to increment.
	 * @param chroma is the chroma to increment.
	 */
	public void editImageColorPanes(int value, int chroma)
	{
		mainPanel.remove(colorPanel);
		for (Component panel : colorPanel.getComponents())
		{
			if (panel.getClass().isInstance(new JPanel()))
			{
				SColor oldColor = (SColor) ((JPanel) panel).getComponent(0);
				String name = oldColor.getName();
				double hue = oldColor.getHue();
				int newValue = oldColor.getValue() + value;
				int newChroma = oldColor.getChroma() + chroma;

				if (newValue > 9 && !name.equals("White"))
				{
					newValue = 9;
				}
				if (newValue < 1 && !name.equals("Black"))
				{
					newValue = 1;
				}
				if (newChroma > 22)
				{
					newChroma = 22;
				}
				if (newChroma < 2 && !name.contains("V"))
				{
					newChroma = 2;
				}
				if (newValue > 10 && name.equals("White"))
				{
					newValue = 10;
				}
				if (newValue < 0 && name.equals("Black"))
				{
					newValue = 0;
				}
				if (newChroma < 0 && name.contains("V"))
				{
					newChroma = 0;
				}

				SColor newColor = getRealColor(name, hue, newValue, newChroma);
				Color background = new Color(newColor.getRed(),
						newColor.getGreen(), newColor.getBlue());
				((JPanel) panel).removeAll();
				((JPanel) panel).setBackground(background);
				((JPanel) panel).add(newColor);
			}
		}
		mainPanel.setVisible(false);
		mainPanel.add(colorPanel);
		mainPanel.setVisible(true);
	}

	/**
	 * Commits the changes made in the settings.
	 * 
	 * @param sec  is the second color.
	 * @param main is the main color.
	 * @param text is the text needed for the method.
	 */
	public void settingChanges(SColor main, SColor sec, SColor text)
	{

		Color mainCol = new Color(main.getRed(), main.getGreen(),
				main.getBlue());
		Color secCol = new Color(sec.getRed(), sec.getGreen(), sec.getBlue());

		textColor = new Color(text.getRed(), text.getGreen(), text.getBlue());

		mainPanel.setBackground(mainCol);

		titlePanel.setBackground(mainCol);

		buttonPanel.setBackground(secCol);

		mixPanel.setBackground(secCol);

		palettePanel.setBackground(secCol);

		sidePanel.setBackground(mainCol);

		bottomPanel.setBackground(mainCol);

	}

	/**
	 * generates new SColor Array (c) and new Correct Color (Correct).
	 */
	private void genNewCol()
	{
		List<SColor> spec = spectrum.getSpectrum();
		int size = spec.size();
		for (int i = 0; i < 4; i++)
		{
			int rand = (int) (Math.random() * size);
			c[i] = spec.get(rand);
		}
		correct = (int) (Math.random() * 4);
	}

	/**
	 * Updates the palette on the bottom of each page.
	 */
	private void updatePalettePanel()
	{
		if (currentPage.equals(pages[1]))
		{
			displayColors(sort);
		} else if (currentPage.equals(pages[2]))
		{
			displayMixColors();
		} else if (currentPage.equals(pages[3]))
		{
			displayColorPalette();
		} else if (currentPage.equals(pages[4]))
		{
			displayFindImageColors(image);
		}
	}

	/**
	 * Builds the new posterized image.
	 * 
	 * @param colorPanes is the matrix of jpanels that make up the image.
	 * @param rows       is the number of rows in the matrix.
	 * @param cols       is the number of columns in the matrix.
	 * @param img is the image to use to build the panes.
	 */
	private void buildPosterImage(JPanel[][] colorPanes, int rows, int cols, BufferedImage img)
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				colorPanes[i][j] = new JPanel();
				colorPanel.add(colorPanes[i][j]);
				Color imgColor = new Color(img.getRGB(j, i));
				SColor posterColor = ColorUtils.pclosestColor(imgColor);
				Color posterRGB = new Color(posterColor.getRed(), posterColor.getGreen(), 
						posterColor.getBlue());
				colorPanes[i][j].add(posterColor);
				colorPanes[i][j].setBackground(posterRGB);
				colorPanes[i][j].addMouseListener(listen);
			}
		}
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
				Color imgColor = new Color(img.getRGB(j, i));
				colorPanes[i][j].add(ColorUtils.closestColor(imgColor));
				colorPanes[i][j].setBackground(imgColor);
				colorPanes[i][j].addMouseListener(listen);
			}
		}
	}

	/**
	 * Gets the nearest real color to the given color specified by Name, Hue,
	 * Val, Chroma.
	 * 
	 * @param name   is the name of the given color.
	 * @param hue    is the hue of the given color.
	 * @param value  is the value of the given color.
	 * @param chroma is the chroma of the given color.
	 * @return the nearest real color.
	 */
	private SColor getRealColor(String name, double hue, int value, int chroma)
	{
		SColor compColor = new SColor(name, hue, value, chroma, 0, 0, 0);
		SColor retColor = null;

		for (SColor color : spectrum.getSpectrum())
		{
			if (color.equals(compColor))
			{
				retColor = new SColor(color);
			}
		}

		if (retColor == null)
		{
			return getRealColor(name, hue, value, chroma - 2);
		} else
		{
			return retColor;
		}
	}

	/**
	 * Builds the colorPanels to display the colors as a default when the 2D
	 * display is opened.
	 * 
	 * @param colorPanes is the array of color panes that we will use to display
	 *                   colors.
	 * @param rows       is the number of rows in our color display.
	 * @param cols       is the number of columns in our color display.
	 * @param inSort     is the way this list should be sorted.
	 */
	private void buildDefaultColorPanes(JPanel[][] colorPanes, int rows,
			int cols, String inSort)
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
		switch (inSort)
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
		String[] nameIDs = {"R", "YR", "Y", "GY", "G", "BG", "B", "PB", "P",
			"RP"};
		double hue = 2.5;
		int value = 5;
		int chroma = 0;
		int startCol = 2;
		int row = 11;
		int id = 0;

		for (int col = startCol; col < cols; col++)
		{
			chroma = 2;
			row = 11;

			if (hue > 10.0)
			{
				hue = 2.5;
				id++;
			}

			for (SColor color : spectrum.getSpectrum())
			{

				if (row == 0)
				{
					break;
				}

				String name = color.getName();
				boolean goodName = false;
				char ch = '*';
				if (id % 2 == 0)
				{
					goodName = name.substring(name.length() - 1)
							.equals(nameIDs[id]);
					ch = name.substring(name.length() - 2).charAt(0);
				} else if (id % 2 == 1)
				{
					goodName = name.substring(name.length() - 2)
							.equals(nameIDs[id]);
				}

				if (color.getHue() == hue && color.getChroma() == chroma
						&& color.getValue() == value && goodName
						&& (ch == '*' || ch == '0' || ch == '5'))
				{
					Color addColor = new Color(color.getRed(), color.getGreen(),
							color.getBlue());
					colorPanes[row][col].setBackground(addColor);
					colorPanes[row][col].add(color);
					colorPanes[row][col].addMouseListener(listen);
					chroma += 2;
					row--;

				}
			}

			JLabel label = new JLabel();
			label.setText("" + col);
			colorPanes[0][col].add(label);

			hue += 2.5;

		}

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
		String[] nameIDs = {"R", "YR", "Y", "GY", "G", "BG", "B", "PB", "P",
			"RP"};
		double hue = 2.5;
		int value = 1;
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
	 * Adds a button to the container.
	 * 
	 * @param text      to be added
	 * @param container to add button in
	 */
	private void addButton(String text, JPanel container)
	{
		JButton button = new JButton(text);
		container.add(button);
		button.setBackground(secondaryColor);
		button.addActionListener(listen);
	}

	/**
	 * Adds a button to the container.
	 * 
	 * @param text      to be added
	 * @param container to add button in
	 */
	private void addLabel(String text, JPanel container)
	{
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(textColor);
		container.add(label);
	}

	/**
	 * Clears all the global panels.
	 */
	private void prepPanels()
	{

		mainPanel.removeAll();
		titlePanel.removeAll();
		buttonPanel.removeAll();
		colorPanel.removeAll();
		bottomPanel.removeAll();
		sidePanel.removeAll();
		listen = ViewListener.getInstance();

	}

	/********************** Static Methods ********************/

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
