/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.provider.DocumentsContract
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Audio
 *  android.provider.MediaStore$Audio$Media
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Video
 *  android.provider.MediaStore$Video$Media
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.helper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class ImageFilePath {
    public static String getDataColumn(Context context, Uri uri, String string2, String[] arrstring) {
        Cursor cursor;
        block5 : {
            cursor = null;
            String[] arrstring2 = new String[]{"_data"};
            cursor = context.getContentResolver().query(uri, arrstring2, string2, arrstring, null);
            if (cursor == null) break block5;
            if (!cursor.moveToFirst()) break block5;
            String string3 = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string3;
        }
        return null;
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String getPath(Context context, Uri uri) {
        String string2;
        boolean bl = Build.VERSION.SDK_INT >= 19;
        if (bl && DocumentsContract.isDocumentUri((Context)context, (Uri)uri)) {
            Uri uri2;
            if (ImageFilePath.isExternalStorageDocument(uri)) {
                String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
                boolean bl2 = "primary".equalsIgnoreCase(arrstring[0]);
                string2 = null;
                if (!bl2) return string2;
                return (Object)Environment.getExternalStorageDirectory() + "/" + arrstring[1];
            }
            if (ImageFilePath.isDownloadsDocument(uri)) {
                String string3 = DocumentsContract.getDocumentId((Uri)uri);
                return ImageFilePath.getDataColumn(context, ContentUris.withAppendedId((Uri)Uri.parse((String)"content://downloads/public_downloads"), (long)Long.valueOf((String)string3)), null, null);
            }
            boolean bl3 = ImageFilePath.isMediaDocument(uri);
            string2 = null;
            if (!bl3) return string2;
            String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
            String string4 = arrstring[0];
            if ("image".equals((Object)string4)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals((Object)string4)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                boolean bl4 = "audio".equals((Object)string4);
                uri2 = null;
                if (bl4) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
            }
            String[] arrstring2 = new String[]{arrstring[1]};
            return ImageFilePath.getDataColumn(context, uri2, "_id=?", arrstring2);
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (!ImageFilePath.isGooglePhotosUri(uri)) return ImageFilePath.getDataColumn(context, uri, null, null);
            return uri.getLastPathSegment();
        }
        boolean bl5 = "file".equalsIgnoreCase(uri.getScheme());
        string2 = null;
        if (!bl5) return string2;
        return uri.getPath();
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals((Object)uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals((Object)uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals((Object)uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals((Object)uri.getAuthority());
    }
}

