/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.LinkedList
 *  java.util.List
 */
package org.jf.dexlib.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.Pair;

public class TryListBuilder {
    private TryRange firstTryRange = new TryRange(0, 0);
    private TryRange lastTryRange;

    public TryListBuilder() {
        this.firstTryRange.next = this.lastTryRange = new TryRange(0, 0);
        this.lastTryRange.previous = this.firstTryRange;
    }

    public void addCatchAllHandler(int n, int n2, int n3) {
        Pair<TryRange, TryRange> pair = this.getBoundingRanges(n, n2);
        TryRange tryRange = (TryRange)pair.first;
        TryRange tryRange2 = (TryRange)pair.second;
        int n4 = n;
        TryRange tryRange3 = tryRange;
        do {
            if (tryRange3.startAddress > n4) {
                TryRange tryRange4 = (TryListBuilder)this.new TryRange(n4, tryRange3.startAddress);
                tryRange3.prepend(tryRange4);
                tryRange3 = tryRange4;
            }
            if (tryRange3.catchAllHandlerAddress == -1) {
                tryRange3.catchAllHandlerAddress = n3;
            }
            n4 = tryRange3.endAddress;
            tryRange3 = tryRange3.next;
        } while (tryRange3.previous != tryRange2);
    }

    public void addHandler(TypeIdItem typeIdItem, int n, int n2, int n3) {
        Pair<TryRange, TryRange> pair = this.getBoundingRanges(n, n2);
        TryRange tryRange = (TryRange)pair.first;
        TryRange tryRange2 = (TryRange)pair.second;
        Handler handler = (TryListBuilder)this.new Handler(typeIdItem, n3);
        int n4 = n;
        TryRange tryRange3 = tryRange;
        do {
            if (tryRange3.startAddress > n4) {
                TryRange tryRange4 = (TryListBuilder)this.new TryRange(n4, tryRange3.startAddress);
                tryRange3.prepend(tryRange4);
                tryRange3 = tryRange4;
            }
            tryRange3.appendHandler(handler);
            n4 = tryRange3.endAddress;
            tryRange3 = tryRange3.next;
        } while (tryRange3.previous != tryRange2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Pair<List<CodeItem.TryItem>, List<CodeItem.EncodedCatchHandler>> encodeTries() {
        if (this.firstTryRange.next == this.lastTryRange) {
            return new Pair<Object, Object>(null, null);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap = new HashMap();
        TryRange tryRange = this.firstTryRange.next;
        while (tryRange != this.lastTryRange) {
            CodeItem.EncodedTypeAddrPair[] arrencodedTypeAddrPair = new CodeItem.EncodedTypeAddrPair[tryRange.handlers.size()];
            int n = 0;
            for (Handler handler : tryRange.handlers) {
                CodeItem.EncodedTypeAddrPair encodedTypeAddrPair = new CodeItem.EncodedTypeAddrPair(handler.type, handler.handlerAddress);
                int n2 = n + 1;
                arrencodedTypeAddrPair[n] = encodedTypeAddrPair;
                n = n2;
            }
            CodeItem.EncodedCatchHandler encodedCatchHandler = new CodeItem.EncodedCatchHandler(arrencodedTypeAddrPair, tryRange.catchAllHandlerAddress);
            CodeItem.EncodedCatchHandler encodedCatchHandler2 = (CodeItem.EncodedCatchHandler)hashMap.get((Object)encodedCatchHandler);
            if (encodedCatchHandler2 == null) {
                hashMap.put((Object)encodedCatchHandler, (Object)encodedCatchHandler);
                arrayList2.add((Object)encodedCatchHandler);
            } else {
                encodedCatchHandler = encodedCatchHandler2;
            }
            arrayList.add((Object)new CodeItem.TryItem(tryRange.startAddress, tryRange.endAddress - tryRange.startAddress, encodedCatchHandler));
            tryRange = tryRange.next;
        }
        return new Pair<ArrayList, ArrayList>(arrayList, arrayList2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Pair<TryRange, TryRange> getBoundingRanges(int n, int n2) {
        TryRange tryRange;
        TryRange tryRange2 = this.firstTryRange.next;
        do {
            block13 : {
                block10 : {
                    block12 : {
                        block11 : {
                            TryRange tryRange3 = this.lastTryRange;
                            tryRange = null;
                            if (tryRange2 == tryRange3) break block10;
                            if (n != tryRange2.startAddress) break block11;
                            tryRange = tryRange2;
                            break block10;
                        }
                        if (n <= tryRange2.startAddress || n >= tryRange2.endAddress) break block12;
                        tryRange = tryRange2.split(n);
                        break block10;
                    }
                    if (n >= tryRange2.startAddress) break block13;
                    if (n2 <= tryRange2.startAddress) {
                        TryRange tryRange4 = (TryListBuilder)this.new TryRange(n, n2);
                        tryRange2.prepend(tryRange4);
                        return new Pair<TryRange, TryRange>(tryRange4, tryRange4);
                    }
                    tryRange = (TryListBuilder)this.new TryRange(n, tryRange2.startAddress);
                    tryRange2.prepend(tryRange);
                }
                if (tryRange != null) break;
                TryRange tryRange5 = (TryListBuilder)this.new TryRange(n, n2);
                this.lastTryRange.prepend(tryRange5);
                return new Pair<TryRange, TryRange>(tryRange5, tryRange5);
            }
            tryRange2 = tryRange2.next;
        } while (true);
        TryRange tryRange6 = tryRange;
        do {
            block17 : {
                TryRange tryRange7;
                block14 : {
                    block16 : {
                        block15 : {
                            TryRange tryRange8 = this.lastTryRange;
                            tryRange7 = null;
                            if (tryRange6 == tryRange8) break block14;
                            if (tryRange6.endAddress != n2) break block15;
                            tryRange7 = tryRange6;
                            break block14;
                        }
                        if (tryRange6.startAddress >= n2 || tryRange6.endAddress <= n2) break block16;
                        tryRange6.split(n2);
                        tryRange7 = tryRange6;
                        break block14;
                    }
                    if (tryRange6.startAddress < n2) break block17;
                    tryRange7 = (TryListBuilder)this.new TryRange(tryRange6.previous.endAddress, n2);
                    tryRange6.prepend(tryRange7);
                }
                if (tryRange7 == null) {
                    tryRange7 = (TryListBuilder)this.new TryRange(this.lastTryRange.previous.endAddress, n2);
                    this.lastTryRange.prepend(tryRange7);
                }
                return new Pair<TryRange, TryRange>(tryRange, tryRange7);
            }
            tryRange6 = tryRange6.next;
        } while (true);
    }

    private class Handler {
        public final int handlerAddress;
        public final TypeIdItem type;

        public Handler(TypeIdItem typeIdItem, int n) {
            this.type = typeIdItem;
            this.handlerAddress = n;
        }
    }

    private class TryRange {
        public int catchAllHandlerAddress;
        public int endAddress;
        public LinkedList<Handler> handlers;
        public TryRange next = null;
        public TryRange previous = null;
        public int startAddress;

        public TryRange(int n, int n2) {
            this.startAddress = n;
            this.endAddress = n2;
            this.handlers = new LinkedList();
            this.previous = null;
            this.next = null;
            this.catchAllHandlerAddress = -1;
        }

        public void append(TryRange tryRange) {
            this.next.previous = tryRange;
            tryRange.next = this.next;
            this.next = tryRange;
            tryRange.previous = this;
        }

        public void appendHandler(Handler handler) {
            this.handlers.addLast((Object)handler);
        }

        public void prepend(TryRange tryRange) {
            this.previous.next = tryRange;
            tryRange.previous = this.previous;
            this.previous = tryRange;
            tryRange.next = this;
        }

        public void prependHandler(Handler handler) {
            this.handlers.addFirst((Object)handler);
        }

        public TryRange split(int n) {
            TryRange tryRange = new TryRange(n, this.endAddress);
            tryRange.catchAllHandlerAddress = this.catchAllHandlerAddress;
            tryRange.handlers.addAll(this.handlers);
            this.append(tryRange);
            this.endAddress = n;
            return tryRange;
        }
    }

}

