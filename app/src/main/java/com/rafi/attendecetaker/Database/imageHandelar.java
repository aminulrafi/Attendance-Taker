package com.rafi.attendecetaker.Database;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class imageHandelar {

    ByteArrayOutputStream byteArrayOutputStream;
    byte[] imageByte;

    public byte[] databaseImage(Bitmap image) {

        Bitmap imageStore = image;
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageByte = byteArrayOutputStream.toByteArray();
        return imageByte;
    }
}
