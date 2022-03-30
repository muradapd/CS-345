package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to simulate the Munsell color wheel of unique hue names.
 * 
 * @author Patrick Muradaz
 *
 */
public class ColorWheel
{
	private static String[] colors = {"R", "YR", "Y", "GY", "G", "BG", "B",
		"PB", "P", "RP"};
	private static SColorSpectrum spectrum = new SColorSpectrum();
	private static List<String> colorWheel = buildColorWheel();

	/**
	 * Builds the color wheel for use by this class.
	 * 
	 * @return the built color wheel.
	 */
	private static List<String> buildColorWheel()
	{
		List<String> retWheel = new ArrayList<>();
		double hue = 2.5;
		int i = 0;

		for (SColor uniqueColor : spectrum.getSpectrum())
		{
			if (uniqueColor.getName().equals("" + hue + colors[i]))
			{
				retWheel.add(uniqueColor.getName());
				if (hue == 10)
				{
					i++;
				}
				if (hue == 10)
				{
					hue = 0;
				}
				if (i == 10)
				{
					break;
				}
				hue += 2.5;
			}
		}
		return retWheel;
	}

	/**
	 * Gets the name of the hue opposite the index supplied.
	 * 
	 * @param index is the index of the current color.
	 * @return the string name of the hue of the color opposite the given index.
	 */
	public static String getOpposite(int index)
	{
		return colorWheel.get((index + 20) % 40);
	}

	/**
	 * Gets the colors to the left and right of the supplied color at a given
	 * distance around the color wheel.
	 * 
	 * @param index    is the index of the current color.
	 * @param distance is the distance around the color wheel to go to find the
	 *                 left and right colors.
	 * @return the left and right colors in a list.
	 */
	public static String[] getLeftRight(int index, int distance)
	{
		String[] names = new String[2];
		int leftIndex = index;
		int distCount = distance;

		while (distCount > 0)
		{
			leftIndex--;
			if (leftIndex == -1)
			{
				leftIndex = 39;
			}
			distCount--;
		}

		String leftName = colorWheel.get(leftIndex);
		int test = (index + distance) % 40;
		String rightName = colorWheel.get(test);

		names[0] = leftName;
		names[1] = rightName;
		return names;
	}

	/**
	 * Gets the index of the current color when supplied that color's name.
	 * 
	 * @param name is the name of the current color.
	 * @return the index of the color who's name was supplied.
	 */
	public static int indexOf(String name)
	{
		return colorWheel.indexOf(name);
	}
}
