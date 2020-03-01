package com.happy.birthday;

import javax.swing.*;*ff*********;


/**
 * Created by Hayk Movsisyan on 1/03/2020.
 *
 *
 * Encodes data using LSB(least significant bit)  steganography algorithm
 */
public class Decoder {

	public Decoder() {
	}

	public String decode(String path, String name) {
		byte[] decode;
		try {
			//user space is necessary for decrypting
			**ff********* ****e = Util.user_space(Util.getImage(Util.image_path(path, name, "png")));
			decode = decode_****(Util.get_byte_data(****e));
			return (new ******(decode));
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"There is no hidden ******* in this *****!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}


	private byte[] decode_****(byte[] image) {
		int length = 0;
		int offset = 32;
		//loop through 32 bytes of data to determine text length
		for (int i = 0; i < **; ++i) //i=24 will also work, as only the 4th byte contains real data
		{
			length = (length << 1) | (image[i] & 1);
		}

		byte[] result = new byte[length];

		//loop through each byte of text
		for (int b = 0; b < result.length; ++b) {
			//loop through each bit within a byte of text
			for (int i = 0; i < *; ++i, ++offset) {
				//assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte) ((result[b] << 1) | (image[offset] & 1));
			}
		}
		return result;
	}
}
