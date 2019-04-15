/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package org.jf.dexlib.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib.DebugInfoItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.ByteArrayOutput;
import org.jf.dexlib.Util.Output;

public class DebugInfoBuilder {
    private static final int FIRST_SPECIAL = 10;
    private static final int LINE_BASE = -4;
    private static final int LINE_RANGE = 15;
    private int currentAddress;
    private int currentLine;
    private ArrayList<Event> events = new ArrayList();
    private boolean hasData;
    private int lastAddress = 0;
    private int lineStart = 0;
    private ArrayList<String> parameterNames = new ArrayList();

    public static byte calculateSpecialOpcode(int n, int n2) {
        return (byte)(10 + n2 * 15 + (n + 4));
    }

    private void checkAddress(int n) {
        if (this.lastAddress > n) {
            throw new RuntimeException("Cannot add an event with an address before the address of the prior event");
        }
    }

    private void emitAdvanceLine(Output output, int n) {
        output.writeByte(2);
        output.writeSignedLeb128(n);
    }

    private void emitAdvancePC(Output output, int n) {
        int n2 = n - this.currentAddress;
        if (n2 > 0) {
            output.writeByte(1);
            output.writeUnsignedLeb128(n2);
            this.currentAddress = n;
        }
    }

    private void emitEndLocal(Output output, int n) {
        output.writeByte(5);
        output.writeUnsignedLeb128(n);
    }

    private void emitEndSequence(Output output) {
        output.writeByte(0);
    }

    private void emitRestartLocal(Output output, int n) {
        output.writeByte(6);
        output.writeUnsignedLeb128(n);
    }

    private void emitSetEpilogueBegin(Output output) {
        output.writeByte(8);
    }

    private void emitSetFile(Output output) {
        output.writeByte(9);
        output.writeByte(1);
    }

    private void emitSetPrologueEnd(Output output) {
        output.writeByte(7);
    }

    private void emitSpecialOpcode(Output output, byte by) {
        output.writeByte(by);
    }

    private void emitStartLocal(Output output, int n) {
        output.writeByte(3);
        output.writeUnsignedLeb128(n);
        output.writeByte(1);
        output.writeByte(1);
    }

    private void emitStartLocalExtended(Output output, int n) {
        output.writeByte(4);
        output.writeUnsignedLeb128(n);
        output.writeByte(1);
        output.writeByte(1);
        output.writeByte(1);
    }

