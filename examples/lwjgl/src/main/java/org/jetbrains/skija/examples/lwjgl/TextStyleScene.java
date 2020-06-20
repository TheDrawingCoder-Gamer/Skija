package org.jetbrains.skija.examples.lwjgl;

import java.util.Arrays;
import org.jetbrains.skija.*;
import org.jetbrains.skija.paragraph.*;

public class TextStyleScene implements Scene {
    public FontCollection fc = new FontCollection();
    public Typeface slabo;

    public TextStyleScene() {
        fc.setDefaultFontManager(FontMgr.getDefault());
        slabo = Typeface.makeFromFile("fonts/Slabo13px-Regular.ttf");
    }

    public void drawLine(Canvas canvas, String text, TextStyle ts) {
        try (ParagraphStyle ps   = new ParagraphStyle();
             ParagraphBuilder pb = new ParagraphBuilder(ps, fc);)
        {
            pb.pushStyle(ts);
            pb.addText(text);
            try (Paragraph p = pb.build();) {
                p.layout(Float.POSITIVE_INFINITY);
                p.paint(canvas, 0, 0);
                canvas.translate(0, p.getHeight());
            }
        }
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(30, 30);

        try (var ts = new TextStyle().setColor(0xFFcc3333)) {
            assert 0xFFcc3333 == ts.getColor();
            drawLine(canvas, "Shall I compare thee to a summer’s day?", ts); 
        }

        try (Shader sh = Shader.makeLinearGradient(0, 0, 0, 13, new int[] { 0xFF3A1C71, 0xFFD76D77, 0xFFFFAF7B });
             Paint p = new Paint().setShader(sh);
             var ts = new TextStyle().setForeground(p)) {
            assert p.equals(ts.getForeground());
            drawLine(canvas, "Thou art more lovely and more temperate:", ts); 
            ts.setForeground(null);
            assert null == ts.getForeground();
        }

        try (Shader sh = Shader.makeLinearGradient(0, 0, 0, 13, new int[] { 0xFF3A1C71, 0xFFD76D77, 0xFFFFAF7B });
             Paint p = new Paint().setShader(sh);
             var ts = new TextStyle().setBackground(p)) {
            assert p.equals(ts.getBackground());
            drawLine(canvas, "Rough winds do shake the darling buds of May,", ts); 
        }

        var d = TextStyle.Decoration.NONE.withUnderline(true).withColor(0xFF3A1C71);
        try (var ts = new TextStyle().setColor(0xFF000000).setDecoration(d)) {
            assert d.equals(ts.getDecoration());
            assert d.hasUnderline() && !d.hasOverline() && !d.hasLineThrough();
            drawLine(canvas, "And summer’s lease hath all too short a date;", ts);
        }

        d = TextStyle.Decoration.NONE.withUnderline(true).withColor(0xFFFFAF7B).withMode(TextStyle.Decoration.Mode.THROUGH);
        try (var ts = new TextStyle().setColor(0xFF000000).setDecoration(d)) {
            assert d.equals(ts.getDecoration());
            drawLine(canvas, "Sometime too hot the eye of heaven shines,", ts);
        }

        d = TextStyle.Decoration.NONE.withUnderline(true).withColor(0xFF3A1C71).withStyle(TextStyle.Decoration.Style.DOTTED);
        try (var ts = new TextStyle().setColor(0xFF000000).setDecoration(d)) {
            assert d.equals(ts.getDecoration());
            drawLine(canvas, "And often is his gold complexion dimm'd;", ts);
        }

        d = TextStyle.Decoration.NONE.withOverline(true).withLineThrough(true).withColor(0xFFD76D77).withThicknessMultiplier(3);
        try (var ts = new TextStyle().setColor(0xFF000000).setDecoration(d)) {
            assert d.equals(ts.getDecoration());
            assert !d.hasUnderline() && d.hasOverline() && d.hasLineThrough();
            drawLine(canvas, "And every fair from fair sometime declines,", ts);
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setFontStyle(FontStyle.BOLD_ITALIC)) {
            assert FontStyle.BOLD_ITALIC.equals(ts.getFontStyle());
            drawLine(canvas, "By chance or nature’s changing course untrimm'd;", ts);
        }

        var shadows = new TextStyle.Shadow[] {
            new TextStyle.Shadow(0x803A1C71, -1, -1, 0),
            new TextStyle.Shadow(0xFFD76D77, 3, 3, 3)
        };
        try (var ts = new TextStyle().setColor(0xFF000000).addShadows(shadows)) {
            assert Arrays.equals(shadows, ts.getShadows());
            drawLine(canvas, "But thy eternal summer shall not fade,", ts);
            ts.clearShadows();
            assert Arrays.equals(new TextStyle.Shadow[0], ts.getShadows());
        }

        var fontFeatures = new TextStyle.FontFeature[] {
            new TextStyle.FontFeature("cv06", 1),
            new TextStyle.FontFeature("cv07", 1),
        };

        var fontFamilies = new String[] { "System Font", "Apple Color Emoji" };

        try (var ts = new TextStyle().setColor(0xFF000000).setFontFamilies(fontFamilies).addFontFeatures(fontFeatures)) {
            assert Arrays.equals(fontFamilies, ts.getFontFamilies());
            assert Arrays.equals(fontFeatures, ts.getFontFeatures());
            drawLine(canvas, "Nor lose possession of that fair thou 🧑🏿‍🦰 ow’st;", ts);
            ts.clearFontFeatures();
            assert Arrays.equals(new TextStyle.FontFeature[0], ts.getFontFeatures());

            FontMetrics m = ts.getFontMetrics();
            assert m.getTop() < m.getAscent() && m.getAscent() < m.getDescent() && m.getDescent() < m.getBottom();
            drawLine(canvas, m.toString(), ts);
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setFontSize(20)) {
            assert 20 == ts.getFontSize();
            drawLine(canvas, "Nor shall death brag thou wander’st in his shade,", ts);
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setHeight(3f)) {
            assert 3f == ts.getHeight();
            drawLine(canvas, "So long as men can breathe or eyes can see,", ts);
            ts.setHeight(null);
            assert null == ts.getHeight();
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setLetterSpacing(3)) {
            assert 3f == ts.getLetterSpacing();
            drawLine(canvas, "When in eternal lines to time thou grow’st:", ts);
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setWordSpacing(3)) {
            assert 3f == ts.getWordSpacing();
            drawLine(canvas, "So long as men can breathe or eyes can see,", ts);
        }

        // TODO doesn’t work?
        try (var ts = new TextStyle().setColor(0xFF000000).setTypeface(slabo).setFontFamily("Slabo 13px")) {
            assert slabo.equals(ts.getTypeface());
            drawLine(canvas, "So long lives this, and this gives life to thee. rrrrr", ts);
        }

        // TODO single font?
        for (String locale: new String[] { "zh-Hans", "zh-Hant", "zh-Hant-HK", "ja", "ko", "vi-Hani" }) {
            // for (String family: new String[] { "PingFang SC", "PingFang TC", "PingFang HK", "Apple SD Gothic Neo", "Hiragino Sans" }) {
                try (var ts = new TextStyle().setColor(0xFF000000).setLetterSpacing(2).setLocale(locale)/*.setFontFamily(family)*/) {
                    assert locale.equals(ts.getLocale());
                    drawLine(canvas, "令免入全关具刃化外情才抵次海直真示神空者草蔥角道雇骨 " + locale, ts);
                }
            // }
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setBaselineType(TextStyle.BaselineType.IDEOGRAPHIC)) {
            assert TextStyle.BaselineType.IDEOGRAPHIC == ts.getBaselineType();
            drawLine(canvas, "Baseline Type", ts);
        }

        try (var ts = new TextStyle().setColor(0xFF000000).setPlaceholder()) {
            assert ts.isPlaceholder();
            drawLine(canvas, "Placeholder", ts);
        }
    }
}