package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import Controller.ColorUtils;
import Model.SColor;
import Model.SColorSpectrum;
import View.ViewHandler;

/**
 * Test class for our program. All testing is handled here.
 * 
 * @author Patrick Muradaz
 */
public class SpectrumTester
{
	SColor testColor = new SColor("2.5R", 2.5, 1, 2, 45, 21, 31);
	SColorSpectrum testSpectrum = new SColorSpectrum();
	HashMap<String, Integer> colorToAngle = new HashMap<>();
	HashMap<Integer, String> angleToColor = new HashMap<>();

	/**
	 * Builds the maps used to test the color mixing method.
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
			colorToAngle.put(colors.get(i), angleValue);
			angleToColor.put(angleValue, colors.get(i));
			angleValue += 9;
		}
	}

	/** ==================== Unit tests for SColor. ==================== */
	@Test
	public void testSColorGetters()
	{
		assertEquals("2.5R", testColor.getName());
		assertEquals(2.5, testColor.getHue());
		assertEquals(1, testColor.getValue());
		assertEquals(2, testColor.getChroma());
		assertEquals(45, testColor.getRed());
		assertEquals(21, testColor.getGreen());
		assertEquals(31, testColor.getBlue());
	}

	/**
	 * Tests the SColor equals method.
	 */
	@Test
	public void testSColorEquals()
	{
		SColor testDifferent = new SColor("2.5R", 2.5, 2, 2, 66, 43, 50);
		SColor testSame = new SColor("2.5R", 2.5, 1, 2, 45, 21, 31);
		SColor testCopy = new SColor(testColor);
		assertTrue(testColor.equals(testSame));
		assertFalse(testColor.equals(testDifferent));
		assertTrue(testColor.equals(testCopy));
	}

	/**
	 * Tests the SColor toString method.
	 */
	@Test
	public void testSColorToString()
	{
		String colorString = "2.5R 1/2";
		assertEquals(testColor.toString(), colorString);
	}

	/** ================ Unit tests for SColorSpectrum. ================ */

	/**
	 * Tests SColorSpectrum size method.
	 */
	@Test
	public void testSColorSpectrum()
	{
		assertEquals(2745, testSpectrum.size());
	}

	/**
	 * Tests ColorUtils analogousColors method.
	 */
	@Test
	public void testAnalogousColors()
	{
		SColor testRed = new SColor("2.5R", 2.5, 1, 2, 66, 43, 50);
		SColor testYellowRed = new SColor("2.5YR", 2.5, 1, 2, 66, 43, 50);
		SColor testYellow = new SColor("2.5Y", 2.5, 1, 2, 66, 43, 50);
		SColor testGreenYellow = new SColor("2.5GY", 2.5, 1, 2, 66, 43, 50);
		SColor testGreen = new SColor("2.5G", 2.5, 1, 2, 66, 43, 50);
		SColor testBlueGreen = new SColor("2.5BG", 2.5, 1, 2, 66, 43, 50);
		SColor testBlue = new SColor("2.5B", 2.5, 1, 2, 66, 43, 50);
		SColor testPurpleBlue = new SColor("2.5PB", 2.5, 1, 2, 66, 43, 50);
		SColor testPurple = new SColor("2.5P", 2.5, 1, 2, 66, 43, 50);
		SColor testRedPurple = new SColor("2.5RP", 2.5, 1, 2, 66, 43, 50);

		assertEquals("10.0RP",
				ColorUtils.analogousColors(testRed, 1).get(0).getName());
		assertEquals("5.0R",
				ColorUtils.analogousColors(testYellowRed, 3).get(0).getName());
		assertEquals("10.0R",
				ColorUtils.analogousColors(testYellow, 5).get(0).getName());
		assertEquals("5.0YR", ColorUtils.analogousColors(testGreenYellow, 7)
				.get(0).getName());
		assertEquals("10.0YR",
				ColorUtils.analogousColors(testGreen, 9).get(0).getName());
		assertEquals("5.0Y",
				ColorUtils.analogousColors(testBlueGreen, 11).get(0).getName());
		assertEquals("10.0Y",
				ColorUtils.analogousColors(testBlue, 13).get(0).getName());
		assertEquals("5.0GY", ColorUtils.analogousColors(testPurpleBlue, 15)
				.get(0).getName());
		assertEquals("10.0GY",
				ColorUtils.analogousColors(testPurple, 17).get(0).getName());
		assertEquals("5.0G",
				ColorUtils.analogousColors(testRedPurple, 19).get(0).getName());
	}

