package com.happy.birthday;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;


/**
 * Created by Hayk Movsisyan on 1/03/2020.
 *
 *
 * Encodes data using LSB(least significant bit)  steganography algorithm
 */
public class Util {

	public static String image_path(String path, String name, String ext) {
		return path + "/" + name + "." + ext;
	}

	public static BufferedImage getImage(String f) {
		BufferedImage image = null;
		File file = new File(f);

		try {
			image = ImageIO.read(file);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					"Image could not be read!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	public static BufferedImage user_space(BufferedImage image) {
		BufferedImage new_img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose();
		return new_img;
	}

	public static byte[] get_byte_data(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}
}
