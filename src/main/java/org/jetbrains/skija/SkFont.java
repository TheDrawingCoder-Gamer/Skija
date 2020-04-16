package org.jetbrains.skija;

public class SkFont extends Managed {
    public final SkTypeface typeface;
    public SkFont(SkTypeface typeface, float size) {
        super(nInit(typeface.mNativeInstance, size), kNativeFinalizer);
        this.typeface = typeface;
    }

    private static long kNativeFinalizer = nGetNativeFinalizer();
    private static native long nInit(long typefacePtr, float size);
    private static native long nGetNativeFinalizer();
}