	/** ================== Unit tests for ColorUtils. ================== */

	/**
	 * Tests ColorUtils closestColor method.
	 */
	@Test
	public void testClosestColor()
	{
		Color inColor = new Color(230, 220, 245);
		SColor outColor = ColorUtils.closestColor(inColor);
		assertEquals("10.0PB", outColor.getName());
	}
	
	/**
	 * Tests ColorUtils pClosestColor method.
	 */
	@Test
	public void testPclosestColor()
	{
		ViewHandler palette = ViewHandler.getInstance();
		Color inColor = new Color(230, 220, 245);
		palette.addPalette(new SColor("Black", 0, 0, 0, 0, 0, 0));
		SColor outColor = ColorUtils.pclosestColor(inColor);
		assertEquals("Black", outColor.getName());
	}

	/**
	 * Tests ColorUtils complementaryColors method.
	 */
	@Test
	public void testComplementaryColors()
	{
		SColor testRed = new SColor("2.5R", 2.5, 1, 2, 66, 43, 50);
		SColor testYellowRed = new SColor("2.5YR", 2.5, 1, 2, 66, 43, 50);
		SColor testYellow = new SColor("2.5Y", 2.5, 1, 2, 66, 43, 50);
		SColor testGreenYellow = new SColor("2.5GY", 2.5, 1, 2, 66, 43, 50);
		SColor testGreen = new SColor("2.5G", 2.5, 1, 2, 66, 43, 50);
		SColor testBlueGreen = new SColor("2.5BG", 2.5, 1, 2, 66, 43, 50);
		SColor testBlue = new SColor("2.5B", 2.5, 1, 2, 66, 43, 50);
		SColor testPurpleBlue = new SColor("2.5PB", 2.5, 1, 2, 66, 43, 50);
		SColor testPurple = new SColor("2.5P", 2.5, 1, 2, 66, 43, 50);
		SColor testRedPurple = new SColor("2.5RP", 2.5, 1, 2, 66, 43, 50);

		assertEquals(testBlueGreen, ColorUtils.complementaryColor(testRed));
		assertEquals(testBlue, ColorUtils.complementaryColor(testYellowRed));
		assertEquals(testPurpleBlue, ColorUtils.complementaryColor(testYellow));
		assertEquals(testPurple,
				ColorUtils.complementaryColor(testGreenYellow));
		assertEquals(testRedPurple, ColorUtils.complementaryColor(testGreen));
		assertEquals(testRed, ColorUtils.complementaryColor(testBlueGreen));
		assertEquals(testYellowRed, ColorUtils.complementaryColor(testBlue));
		assertEquals(testYellow, ColorUtils.complementaryColor(testPurpleBlue));
		assertEquals(testGreenYellow,
				ColorUtils.complementaryColor(testPurple));
		assertEquals(testGreen, ColorUtils.complementaryColor(testRedPurple));
	}

	/**
	 * Tests ColorUtils squareColors method.
	 */
	@Test
	public void testSquareColors()
	{
		SColor testRed = new SColor("5.0R", 5.0, 1, 2, 66, 43, 50);
		SColor testYellow = new SColor("10.0Y", 10.0, 1, 2, 66, 43, 50);
		SColor testBlueGreen = new SColor("5.0BG", 5.0, 1, 2, 66, 43, 50);
		SColor testPurpleBlue = new SColor("10.0PB", 10.0, 1, 2, 66, 43, 50);

		assertEquals(testYellow, ColorUtils.squareColors(testRed).get(0));
		assertEquals(testBlueGreen, ColorUtils.squareColors(testRed).get(1));
		assertEquals(testPurpleBlue, ColorUtils.squareColors(testRed).get(2));
	}