    public void addEndLocal(int n, int n2) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        this.events.add((Object)(DebugInfoBuilder)this.new EndLocalEvent(n, n2));
    }

    public void addEpilogue(int n) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        this.events.add((Object)(DebugInfoBuilder)this.new EpilogueEvent(n));
    }

    public void addLine(int n, int n2) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        if (this.lineStart == 0) {
            this.lineStart = n2;
        }
        this.events.add((Object)(DebugInfoBuilder)this.new LineEvent(n, n2));
    }

    public void addLocal(int n, int n2, String string2, String string3) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        this.events.add((Object)(DebugInfoBuilder)this.new StartLocalEvent(n, n2, string2, string3));
    }

    public void addLocalExtended(int n, int n2, String string2, String string3, String string4) {
        this.hasData = true;
        this.checkAddress(n);
        this.events.add((Object)new StartLocalExtendedEvent(n, n2, string2, string3, string4));
    }

    public void addParameterName(String string2) {
        if (string2 != null) {
            this.hasData = true;
        }
        this.parameterNames.add((Object)string2);
    }

    public void addPrologue(int n) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        this.events.add((Object)(DebugInfoBuilder)this.new PrologueEvent(n));
    }

    public void addRestartLocal(int n, int n2) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        this.events.add((Object)(DebugInfoBuilder)this.new RestartLocalEvent(n, n2));
    }

    public void addSetFile(int n, String string2) {
        this.hasData = true;
        DebugInfoBuilder.super.checkAddress(n);
        this.events.add((Object)(DebugInfoBuilder)this.new SetFileEvent(n, string2));
    }

    public DebugInfoItem encodeDebugInfo(DexFile dexFile) {
        if (!this.hasData) {
            return null;
        }
        ByteArrayOutput byteArrayOutput = new ByteArrayOutput();
        StringIdItem[] arrstringIdItem = new StringIdItem[this.parameterNames.size()];
        ArrayList arrayList = new ArrayList();
        if (this.lineStart == 0) {
            this.lineStart = 1;
        }
        this.currentLine = this.lineStart;
        Iterator iterator = this.events.iterator();
        while (iterator.hasNext()) {
            ((Event)iterator.next()).emit(dexFile, byteArrayOutput, (List<Item>)arrayList);
        }
        DebugInfoBuilder.super.emitEndSequence(byteArrayOutput);
        int n = 0;
        for (String string2 : this.parameterNames) {
            if (string2 == null) {
                int n2 = n + 1;
                arrstringIdItem[n] = null;
                n = n2;
                continue;
            }
            int n3 = n + 1;
            arrstringIdItem[n] = StringIdItem.internStringIdItem(dexFile, string2);
            n = n3;
        }
        Object[] arrobject = new Item[arrayList.size()];
        arrayList.toArray(arrobject);
        return DebugInfoItem.internDebugInfoItem(dexFile, this.lineStart, arrstringIdItem, byteArrayOutput.toByteArray(), (Item[])arrobject);
    }

    public int getParameterNameCount() {
        return this.parameterNames.size();
    }

    private class EndLocalEvent
    implements Event {
        private final int address;
        private final int registerNum;

        public EndLocalEvent(int n, int n2) {
            this.address = n;
            this.registerNum = n2;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitEndLocal(output, this.registerNum);
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private class EpilogueEvent
    implements Event {
        private final int address;

        public EpilogueEvent(int n) {
            this.address = n;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitSetEpilogueBegin(output);
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private static interface Event {
        public void emit(DexFile var1, Output var2, List<Item> var3);

        public int getAddress();
    }

    private class LineEvent
    implements Event {
        private final int address;
        private final int line;

        public LineEvent(int n, int n2) {
            this.address = n;
            this.line = n2;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            int n = this.line - DebugInfoBuilder.this.currentLine;
            int n2 = this.address - DebugInfoBuilder.this.currentAddress;
            if (n < -4 || n > 10) {
                DebugInfoBuilder.this.emitAdvanceLine(output, n);
                n = 0;
            }
            if (n < 2 && n2 > 16 || n > 1 && n2 > 15) {
                DebugInfoBuilder.this.emitAdvancePC(output, this.address);
                n2 = 0;
            }
            DebugInfoBuilder.this.emitSpecialOpcode(output, DebugInfoBuilder.calculateSpecialOpcode(n, n2));
            DebugInfoBuilder.this.currentAddress = this.address;
            DebugInfoBuilder.this.currentLine = this.line;
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private class PrologueEvent
    implements Event {
        private final int address;

        public PrologueEvent(int n) {
            this.address = n;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitSetPrologueEnd(output);
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private class RestartLocalEvent
    implements Event {
        private final int address;
        private final int registerNum;

        public RestartLocalEvent(int n, int n2) {
            this.address = n;
            this.registerNum = n2;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitRestartLocal(output, this.registerNum);
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private class SetFileEvent
    implements Event {
        private final int address;
        private final String fileName;

        public SetFileEvent(int n, String string2) {
            this.address = n;
            this.fileName = string2;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitSetFile(output);
            if (this.fileName != null) {
                list.add((Object)StringIdItem.internStringIdItem(dexFile, this.fileName));
            }
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private class StartLocalEvent
    implements Event {
        private final int address;
        private final String localName;
        private final String localType;
        private final int registerNum;

        public StartLocalEvent(int n, int n2, String string2, String string3) {
            this.address = n;
            this.registerNum = n2;
            this.localName = string2;
            this.localType = string3;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitStartLocal(output, this.registerNum);
            StringIdItem stringIdItem = this.localName == null ? null : StringIdItem.internStringIdItem(dexFile, this.localName);
            list.add(stringIdItem);
            String string2 = this.localType;
            TypeIdItem typeIdItem = null;
            if (string2 != null) {
                typeIdItem = TypeIdItem.internTypeIdItem(dexFile, StringIdItem.internStringIdItem(dexFile, this.localType));
            }
            list.add(typeIdItem);
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

    private class StartLocalExtendedEvent
    implements Event {
        private final int address;
        private final String localName;
        private final String localType;
        private final int registerNum;
        private final String signature;

        public StartLocalExtendedEvent(int n, int n2, String string2, String string3, String string4) {
            this.address = n;
            this.registerNum = n2;
            this.localName = string2;
            this.localType = string3;
            this.signature = string4;
        }

        @Override
        public void emit(DexFile dexFile, Output output, List<Item> list) {
            DebugInfoBuilder.this.emitAdvancePC(output, this.address);
            DebugInfoBuilder.this.emitStartLocalExtended(output, this.registerNum);
            if (this.localName != null) {
                list.add((Object)StringIdItem.internStringIdItem(dexFile, this.localName));
            }
            if (this.localType != null) {
                list.add((Object)TypeIdItem.internTypeIdItem(dexFile, StringIdItem.internStringIdItem(dexFile, this.localType)));
            }
            if (this.signature != null) {
                list.add((Object)StringIdItem.internStringIdItem(dexFile, this.signature));
            }
        }

        @Override
        public int getAddress() {
            return this.address;
        }
    }

}

