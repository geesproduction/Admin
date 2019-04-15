/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package kellinwood.security.zipsigner;

public interface ResourceAdapter {
    public /* varargs */ String getString(Item var1, Object ... var2);

    public static final class Item
    extends Enum<Item> {
        private static final /* synthetic */ Item[] $VALUES;
        public static final /* enum */ Item AUTO_KEY_SELECTION_ERROR;
        public static final /* enum */ Item COPYING_ZIP_ENTRY;
        public static final /* enum */ Item GENERATING_MANIFEST;
        public static final /* enum */ Item GENERATING_SIGNATURE_BLOCK;
        public static final /* enum */ Item GENERATING_SIGNATURE_FILE;
        public static final /* enum */ Item INPUT_SAME_AS_OUTPUT_ERROR;
        public static final /* enum */ Item LOADING_CERTIFICATE_AND_KEY;
        public static final /* enum */ Item PARSING_CENTRAL_DIRECTORY;

        static {
            INPUT_SAME_AS_OUTPUT_ERROR = new Item();
            AUTO_KEY_SELECTION_ERROR = new Item();
            LOADING_CERTIFICATE_AND_KEY = new Item();
            PARSING_CENTRAL_DIRECTORY = new Item();
            GENERATING_MANIFEST = new Item();
            GENERATING_SIGNATURE_FILE = new Item();
            GENERATING_SIGNATURE_BLOCK = new Item();
            COPYING_ZIP_ENTRY = new Item();
            Item[] arritem = new Item[]{INPUT_SAME_AS_OUTPUT_ERROR, AUTO_KEY_SELECTION_ERROR, LOADING_CERTIFICATE_AND_KEY, PARSING_CENTRAL_DIRECTORY, GENERATING_MANIFEST, GENERATING_SIGNATURE_FILE, GENERATING_SIGNATURE_BLOCK, COPYING_ZIP_ENTRY};
            $VALUES = arritem;
        }

        public static Item valueOf(String string2) {
            return (Item)Enum.valueOf(Item.class, (String)string2);
        }

        public static Item[] values() {
            return (Item[])$VALUES.clone();
        }
    }

}