	/**
	 * Tests the color mixing functionality.
	 */
	@Test
	public void testMixColors()
	{
		buildMaps();
		SColor mixRed = new SColor("2.5R", 2.5, 1, 2, 66, 43, 50);
		SColor mixYellow = new SColor("2.5Y", 2.5, 1, 2, 66, 43, 50);
		SColor mixBlueGreen = new SColor("2.5BG", 2.5, 1, 2, 66, 43, 50);
		SColor mixOutput1 = new SColor("2.5YR", 2.5, 1, 2, 0, 0, 0);
		SColor mixOutput2 = new SColor("V1", 10.0, 1, 0, 37, 28, 9);
				
		assertEquals(mixOutput1, ColorUtils.mixTwoColors(mixRed, mixYellow,
				angleToColor, colorToAngle));
		assertEquals(mixOutput2, ColorUtils.mixTwoColors(mixRed, mixBlueGreen,
				angleToColor, colorToAngle));
	}

	/**
	 * Tests ColorUtils tetradColors method.
	 */
	@Test
	public void testTetradColors()
	{
		SColor testRed = new SColor("5.0R", 5.0, 1, 2, 66, 43, 50);
		SColor testYellowRed = new SColor("10.0YR", 10.0, 1, 2, 66, 43, 50);
		SColor testBlue = new SColor("10.0B", 10.0, 1, 2, 66, 43, 50);
		SColor testBlueGreen = new SColor("5.0BG", 5.0, 1, 2, 66, 43, 50);

		assertEquals(testYellowRed, ColorUtils.tetradColors(testRed).get(0));
		assertEquals(testBlueGreen, ColorUtils.tetradColors(testRed).get(1));
		assertEquals(testBlue, ColorUtils.tetradColors(testRed).get(2));
	}

	/**
	 * Tests ColorUtils triadColors method.
	 */
	@Test
	public void testTriadColors()
	{
		SColor testRed = new SColor("2.5R", 2.5, 1, 2, 66, 43, 50);
		SColor testYellowRed = new SColor("2.5YR", 2.5, 1, 2, 66, 43, 50);
		SColor testYellow = new SColor("2.5Y", 2.5, 1, 2, 66, 43, 50);
		SColor testGreenYellow = new SColor("2.5GY", 2.5, 1, 2, 66, 43, 50);
		SColor testGreen = new SColor("2.5G", 2.5, 1, 2, 66, 43, 50);
		SColor testBlueGreen = new SColor("2.5BG", 2.5, 1, 2, 66, 43, 50);
		SColor testBlue = new SColor("2.5B", 2.5, 1, 2, 66, 43, 50);
		SColor testPurpleBlue = new SColor("2.5PB", 2.5, 1, 2, 66, 43, 50);
		SColor testPurple = new SColor("2.5P", 2.5, 1, 2, 66, 43, 50);
		SColor testRedPurple = new SColor("2.5RP", 2.5, 1, 2, 66, 43, 50);

		assertEquals("5.0GY", ColorUtils.triadColors(testRed).get(1).getName());
		assertEquals("5.0G",
				ColorUtils.triadColors(testYellowRed).get(1).getName());
		assertEquals("5.0BG",
				ColorUtils.triadColors(testYellow).get(1).getName());
		assertEquals("5.0B",
				ColorUtils.triadColors(testGreenYellow).get(1).getName());
		assertEquals("5.0PB",
				ColorUtils.triadColors(testGreen).get(1).getName());
		assertEquals("5.0P",
				ColorUtils.triadColors(testBlueGreen).get(1).getName());
		assertEquals("5.0RP",
				ColorUtils.triadColors(testBlue).get(1).getName());
		assertEquals("5.0R",
				ColorUtils.triadColors(testPurpleBlue).get(1).getName());
		assertEquals("5.0YR",
				ColorUtils.triadColors(testPurple).get(1).getName());
		assertEquals("5.0Y",
				ColorUtils.triadColors(testRedPurple).get(1).getName());
	}
}
