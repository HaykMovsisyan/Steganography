package com.happy.birthday;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Hayk Movsisyan on 1/03/2020.
 *
 *
 * Encodes data using LSB(least significant bit)  steganography algorithm
 */
public class Encoder {

    public Encoder() {
    }

    public boolean encode(String path, String original, String ext1, String stegan, String message) {
        String file_name = Util.image_path(path, original, ext1);
        BufferedImage image_orig = Util.getImage(file_name);

        //user space is not necessary for Encrypting
        BufferedImage image = Util.user_space(image_orig);
        image = add_text(image, message);

        return (setImage(image, new File(Util.image_path(path, stegan, "png")), "png"));
    }

    private boolean setImage(BufferedImage image, File file, String ext) {
        try {
            file.delete(); //delete resources used by the File
            ImageIO.write(image, ext, file);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "File could not be saved!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private BufferedImage add_text(BufferedImage image, String text) {
        //convert all items to byte arrays: image, message, message length
        byte img[] = Util.get_byte_data(image);
        byte msg[] = text.getBytes();
        byte len[] = bit_conversion(msg.length);
        try {
            encode_text(img, len, 0); //0 first positiong
            encode_text(img, msg, 32); //4 bytes of space for length: 4bytes*8bit = 32 bits
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Target File cannot hold message!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return image;
    }


    private byte[] bit_conversion(int i) {
        //originally integers (ints) cast into bytes
        //byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
        //byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
        //byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
        //byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);

        //only using 4 bytes
        byte byte3 = (byte) ((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte) ((i & 0x00FF0000) >>> 16); //0
        byte byte1 = (byte) ((i & 0x0000FF00) >>> 8); //0
        byte byte0 = (byte) ((i & 0x000000FF));
        //{0,0,0,byte0} is equivalent, since all shifts >=8 will be 0
        return (new byte[] {byte3, byte2, byte1, byte0});
    }

    private byte[] encode_text(byte[] image, byte[] addition, int offset) {
        //check that the data + offset will fit in the image
        if (addition.length + offset > image.length) {
            throw new IllegalArgumentException("File not long enough!");
        }
        //loop through each addition byte
        for (int i = 0; i < addition.length; ++i) {
            //loop through the 8 bits of each byte
            int add = addition[i];
            for (int bit = 7; bit >= 0; --bit, ++offset) //ensure the new offset value carries on through both loops
            {
                //assign an integer to b, shifted by bit spaces AND 1
                //a single bit of the current byte
                int b = (add >>> bit) & 1;
                //assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
                //changes the last bit of the byte in the image to be the bit of addition
                image[offset] = (byte) ((image[offset] & 0xFE) | b);
            }
        }
        return image;
    }
}
