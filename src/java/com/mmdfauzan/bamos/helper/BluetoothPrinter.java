/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothSocket
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  android.os.ParcelUuid
 *  com.mmdfauzan.bamos.helper.BluetoothPrinter$1
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Void
 *  java.lang.reflect.Method
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.List
 *  java.util.UUID
 */
package com.mmdfauzan.bamos.helper;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.ParcelUuid;
import com.mmdfauzan.bamos.helper.BluetoothPrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BluetoothPrinter {
    public static final int ALIGN_CENTER = 100;
    public static final int ALIGN_LEFT = 102;
    public static final int ALIGN_RIGHT = 101;
    private static final byte[] ESC_ALIGN_CENTER;
    private static final byte[] ESC_ALIGN_LEFT;
    private static final byte[] ESC_ALIGN_RIGHT;
    private static final byte[] NEW_LINE;
    private static String[] binaryArray;
    private static String hexStr;
    private OutputStream btOutputStream = null;
    private BluetoothSocket btSocket = null;
    private BluetoothDevice printer;

    static {
        NEW_LINE = new byte[]{10};
        ESC_ALIGN_CENTER = new byte[]{27, 97, 1};
        ESC_ALIGN_RIGHT = new byte[]{27, 97, 2};
        ESC_ALIGN_LEFT = new byte[]{27, 97, 0};
        hexStr = "0123456789ABCDEF";
        binaryArray = new String[]{"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
    }

    public BluetoothPrinter(BluetoothDevice bluetoothDevice) {
        this.printer = bluetoothDevice;
    }

    static /* synthetic */ BluetoothSocket access$002(BluetoothPrinter bluetoothPrinter, BluetoothSocket bluetoothSocket) {
        bluetoothPrinter.btSocket = bluetoothSocket;
        return bluetoothSocket;
    }

    static /* synthetic */ OutputStream access$102(BluetoothPrinter bluetoothPrinter, OutputStream outputStream) {
        bluetoothPrinter.btOutputStream = outputStream;
        return outputStream;
    }

    private static List<String> binaryListToHexStringList(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String string2 : list) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < string2.length(); i += 8) {
                stringBuilder.append(BluetoothPrinter.strToHexString(string2.substring(i, i + 8)));
            }
            arrayList.add((Object)stringBuilder.toString());
        }
        return arrayList;
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf((int)c);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] decodeBitmap(Bitmap bitmap) {
        int n = bitmap.getWidth();
        int n2 = bitmap.getHeight();
        ArrayList arrayList = new ArrayList();
        int n3 = n % 8;
        String string2 = "";
        if (n3 > 0) {
            for (int i = 0; i < 8 - n3; ++i) {
                string2 = string2 + "0";
            }
        }
        int n4 = 0;
        do {
            StringBuffer stringBuffer;
            if (n4 < n2) {
                stringBuffer = new StringBuffer();
            } else {
                List<String> list = BluetoothPrinter.binaryListToHexStringList((List<String>)arrayList);
                int n5 = n % 8 == 0 ? n / 8 : 1 + n / 8;
                String string3 = Integer.toHexString((int)n5);
                if (string3.length() > 2) {
                    return null;
                }
                if (string3.length() == 1) {
                    string3 = "0" + string3;
                }
                String string4 = string3 + "00";
                String string5 = Integer.toHexString((int)n2);
                if (string5.length() > 2) {
                    return null;
                }
                if (string5.length() == 1) {
                    string5 = "0" + string5;
                }
                String string6 = string5 + "00";
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add((Object)("1D763000" + string4 + string6));
                arrayList2.addAll(list);
                return BluetoothPrinter.hexList2Byte((List<String>)arrayList2);
            }
            for (int i = 0; i < n; ++i) {
                int n6 = bitmap.getPixel(i, n4);
                int n7 = 255 & n6 >> 16;
                int n8 = 255 & n6 >> 8;
                int n9 = n6 & 255;
                if (n7 > 160 && n8 > 160 && n9 > 160) {
                    stringBuffer.append("0");
                    continue;
                }
                stringBuffer.append("1");
            }
            if (n3 > 0) {
                stringBuffer.append(string2);
            }
            arrayList.add((Object)stringBuffer.toString());
            ++n4;
        } while (true);
    }

    private static String encodeNonAscii(String string2) {
        return string2.replace('\u00e1', 'a').replace('\u010d', 'c').replace('\u010f', 'd').replace('\u00e9', 'e').replace('\u011b', 'e').replace('\u00ed', 'i').replace('\u0148', 'n').replace('\u00f3', 'o').replace('\u0159', 'r').replace('\u0161', 's').replace('\u0165', 't').replace('\u00fa', 'u').replace('\u016f', 'u').replace('\u00fd', 'y').replace('\u017e', 'z').replace('\u00c1', 'A').replace('\u010c', 'C').replace('\u010e', 'D').replace('\u00c9', 'E').replace('\u011a', 'E').replace('\u00cd', 'I').replace('\u0147', 'N').replace('\u00d3', 'O').replace('\u0158', 'R').replace('\u0160', 'S').replace('\u0164', 'T').replace('\u00da', 'U').replace('\u016e', 'U').replace('\u00dd', 'Y').replace('\u017d', 'Z');
    }

    private static byte[] hexList2Byte(List<String> list) {
        ArrayList arrayList = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            arrayList.add((Object)BluetoothPrinter.hexStringToBytes((String)iterator.next()));
        }
        return BluetoothPrinter.sysCopy((List<byte[]>)arrayList);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static byte[] hexStringToBytes(String string2) {
        if (string2 == null) return null;
        if (string2.equals((Object)"")) {
            return null;
        }
        String string3 = string2.toUpperCase();
        int n = string3.length() / 2;
        char[] arrc = string3.toCharArray();
        byte[] arrby = new byte[n];
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2 * 2;
            arrby[n2] = (byte)(BluetoothPrinter.charToByte(arrc[n3]) << 4 | BluetoothPrinter.charToByte(arrc[n3 + 1]));
            ++n2;
        }
        return arrby;
    }

    private static String strToHexString(String string2) {
        String string3 = "";
        String string4 = string2.substring(0, 4);
        String string5 = string2.substring(4, 8);
        for (int i = 0; i < binaryArray.length; ++i) {
            if (!string4.equals((Object)binaryArray[i])) continue;
            string3 = string3 + hexStr.substring(i, i + 1);
        }
        for (int i = 0; i < binaryArray.length; ++i) {
            if (!string5.equals((Object)binaryArray[i])) continue;
            string3 = string3 + hexStr.substring(i, i + 1);
        }
        return string3;
    }

    private static byte[] sysCopy(List<byte[]> list) {
        int n = 0;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            n += ((byte[])iterator.next()).length;
        }
        byte[] arrby = new byte[n];
        int n2 = 0;
        for (byte[] arrby2 : list) {
            System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)arrby2.length);
            n2 += arrby2.length;
        }
        return arrby;
    }

    public boolean addNewLine() {
        return this.printUnicode(NEW_LINE);
    }

    public int addNewLines(int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (!this.addNewLine()) continue;
            ++n2;
        }
        return n2;
    }

    public void connectPrinter(PrinterConnectListener printerConnectListener) {
        ConnectTask connectTask = new ConnectTask((ConnectTask.BtConnectListener)new 1((BluetoothPrinter)this, printerConnectListener), null);
        Object[] arrobject = new BluetoothDevice[]{this.printer};
        connectTask.execute(arrobject);
    }

    public void feedPaper() {
        this.addNewLine();
        this.addNewLine();
        this.addNewLine();
        this.addNewLine();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        if (this.btSocket != null) {
            try {
                this.btOutputStream.close();
                this.btSocket.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            this.btSocket = null;
        }
    }

    public BluetoothDevice getDevice() {
        return this.printer;
    }

    public BluetoothSocket getSocket() {
        return this.btSocket;
    }

    public boolean isConnected() {
        return this.btSocket != null && this.btSocket.isConnected();
    }

    public boolean printImage(Bitmap bitmap) {
        return this.printUnicode(BluetoothPrinter.decodeBitmap(bitmap));
    }

    public boolean printLine() {
        return this.printText("________________________________");
    }

    public boolean printText(String string2) {
        try {
            this.btOutputStream.write(BluetoothPrinter.encodeNonAscii(string2).getBytes());
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public boolean printUnicode(byte[] arrby) {
        try {
            this.btOutputStream.write(arrby);
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAlign(int n) {
        byte[] arrby;
        switch (n) {
            default: {
                arrby = ESC_ALIGN_LEFT;
                break;
            }
            case 100: {
                arrby = ESC_ALIGN_CENTER;
                break;
            }
            case 102: {
                arrby = ESC_ALIGN_LEFT;
                break;
            }
            case 101: {
                arrby = ESC_ALIGN_RIGHT;
            }
        }
        try {
            this.btOutputStream.write(arrby);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setBold(boolean bl) {
        int n = 1;
        byte[] arrby = new byte[3];
        arrby[0] = 27;
        arrby[n] = 69;
        if (!bl) {
            n = 0;
        }
        arrby[2] = n;
        this.printUnicode(arrby);
    }

    public void setLineSpacing(int n) {
        byte[] arrby = new byte[]{27, 51, (byte)n};
        this.printUnicode(arrby);
    }

    private static class ConnectTask
    extends AsyncTask<BluetoothDevice, Void, BluetoothSocket> {
        private BtConnectListener listener;

        private ConnectTask(BtConnectListener btConnectListener) {
            this.listener = btConnectListener;
        }

        /* synthetic */ ConnectTask(BtConnectListener btConnectListener, 1 var2_2) {
            super(btConnectListener);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected /* varargs */ BluetoothSocket doInBackground(BluetoothDevice ... arrbluetoothDevice) {
            BluetoothSocket bluetoothSocket;
            BluetoothDevice bluetoothDevice = arrbluetoothDevice[0];
            UUID uUID = bluetoothDevice.getUuids()[0].getUuid();
            boolean bl = true;
            try {
                BluetoothSocket bluetoothSocket2;
                bluetoothSocket = bluetoothSocket2 = bluetoothDevice.createRfcommSocketToServiceRecord(uUID);
            }
            catch (IOException iOException) {
                bluetoothSocket = null;
            }
            try {
                bluetoothSocket.connect();
            }
            catch (IOException iOException) {
                try {
                    Class class_ = bluetoothDevice.getClass();
                    Class[] arrclass = new Class[]{Integer.TYPE};
                    Method method = class_.getMethod("createRfcommSocket", arrclass);
                    Object[] arrobject = new Object[]{1};
                    bluetoothSocket = (BluetoothSocket)method.invoke((Object)bluetoothDevice, arrobject);
                    bluetoothSocket.connect();
                }
                catch (Exception exception) {
                    return null;
                }
            }
            if (!bl) return null;
            return bluetoothSocket;
        }

        protected void onPostExecute(BluetoothSocket bluetoothSocket) {
            block3 : {
                block2 : {
                    if (this.listener == null) break block2;
                    if (bluetoothSocket == null) break block3;
                    this.listener.onConnected(bluetoothSocket);
                }
                return;
            }
            this.listener.onFailed();
        }

        private static interface BtConnectListener {
            public void onConnected(BluetoothSocket var1);

            public void onFailed();
        }

    }

    public static interface PrinterConnectListener {
        public void onConnected();

        public void onFailed();
    }

}

