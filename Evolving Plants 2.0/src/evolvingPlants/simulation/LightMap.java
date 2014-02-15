package evolvingPlants.simulation;

import java.awt.image.BufferedImage;

import tools.ColTools;

public class LightMap
	{
		private int[][] lightData;
		private int baseLight = 0;
		
		BufferedImage lightMap = new BufferedImage(800, 550, BufferedImage.TYPE_INT_RGB);

		private int width, height;

		public LightMap(int width, int height)
			{
				this.width = width;
				this.height = height;

				lightData = new int[width][height];

				for (int x = 0; x < width; x++)
					for (int y = 0; y < height; y++)
							lightData[x][y] = baseLight;
			}

		public void setLight(double newLightValue)
			{
				if (newLightValue == baseLight)
					return;
				
				int difference = (int) newLightValue - baseLight;

				for (int x = 0; x < width; x++)
					for (int y = 0; y < height; y++)
						lightData[x][y] += difference;

				baseLight = (int) newLightValue;
			}

		/**
		 * 
		 * @param shadowX
		 * @param shadowY
		 * @param shadowWidth
		 * @param leafOpacity - How much light the leaf casting the shadow blocks, 0 being none, 255 being all
		 */
		public final void addShadow(int shadowX, int shadowY, int shadowWidth, int leafOpacity)
			{
				if (shadowX + shadowWidth > width)
					shadowWidth = width - shadowX;

				for (int x = Math.max(0, shadowX); x < shadowX + shadowWidth; x++)
					for (int y = Math.max(0, shadowY); y < height; y++)
							lightData[x][y] -= leafOpacity;
			}

		/**
		 * 
		 * @param shadowX
		 * @param shadowY
		 * @param shadowWidth
		 * @param leafOpacity - How much light the leaf casting the shadow blocked, 0 being none, 255 being all
		 */
		public final void removeShadow(int shadowX, int shadowY, int shadowWidth, int leafOpacity)
			{
				if (shadowX + shadowWidth > width)
					shadowWidth = width - shadowX;

				for (int x = Math.max(0, shadowX); x < shadowX + shadowWidth; x++)
					for (int y = Math.max(0, shadowY); y < height; y++)
							lightData[x][y] += leafOpacity;
			}

		public final BufferedImage getLightImage(BufferedImage image, int xPos)
			{
				for (int x = xPos; x < xPos + image.getWidth() && x < width; x++)
					for (int y = 0; y < image.getHeight(); y++)
						image.setRGB(x - xPos, y, ColTools.checkColour(lightData[x][y], lightData[x][y], lightData[x][y]).getRGB());

				return image;
			}

		/**
		 * 
		 * @param x
		 * @param y
		 * @param leafOpacity - How much light the leaf casting the shadow is blocking, 0 being none, 255 being all
		 * @return
		 */
		public int getLightMinusShadowAt(int x, int y, int leafOpacity)
			{
				if (x > 0 && x < width && y > 0 && y < height)
					{
						return lightData[x][y] + leafOpacity;
					}

				return 0;
			}
	}