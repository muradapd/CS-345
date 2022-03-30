package Controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.ColorWheel;
import Model.SColor;
import Model.SColorSpectrum;
import View.ViewHandler;

/**
 * Utility class for determining harmonious colors. (Complementary color, triad
 * colors, triad colors, tetrad colors, and square colors)
 * 
 */
public class ColorUtils
{
	private static SColorSpectrum spectrum = new SColorSpectrum();
	private static ViewHandler palette = ViewHandler.getInstance();

	/**
	 * Finds the closest color for the posterize function.
	 * 
	 * @param image is the incoming color.
	 * @return the SColor that is closest to the incoming color.
	 */
	public static SColor pclosestColor(Color image)
	{ // could this be implemented with binary search?
		SColor pclosestColor = null;
		double closestDistance = Integer.MAX_VALUE;

		for (SColor color : palette.getPalette())
		{
			double red = Math.pow(image.getRed() - color.getRed(), 2);
			double green = Math.pow(image.getGreen() - color.getGreen(), 2);
			double blue = Math.pow(image.getBlue() - color.getBlue(), 2);
			double result = red + green + blue;
			if (closestDistance > result)
			{
				closestDistance = result;
				pclosestColor = color;
			}
		}
		return new SColor(pclosestColor);
	}

	/**
	 * Finds analogous colors given a color and the distance around the circle
	 * the user wants to find the analogs.
	 * 
	 * @param color    is the color to find analogs for.
	 * @param distance is the distance around the circle the user wants.
	 * @return the two analogous colors.
	 */
	public static List<SColor> analogousColors(SColor color, int distance)
	{
		List<SColor> analogColors = new ArrayList<>();
		int colorIndex = ColorWheel.indexOf(color.getName());
		String[] names = ColorWheel.getLeftRight(colorIndex, distance);
		int leftIndex = spectrum.getSpectrum().indexOf(new SColor(names[0],
				Double.parseDouble(names[0].substring(0, 3)), color.getValue(),
				color.getChroma(), 0, 0, 0));
		int rightIndex = spectrum.getSpectrum()
				.indexOf(new SColor(names[1],
						Double.parseDouble(names[1].substring(0, 3)),
						color.getValue(), color.getChroma(), 0, 0, 0));
		SColor leftColor = spectrum.getSpectrum().get(leftIndex);
		SColor rightColor = spectrum.getSpectrum().get(rightIndex);
		analogColors.add(leftColor);
		analogColors.add(rightColor);

		return analogColors;
	}

