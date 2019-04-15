/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.Vector
 *  org.spongycastle.asn1.ASN1ObjectIdentifier
 *  org.spongycastle.asn1.x500.style.BCStyle
 *  org.spongycastle.jce.X509Principal
 */
package kellinwood.security.zipsigner.optional;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x500.style.BCStyle;
import org.spongycastle.jce.X509Principal;

public class DistinguishedNameValues
extends LinkedHashMap<ASN1ObjectIdentifier, String> {
    public DistinguishedNameValues() {
        this.put(BCStyle.C, null);
        this.put(BCStyle.ST, null);
        this.put(BCStyle.L, null);
        this.put(BCStyle.STREET, null);
        this.put(BCStyle.O, null);
        this.put(BCStyle.OU, null);
        this.put(BCStyle.CN, null);
    }

    public X509Principal getPrincipal() {
        Vector vector = new Vector();
        Vector vector2 = new Vector();
        for (Map.Entry entry : this.entrySet()) {
            if (entry.getValue() == null || ((String)entry.getValue()).equals((Object)"")) continue;
            vector.add(entry.getKey());
            vector2.add(entry.getValue());
        }
        return new X509Principal(vector, vector2);
    }

    public String put(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string2) {
        if (string2 != null && string2.equals((Object)"")) {
            string2 = null;
        }
        if (this.containsKey((Object)aSN1ObjectIdentifier)) {
            super.put((Object)aSN1ObjectIdentifier, (Object)string2);
            return string2;
        }
        super.put((Object)aSN1ObjectIdentifier, (Object)string2);
        return string2;
    }

    public void setCommonName(String string2) {
        this.put(BCStyle.CN, string2);
    }

    public void setCountry(String string2) {
        this.put(BCStyle.C, string2);
    }

    public void setLocality(String string2) {
        this.put(BCStyle.L, string2);
    }

    public void setOrganization(String string2) {
        this.put(BCStyle.O, string2);
    }

    public void setOrganizationalUnit(String string2) {
        this.put(BCStyle.OU, string2);
    }

    public void setState(String string2) {
        this.put(BCStyle.ST, string2);
    }

    public void setStreet(String string2) {
        this.put(BCStyle.STREET, string2);
    }

    public int size() {
        int n = 0;
        Iterator iterator = this.values().iterator();
        while (iterator.hasNext()) {
            if ((String)iterator.next() == null) continue;
            ++n;
        }
        return n;
    }
}

