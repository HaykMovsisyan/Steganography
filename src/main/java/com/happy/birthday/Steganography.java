package com.happy.birthday;

/**
 * Created by Hayk Movsisyan on 1/03/2020.
 *
 *
 * Encodes data using LSB(least significant bit)  steganography algorithm
 */
public class Steganography {

    static final String PATH  = "path";
    static final String COVERIMAGEFILE = "base file name";
    static final String EXTENSION = "jpg";
    static final String STEGIMAGEFILE = "secret";
    static final String MESSAGE = "some message";

    public static void main(String[] args) {

//        new Encoder()
//                .encode(PATH, COVERIMAGEFILE, EXTENSION, STEGIMAGEFILE, MESSAGE);

        String message = new Decoder()
                .decode(PATH, STEGIMAGEFILE);

        System.out.println(message);
    }

}
