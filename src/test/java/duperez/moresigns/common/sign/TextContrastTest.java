package duperez.moresigns.common.sign;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextContrastTest {

    @Test
    void nearBlackBoardsGetWhiteText() {
        assertTrue(TextContrast.prefersWhiteText(0x191919), "black wool / obsidian map color");
        assertTrue(TextContrast.prefersWhiteText(0x000000));
    }

    @Test
    void lightBoardsKeepBlackText() {
        assertFalse(TextContrast.prefersWhiteText(0xFFFFFF), "snow / white wool");
        assertFalse(TextContrast.prefersWhiteText(0x8F7748), "oak planks");
        assertFalse(TextContrast.prefersWhiteText(0xF7E9A3), "birch planks");
    }

    @Test
    void vanillaSignWoodsKeepBlackTextLikeVanilla() {
        assertFalse(TextContrast.prefersWhiteText(0x664C33), "dark oak planks, black text in vanilla");
    }
}
