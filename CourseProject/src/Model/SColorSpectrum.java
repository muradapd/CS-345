package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a container for SColor objects. The class holds the objects in
 * a list.
 * 
 * @author Patrick Muradaz
 * @version 10/23/19
 */
public class SColorSpectrum
{
	private List<SColor> spectrum;

	/**
	 * Constructor. Initializes spectrum as an ArrayList<SColor> and populates
	 * the list by reading colors from a .csv file containing all possible
	 * colors (for our purposed).
	 */
	public SColorSpectrum()
	{
		spectrum = new ArrayList<SColor>();
		populate();
	}

	/**
	 * Populates the color spectrum by reading colors from a local .csv file.
	 */
	private void populate()
	{
		InputStream csvStream = SColorSpectrum.class
				.getResourceAsStream("SColors.csv");

		BufferedReader csvReader = null;
		String row = null;

		csvReader = new BufferedReader(new InputStreamReader(csvStream)); // initialize
																			// reader
		try
		{ // try/catch for parsing the file
			while ((row = csvReader.readLine()) != null)
			{ // parse the file
				String[] params = row.split(",");
				String name = params[0];
				double hue = Double.parseDouble(params[1]);
				int value = Integer.parseInt(params[2]);
				int chroma = Integer.parseInt(params[3]);
				int red = Integer.parseInt(params[4]);
				int green = Integer.parseInt(params[5]);
				int blue = Integer.parseInt(params[6]);
				spectrum.add(
						new SColor(name, hue, value, chroma, red, green, blue));
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{ // try/catch for close file
			csvReader.close(); // close file
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Getter for this spectrum object.
	 * 
	 * @return spectrum.
	 */
	public List<SColor> getSpectrum()
	{
		return this.spectrum;
	}

	/** ==================== TEST METHOD ==================== */

	/**
	 * Getter for the number of unique colors in the spectrum.
	 * 
	 * @return the number of colors in the HashMap.
	 */
	public int size()
	{
		return spectrum.size();
	}
}
