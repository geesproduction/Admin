/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.BitSet
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.SortedSet
 *  java.util.TreeSet
 */
package org.jf.dexlib.Code.Analysis;

import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jf.dexlib.Code.Analysis.RegisterType;
import org.jf.dexlib.Code.FiveRegisterInstruction;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.RegisterRangeInstruction;
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.Util.ExceptionWithContext;

public class AnalyzedInstruction
implements Comparable<AnalyzedInstruction> {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected boolean dead = false;
    protected Instruction instruction;
    protected final int instructionIndex;
    protected final Instruction originalInstruction;
    protected final RegisterType[] postRegisterMap;
    protected final RegisterType[] preRegisterMap;
    protected final TreeSet<AnalyzedInstruction> predecessors = new TreeSet();
    protected final LinkedList<AnalyzedInstruction> successors = new LinkedList();

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !AnalyzedInstruction.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public AnalyzedInstruction(Instruction instruction, int n, int n2) {
        this.instruction = instruction;
        this.originalInstruction = instruction;
        this.instructionIndex = n;
        this.postRegisterMap = new RegisterType[n2];
        this.preRegisterMap = new RegisterType[n2];
        RegisterType registerType = RegisterType.getRegisterType(RegisterType.Category.Unknown, null);
        for (int i = 0; i < n2; ++i) {
            this.preRegisterMap[i] = registerType;
            this.postRegisterMap[i] = registerType;
        }
    }

    protected boolean addPredecessor(AnalyzedInstruction analyzedInstruction) {
        return this.predecessors.add((Object)analyzedInstruction);
    }

    protected void addSuccessor(AnalyzedInstruction analyzedInstruction) {
        this.successors.add((Object)analyzedInstruction);
    }

    public int compareTo(AnalyzedInstruction analyzedInstruction) {
        if (this.instructionIndex < analyzedInstruction.instructionIndex) {
            return -1;
        }
        return this.instructionIndex != analyzedInstruction.instructionIndex;
    }

    public int getDestinationRegister() {
        if (!this.instruction.opcode.setsRegister()) {
            throw new ExceptionWithContext("Cannot call getDestinationRegister() for an instruction that doesn't store a value");
        }
        return ((SingleRegisterInstruction)((Object)this.instruction)).getRegisterA();
    }

    public Instruction getInstruction() {
        return this.instruction;
    }

    public int getInstructionIndex() {
        return this.instructionIndex;
    }

    public Instruction getOriginalInstruction() {
        return this.originalInstruction;
    }

    public RegisterType getPostInstructionRegisterType(int n) {
        return this.postRegisterMap[n];
    }

    public RegisterType getPreInstructionRegisterType(int n) {
        return this.preRegisterMap[n];
    }

    public int getPredecessorCount() {
        return this.predecessors.size();
    }

    public SortedSet<AnalyzedInstruction> getPredecessors() {
        return Collections.unmodifiableSortedSet(this.predecessors);
    }

    public int getRegisterCount() {
        return this.postRegisterMap.length;
    }

    public List<AnalyzedInstruction> getSuccesors() {
        return Collections.unmodifiableList(this.successors);
    }

    public int getSuccessorCount() {
        return this.successors.size();
    }

    public boolean isBeginningInstruction() {
        if (this.predecessors.size() == 0) {
            return false;
        }
        return ((AnalyzedInstruction)this.predecessors.first()).instructionIndex == -1;
    }

    public boolean isDead() {
        return this.dead;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean isInvokeInit() {
        block5 : {
            block4 : {
                if (this.instruction == null || this.instruction.opcode != Opcode.INVOKE_DIRECT && this.instruction.opcode != Opcode.INVOKE_DIRECT_RANGE && this.instruction.opcode != Opcode.INVOKE_DIRECT_EMPTY) break block4;
                Item item = ((InstructionWithReference)this.instruction).getReferencedItem();
                if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_METHOD_ID_ITEM) {
                    throw new AssertionError();
                }
                if (((MethodIdItem)item).getMethodName().getStringValue().equals((Object)"<init>")) break block5;
            }
            return false;
        }
        return true;
    }

    protected RegisterType mergePreRegisterTypeFromPredecessors(int n) {
        RegisterType registerType = null;
        Iterator iterator = this.predecessors.iterator();
        while (iterator.hasNext()) {
            RegisterType registerType2 = ((AnalyzedInstruction)iterator.next()).postRegisterMap[n];
            if (!$assertionsDisabled && registerType2 == null) {
                throw new AssertionError();
            }
            registerType = registerType2.merge(registerType);
        }
        return registerType;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean mergeRegister(int n, RegisterType registerType, BitSet bitSet) {
        RegisterType registerType2;
        block6 : {
            block5 : {
                if (!($assertionsDisabled || n >= 0 && n < this.postRegisterMap.length)) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && registerType == null) {
                    throw new AssertionError();
                }
                RegisterType registerType3 = this.preRegisterMap[n];
                registerType2 = registerType3.merge(registerType);
                if (registerType2 == registerType3) break block5;
                this.preRegisterMap[n] = registerType2;
                bitSet.clear(this.instructionIndex);
                if (!this.setsRegister(n)) break block6;
            }
            return false;
        }
        this.postRegisterMap[n] = registerType2;
        return true;
    }

    protected void restoreOdexedInstruction() {
        if (!$assertionsDisabled && !this.originalInstruction.opcode.odexOnly()) {
            throw new AssertionError();
        }
        this.instruction = this.originalInstruction;
    }

    protected void setDeodexedInstruction(Instruction instruction) {
        if (!$assertionsDisabled && !this.originalInstruction.opcode.odexOnly()) {
            throw new AssertionError();
        }
        this.instruction = instruction;
    }

    protected boolean setPostRegisterType(int n, RegisterType registerType) {
        if (!($assertionsDisabled || n >= 0 && n < this.postRegisterMap.length)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (this.postRegisterMap[n] == registerType) {
            return false;
        }
        this.postRegisterMap[n] = registerType;
        return true;
    }

    public boolean setsRegister() {
        return this.instruction.opcode.setsRegister();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean setsRegister(int n) {
        if (this.isInvokeInit()) {
            int n2;
            if (this.instruction instanceof FiveRegisterInstruction) {
                n2 = ((FiveRegisterInstruction)((Object)this.instruction)).getRegisterD();
            } else {
                if (!$assertionsDisabled && !(this.instruction instanceof RegisterRangeInstruction)) {
                    throw new AssertionError();
                }
                RegisterRangeInstruction registerRangeInstruction = (RegisterRangeInstruction)((Object)this.instruction);
                if (!$assertionsDisabled && registerRangeInstruction.getRegCount() <= 0) {
                    throw new AssertionError();
                }
                n2 = registerRangeInstruction.getStartRegister();
            }
            if (n == n2) {
                return true;
            }
            RegisterType registerType = this.getPreInstructionRegisterType(n);
            if (registerType.category != RegisterType.Category.UninitRef && registerType.category != RegisterType.Category.UninitThis) {
                return false;
            }
            return this.getPreInstructionRegisterType(n) == registerType;
        }
        if (!this.setsRegister()) {
            return false;
        }
        int n3 = this.getDestinationRegister();
        if (n == n3) {
            return true;
        }
        return this.setsWideRegister() && n == n3 + 1;
    }

    public boolean setsWideRegister() {
        return this.instruction.opcode.setsWideRegister();
    }
}

