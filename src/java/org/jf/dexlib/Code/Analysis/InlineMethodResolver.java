/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code.Analysis;

import org.jf.dexlib.Code.Analysis.AnalyzedInstruction;
import org.jf.dexlib.Code.Analysis.DeodexUtil;
import org.jf.dexlib.Code.Format.Instruction35ms;
import org.jf.dexlib.Code.Format.Instruction3rms;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.OdexedInvokeVirtual;

abstract class InlineMethodResolver {
    private InlineMethodResolver() {
    }

    /* synthetic */ InlineMethodResolver(1 var1) {
    }

    public static InlineMethodResolver createInlineMethodResolver(DeodexUtil deodexUtil, int n) {
        if (n == 35) {
            return new InlineMethodResolver_version35(deodexUtil);
        }
        if (n == 36) {
            return new InlineMethodResolver_version36(deodexUtil);
        }
        Object[] arrobject = new Object[]{n};
        throw new RuntimeException(String.format((String)"odex version %d is not supported yet", (Object[])arrobject));
    }

    public abstract DeodexUtil.InlineMethod resolveExecuteInline(AnalyzedInstruction var1);

    private static class InlineMethodResolver_version35
    extends InlineMethodResolver {
        static final /* synthetic */ boolean $assertionsDisabled;
        private final DeodexUtil.InlineMethod[] inlineMethods;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !InlineMethodResolver.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        public InlineMethodResolver_version35(DeodexUtil deodexUtil) {
            super(null);
            DeodexUtil.InlineMethod[] arrinlineMethod = new DeodexUtil.InlineMethod[14];
            deodexUtil.getClass();
            arrinlineMethod[0] = deodexUtil.new DeodexUtil.InlineMethod(2, "Lorg/apache/harmony/dalvik/NativeTestTarget;", "emptyInlineMethod", "", "V");
            deodexUtil.getClass();
            arrinlineMethod[1] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "charAt", "I", "C");
            deodexUtil.getClass();
            arrinlineMethod[2] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "compareTo", "Ljava/lang/String;", "I");
            deodexUtil.getClass();
            arrinlineMethod[3] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "equals", "Ljava/lang/Object;", "Z");
            deodexUtil.getClass();
            arrinlineMethod[4] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "length", "", "I");
            deodexUtil.getClass();
            arrinlineMethod[5] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "I", "I");
            deodexUtil.getClass();
            arrinlineMethod[6] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "J", "J");
            deodexUtil.getClass();
            arrinlineMethod[7] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "F", "F");
            deodexUtil.getClass();
            arrinlineMethod[8] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[9] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "min", "II", "I");
            deodexUtil.getClass();
            arrinlineMethod[10] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "max", "II", "I");
            deodexUtil.getClass();
            arrinlineMethod[11] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "sqrt", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[12] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "cos", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[13] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "sin", "D", "D");
            this.inlineMethods = arrinlineMethod;
        }

        @Override
        public DeodexUtil.InlineMethod resolveExecuteInline(AnalyzedInstruction analyzedInstruction) {
            if (!$assertionsDisabled && !(analyzedInstruction.instruction instanceof OdexedInvokeVirtual)) {
                throw new AssertionError();
            }
            int n = ((OdexedInvokeVirtual)((Object)analyzedInstruction.instruction)).getMethodIndex();
            if (n < 0 || n >= this.inlineMethods.length) {
                throw new RuntimeException("Invalid method index: " + n);
            }
            return this.inlineMethods[n];
        }
    }

    private static class InlineMethodResolver_version36
    extends InlineMethodResolver {
        static final /* synthetic */ boolean $assertionsDisabled;
        private final DeodexUtil.InlineMethod fastIndexOfMethod;
        private final DeodexUtil.InlineMethod indexOfIIMethod;
        private final DeodexUtil.InlineMethod indexOfIMethod;
        private final DeodexUtil.InlineMethod[] inlineMethods;
        private final DeodexUtil.InlineMethod isEmptyMethod;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !InlineMethodResolver.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        public InlineMethodResolver_version36(DeodexUtil deodexUtil) {
            super(null);
            deodexUtil.getClass();
            this.indexOfIMethod = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "indexOf", "I", "I");
            deodexUtil.getClass();
            this.indexOfIIMethod = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "indexOf", "II", "I");
            deodexUtil.getClass();
            this.fastIndexOfMethod = deodexUtil.new DeodexUtil.InlineMethod(1, "Ljava/lang/String;", "fastIndexOf", "II", "I");
            deodexUtil.getClass();
            this.isEmptyMethod = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "isEmpty", "", "Z");
            DeodexUtil.InlineMethod[] arrinlineMethod = new DeodexUtil.InlineMethod[22];
            deodexUtil.getClass();
            arrinlineMethod[0] = deodexUtil.new DeodexUtil.InlineMethod(2, "Lorg/apache/harmony/dalvik/NativeTestTarget;", "emptyInlineMethod", "", "V");
            deodexUtil.getClass();
            arrinlineMethod[1] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "charAt", "I", "C");
            deodexUtil.getClass();
            arrinlineMethod[2] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "compareTo", "Ljava/lang/String;", "I");
            deodexUtil.getClass();
            arrinlineMethod[3] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "equals", "Ljava/lang/Object;", "Z");
            arrinlineMethod[4] = null;
            arrinlineMethod[5] = null;
            deodexUtil.getClass();
            arrinlineMethod[6] = deodexUtil.new DeodexUtil.InlineMethod(0, "Ljava/lang/String;", "length", "", "I");
            deodexUtil.getClass();
            arrinlineMethod[7] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "I", "I");
            deodexUtil.getClass();
            arrinlineMethod[8] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "J", "J");
            deodexUtil.getClass();
            arrinlineMethod[9] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "F", "F");
            deodexUtil.getClass();
            arrinlineMethod[10] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "abs", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[11] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "min", "II", "I");
            deodexUtil.getClass();
            arrinlineMethod[12] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "max", "II", "I");
            deodexUtil.getClass();
            arrinlineMethod[13] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "sqrt", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[14] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "cos", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[15] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Math;", "sin", "D", "D");
            deodexUtil.getClass();
            arrinlineMethod[16] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Float;", "floatToIntBits", "F", "I");
            deodexUtil.getClass();
            arrinlineMethod[17] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Float;", "floatToRawIntBits", "F", "I");
            deodexUtil.getClass();
            arrinlineMethod[18] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Float;", "intBitsToFloat", "I", "F");
            deodexUtil.getClass();
            arrinlineMethod[19] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Double;", "doubleToLongBits", "D", "J");
            deodexUtil.getClass();
            arrinlineMethod[20] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Double;", "doubleToRawLongBits", "D", "J");
            deodexUtil.getClass();
            arrinlineMethod[21] = deodexUtil.new DeodexUtil.InlineMethod(2, "Ljava/lang/Double;", "longBitsToDouble", "J", "D");
            this.inlineMethods = arrinlineMethod;
        }

        private int getParameterCount(OdexedInvokeVirtual odexedInvokeVirtual) {
            if (odexedInvokeVirtual instanceof Instruction35ms) {
                return ((Instruction35ms)odexedInvokeVirtual).getRegCount();
            }
            return ((Instruction3rms)odexedInvokeVirtual).getRegCount();
        }

        @Override
        public DeodexUtil.InlineMethod resolveExecuteInline(AnalyzedInstruction analyzedInstruction) {
            if (!$assertionsDisabled && !(analyzedInstruction.instruction instanceof OdexedInvokeVirtual)) {
                throw new AssertionError();
            }
            OdexedInvokeVirtual odexedInvokeVirtual = (OdexedInvokeVirtual)((Object)analyzedInstruction.instruction);
            int n = odexedInvokeVirtual.getMethodIndex();
            if (n < 0 || n >= this.inlineMethods.length) {
                throw new RuntimeException("Invalid method index: " + n);
            }
            if (n == 4) {
                int n2 = InlineMethodResolver_version36.super.getParameterCount(odexedInvokeVirtual);
                if (n2 == 2) {
                    return this.indexOfIMethod;
                }
                if (n2 == 3) {
                    return this.fastIndexOfMethod;
                }
                throw new RuntimeException("Could not determine the correct inline method to use");
            }
            if (n == 5) {
                int n3 = InlineMethodResolver_version36.super.getParameterCount(odexedInvokeVirtual);
                if (n3 == 3) {
                    return this.indexOfIIMethod;
                }
                if (n3 == 1) {
                    return this.isEmptyMethod;
                }
                throw new RuntimeException("Could not determine the correct inline method to use");
            }
            return this.inlineMethods[n];
        }
    }

}