	/**
	 * Finds the SColor that is closest in euclidean distance (in the RGB color
	 * space) to the given java Color object.
	 * 
	 * @param imgColor is the java Color to find a close SColor match for.
	 * @return the SColor that is most similar to the given java color object.
	 */
	public static SColor closestColor(Color imgColor)
	{ // could this be implemented with binary search?
		SColor closestColor = null;
		double closestDistance = Integer.MAX_VALUE;
	
		for (SColor color : spectrum.getSpectrum())
		{
			double red = Math.pow(imgColor.getRed() - color.getRed(), 2);
			double green = Math.pow(imgColor.getGreen() - color.getGreen(), 2);
			double blue = Math.pow(imgColor.getBlue() - color.getBlue(), 2);
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
	 * Determines the complementary color of the given SColor object.
	 * 
	 * @param color Color to get complement for
	 * @return color complement
	 */
	public static SColor complementaryColor(SColor color)
	{
		int colorIndex = ColorWheel.indexOf(color.getName());
		String complementName = ColorWheel.getOpposite(colorIndex);

		int complementIndex = spectrum.getSpectrum()
				.indexOf(new SColor(complementName, color.getHue(),
						color.getValue(), color.getChroma(), 0, 0, 0));
		SColor complement = spectrum.getSpectrum().get(complementIndex);

		return complement;
	}

	/**
	 * Mixes two colors together and returns the resulting color.
	 * 
	 * @param color1       is the first color to mix.
	 * @param color2       is the second color to mix.
	 * @param angleToColor is the map used to get the color name given the hue
	 *                     angle.
	 * @param nameToAngle  is the map used to get the angle of a color given its
	 *                     name.
	 * @return the new color resulting from the mixture.
	 */
	public static SColor mixTwoColors(SColor color1, SColor color2,
			HashMap<Integer, String> angleToColor,
			HashMap<String, Integer> nameToAngle)
	{
		double[] c1Coordinates = getCoordinates(color1, nameToAngle);
		double[] c2Coordinates = getCoordinates(color2, nameToAngle);
		int[] midpoint = midpoint(c1Coordinates[0], c1Coordinates[1],
				c2Coordinates[0], c2Coordinates[1]);
		int x2 = midpoint[0];
		int y2 = midpoint[1];
		// Use the midpoint to calculate the angle, value and chroma for the new
		// color.
		int newAngle;
		if (x2 == 0 && y2 == 0) 
		{
			newAngle = -1;
		} else if (x2 == 0 && y2 > 0)
		{
			newAngle = 90;
		}
		else if (x2 == 0 && y2 < 0)
		{
			newAngle = 270;
		} else
		{
			newAngle =  (int) Math.toDegrees(Math.atan(y2 / x2));
		}
		if (x2 < 0)
		{
			newAngle += 180;
		}
		if (newAngle < 0 && newAngle != -1) 
		{
			newAngle = 360 + newAngle;
		}
		
		// set up the new value and chroma
		int newValue = Math.abs(color1.getValue() + color2.getValue()) / 2;
		int newChroma = (int) Math
				.sqrt((Math.pow(x2, 2) + Math.pow(y2, 2)));
		// fix the new chroma to get nearest even chroma
		if (newChroma % 2 != 0)
		{
			newChroma++;
		}
		// fix new angle to approximate nearest real angle in the hashmap
		int nearestAngle;
		if (newAngle < 9 && newAngle > 5)
		{
			nearestAngle = 9;
		} else if (newAngle < 9 && newAngle < 5)
		{
			nearestAngle = 0;
		} else
		{
			int mod = newAngle % 9;
			if (mod < 4) 
			{
				nearestAngle = newAngle + mod;
			} else 
			{
				nearestAngle = newAngle - mod;
			}
		}
		if (newAngle == -1)
		{
			nearestAngle = -1;
		}
		// get the name from the angle
		String name = angleToColor.get(nearestAngle);
		if (name == null && newValue == 0)
		{
			name = "Black";
			newChroma = 0;
		} else if (name == null && newValue == 10)
		{
			name = "White";
			newChroma = 0;
		} else if (name == null)
		{
			name = "V" + newValue;
			newChroma = 0;
		}
		// get the hue from the name
		double newHue;
		try
		{
			newHue = Double.parseDouble(name.substring(0, 3));
		} catch (NumberFormatException nfe)
		{
			newHue = 10.0;
		} catch (StringIndexOutOfBoundsException e)
		{
			newHue = 10.0;
		}
		// get the nearest real munsell color calculated from the mix
		int index = spectrum.getSpectrum().indexOf(
				new SColor(name, newHue, newValue, newChroma, 0, 0, 0));

		SColor newColor = spectrum.getSpectrum().get(index);
		return newColor;
	}

	/**
	 * Determines all square colors for the given SColor object.
	 * 
	 * @param color Color to get square colors for
	 * @return list of square color pairs
	 */
	public static List<SColor> squareColors(SColor color)
	{
		List<SColor> squareColors = new ArrayList<SColor>();
		int colorIndex = ColorWheel.indexOf(color.getName());
		String[] names = ColorWheel.getLeftRight(colorIndex, 10);
		int rightIndex = spectrum.getSpectrum()
				.indexOf(new SColor(names[1],
						Double.parseDouble(names[1].substring(0, 3)),
						color.getValue(), color.getChroma(), 0, 0, 0));
		SColor rightColor = spectrum.getSpectrum().get(rightIndex);
		SColor complement1 = complementaryColor(color);
		SColor complement2 = complementaryColor(rightColor);

		squareColors.add(rightColor);
		squareColors.add(complement1);
		squareColors.add(complement2);

		return squareColors;
	}

	/**
	 * Determines all tetrad colors for the given SColor object.
	 * 
	 * @param color Color to get tetrad colors for
	 * @return list of tetrad color pairs
	 */
	public static List<SColor> tetradColors(SColor color)
	{
		List<SColor> tetradColors = new ArrayList<SColor>();
		int colorIndex = ColorWheel.indexOf(color.getName());
		String[] names = ColorWheel.getLeftRight(colorIndex, 6);
		int rightIndex = spectrum.getSpectrum()
				.indexOf(new SColor(names[1],
						Double.parseDouble(names[1].substring(0, 3)),
						color.getValue(), color.getChroma(), 0, 0, 0));
		SColor rightColor = spectrum.getSpectrum().get(rightIndex);
		SColor complement1 = complementaryColor(color);
		SColor complement2 = complementaryColor(rightColor);

		tetradColors.add(rightColor);
		tetradColors.add(complement1);
		tetradColors.add(complement2);

		return tetradColors;
	}

	/**
	 * Determines all triad colors for the given SColor object.
	 * 
	 * @param color Color to get triad colors for
	 * @return list of triad color pairs
	 */
	public static List<SColor> triadColors(SColor color)
	{
		List<SColor> triadColors = new ArrayList<SColor>();
		int colorIndex = ColorWheel.indexOf(color.getName());
		String[] names = ColorWheel.getLeftRight(colorIndex, 13);
		int leftIndex = spectrum.getSpectrum()
				.indexOf(new SColor(names[0],
						Double.parseDouble(names[0].substring(0, 3)),
						color.getValue(), color.getChroma(), 0, 0, 0));
		int rightIndex = spectrum.getSpectrum()
				.indexOf(new SColor(names[1],
						Double.parseDouble(names[1].substring(0, 3)),
						color.getValue(), color.getChroma(), 0, 0, 0));
		SColor leftColor = spectrum.getSpectrum().get(leftIndex);
		SColor rightColor = spectrum.getSpectrum().get(rightIndex);
		triadColors.add(leftColor);
		triadColors.add(rightColor);

		return triadColors;
	}

	/**
	 * Calculate the x,y coordinates for the given color.
	 * 
	 * @param color       is the color to get x, y coords for.
	 * @param nameToAngle is the map to get the angle from.
	 * @return x,y coordinates for the given color.
	 */
	private static double[] getCoordinates(SColor color,
			HashMap<String, Integer> nameToAngle)
	{
		if (color.getName().equals("Black") || color.getName().equals("White")
				|| color.getName().equals("V" + color.getValue()))
		{
			return new double[] {0.0, 0.0};
		}
		double angle = nameToAngle.get(color.getName());
		angle = Math.toRadians(angle);
		double x = Math.round((color.getChroma() * Math.cos(angle)));
		double y = Math.round((color.getChroma() * Math.sin(angle)));
		double[] values = {x, y};

		return values;
	}

	/**
	 * Calculate the midpoint of the given points.
	 * 
	 * @param x1 x value for point 1
	 * @param y1 y value for point 1
	 * @param x2 x value for point 2
	 * @param y2 y value for point 2
	 * @return midpoint of the given points
	 */
	private static int[] midpoint(double x1, double y1, double x2, double y2)
	{
		int[] midpoint = new int[2];
		midpoint[0] = (int) Math.round((x1 + x2) / 2);
		midpoint[1] = (int) Math.round((y1 + y2) / 2);
		return midpoint;
	}
}
