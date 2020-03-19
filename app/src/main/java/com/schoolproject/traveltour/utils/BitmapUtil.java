package com.schoolproject.traveltour.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {

    public static String bitmapToBase64String(Bitmap bm) {
        if (bm == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT).replaceAll("\n", "");
    }

    public static Bitmap base64StringToBitmap(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }

        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
