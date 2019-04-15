/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.util.Iterator
 */
package org.jf.dexlib.Code.Format;

import java.util.Iterator;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class ArrayDataPseudoInstruction
extends Instruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private int elementWidth;
    private byte[] encodedValues;

    public ArrayDataPseudoInstruction(int n, byte[] arrby) {
        super(Opcode.NOP);
        if (arrby.length % n != 0) {
            throw new RuntimeException("There are not a whole number of " + n + " byte elements");
        }
        this.elementWidth = n;
        this.encodedValues = arrby;
    }

    public ArrayDataPseudoInstruction(byte[] arrby, int n) {
        super(Opcode.NOP);
        if (arrby[n] != 0) {
            throw new RuntimeException("Invalid opcode byte for an ArrayData pseudo-instruction");
        }
        if (arrby[n + 1] != 3) {
            throw new RuntimeException("Invalid sub-opcode byte for an ArrayData pseudo-instruction");
        }
        this.elementWidth = NumberUtils.decodeUnsignedShort(arrby, n + 2);
        int n2 = NumberUtils.decodeInt(arrby, n + 4);
        this.encodedValues = new byte[n2 * this.elementWidth];
        System.arraycopy((Object)arrby, (int)(n + 8), (Object)this.encodedValues, (int)0, (int)(n2 * this.elementWidth));
    }

    @Override
    protected void annotateInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.annotate(2 * this.getSize(n), "[0x" + Integer.toHexString((int)n) + "] " + "fill-array-data instruction");
    }

    public int getElementCount() {
        return this.encodedValues.length / this.elementWidth;
    }

    public int getElementWidth() {
        return this.elementWidth;
    }

    public Iterator<ArrayElement> getElements() {
        return new Iterator<ArrayElement>(){
            final ArrayElement arrayElement;
            final int elementCount;
            int i;
            int position;
            {
                this.elementCount = ArrayDataPseudoInstruction.this.getElementCount();
                this.i = 0;
                this.position = 0;
                this.arrayElement = new ArrayElement(ArrayDataPseudoInstruction.this.encodedValues, ArrayDataPseudoInstruction.this.getElementWidth());
            }

            public boolean hasNext() {
                return this.i < this.elementCount;
            }

            public ArrayElement next() {
                this.arrayElement.bufferIndex = this.position;
                this.position += this.arrayElement.elementWidth;
                this.i = 1 + this.i;
                return this.arrayElement;
            }

            public void remove() {
            }
        };
    }

    @Override
    public Format getFormat() {
        return Format.ArrayData;
    }

    @Override
    public int getSize(int n) {
        return 4 + (1 + this.encodedValues.length) / 2 + n % 2;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.alignTo(4);
        int n2 = this.encodedValues.length / this.elementWidth;
        annotatedOutput.writeByte(0);
        annotatedOutput.writeByte(3);
        annotatedOutput.writeShort(this.elementWidth);
        annotatedOutput.writeInt(n2);
        annotatedOutput.write(this.encodedValues);
        annotatedOutput.alignTo(2);
    }

    public static class ArrayElement {
        public final byte[] buffer;
        public int bufferIndex;
        public final int elementWidth;

        public ArrayElement(byte[] arrby, int n) {
            this.buffer = arrby;
            this.elementWidth = n;
        }
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            if (opcode != Opcode.NOP) {
                throw new RuntimeException("The opcode for an ArrayDataPseudoInstruction must be NOP");
            }
            return new ArrayDataPseudoInstruction(arrby, n);
        }
    }

}

