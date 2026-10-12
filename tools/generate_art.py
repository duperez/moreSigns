#!/usr/bin/env python3
"""
Generates the pixel art of the sign workbench (block textures and GUI sprites) without any third-party library.

Run from the repository root:   python3 tools/generate_art.py
It overwrites the PNGs under src/main/resources/assets/moresigns/textures/. Edit the palette or the drawing
functions below and run it again; the output is deterministic.
"""
import os
import struct
import zlib

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "moresigns", "textures")
CLEAR = (0, 0, 0, 0)


class Canvas:
    def __init__(self, width, height, fill=CLEAR):
        self.w, self.h = width, height
        self.px = [[fill for _ in range(width)] for _ in range(height)]

    def set(self, x, y, color):
        if 0 <= x < self.w and 0 <= y < self.h:
            self.px[y][x] = color

    def get(self, x, y):
        return self.px[y][x]

    def rect(self, x, y, w, h, color):
        for j in range(y, y + h):
            for i in range(x, x + w):
                self.set(i, j, color)

    def outline(self, x, y, w, h, color):
        self.rect(x, y, w, 1, color)
        self.rect(x, y + h - 1, w, 1, color)
        self.rect(x, y, 1, h, color)
        self.rect(x + w - 1, y, 1, h, color)

    def line(self, x0, y0, x1, y1, color):
        dx, dy = abs(x1 - x0), -abs(y1 - y0)
        sx, sy = (1 if x0 < x1 else -1), (1 if y0 < y1 else -1)
        err = dx + dy
        while True:
            self.set(x0, y0, color)
            if x0 == x1 and y0 == y1:
                break
            e2 = 2 * err
            if e2 >= dy:
                err += dy
                x0 += sx
            if e2 <= dx:
                err += dx
                y0 += sy

    def save(self, relative_path):
        path = os.path.join(ROOT, relative_path)
        os.makedirs(os.path.dirname(path), exist_ok=True)
        raw = b"".join(b"\x00" + b"".join(bytes(p) for p in row) for row in self.px)

        def chunk(tag, data):
            body = tag + data
            return struct.pack(">I", len(data)) + body + struct.pack(">I", zlib.crc32(body) & 0xFFFFFFFF)

        png = b"\x89PNG\r\n\x1a\n" + chunk(b"IHDR", struct.pack(">IIBBBBB", self.w, self.h, 8, 6, 0, 0, 0))
        png += chunk(b"IDAT", zlib.compress(raw, 9)) + chunk(b"IEND", b"")
        with open(path, "wb") as f:
            f.write(png)


def rgb(value, alpha=255):
    return (value >> 16 & 255, value >> 8 & 255, value & 255, alpha)


def shade(color, factor):
    return tuple(max(0, min(255, int(c * factor))) for c in color[:3]) + (color[3],)


class Noise:
    """Small deterministic noise, so every run draws the same texture."""

    def __init__(self, seed):
        self.state = seed

    def next(self):
        self.state = (self.state * 1103515245 + 12345) & 0x7FFFFFFF
        return self.state / 0x7FFFFFFF


# ---------------------------------------------------------------- block textures

WOOD_DARK, WOOD_MID, WOOD_LIGHT = rgb(0x5B3F22), rgb(0x7A5733), rgb(0x95703F)
WOOD_PALE = rgb(0xB48B55)
CANVAS, CANVAS_SHADE, STITCH = rgb(0xE8DDBF), rgb(0xCFC29E), rgb(0x9C8E6B)
INK, GRAPHITE = rgb(0x3A4F7A), rgb(0x3C3C3C)
METAL, METAL_DARK = rgb(0xB8BDC4), rgb(0x6E747C)


def planks(canvas, x0, y0, w, h, base, seed, plank_height=4):
    noise = Noise(seed)
    for y in range(y0, y0 + h):
        row = (y - y0) // plank_height
        for x in range(x0, x0 + w):
            tone = 0.94 + 0.12 * noise.next() + (0.04 if row % 2 else -0.02)
            canvas.set(x, y, shade(base, tone))
        if (y - y0) % plank_height == plank_height - 1:
            for x in range(x0, x0 + w):
                canvas.set(x, y, shade(base, 0.72))
    # plank ends
    for row in range((h + plank_height - 1) // plank_height):
        offset = (row * 7 + seed) % w
        for y in range(row * plank_height, min(h, row * plank_height + plank_height - 1)):
            canvas.set(x0 + offset, y0 + y, shade(base, 0.78))


def block_top():
    c = Canvas(16, 16)
    planks(c, 0, 0, 16, 16, WOOD_LIGHT, 11)
    c.rect(0, 0, 16, 1, shade(WOOD_MID, 0.85))
    c.rect(0, 15, 16, 1, shade(WOOD_MID, 0.8))
    # a piece of canvas pinned to the table, with a sketch and stitches
    c.rect(2, 3, 9, 8, CANVAS)
    c.rect(2, 10, 9, 1, CANVAS_SHADE)
    c.outline(2, 3, 9, 8, CANVAS_SHADE)
    for x in range(3, 10, 2):
        c.set(x, 4, STITCH)
        c.set(x, 9, STITCH)
    c.line(4, 8, 6, 5, INK)
    c.line(6, 5, 8, 7, INK)
    c.set(5, 6, INK)
    # a ruler along the right edge and a pencil
    c.rect(12, 2, 2, 12, rgb(0xD9B25F))
    c.outline(12, 2, 2, 12, rgb(0x8F6E2E))
    for y in range(3, 13, 2):
        c.set(12, y, rgb(0x5A4416))
    c.line(5, 13, 10, 13, GRAPHITE)
    c.set(11, 13, rgb(0xE06666))
    return c


def block_side():
    c = Canvas(16, 16)
    planks(c, 0, 0, 16, 16, WOOD_MID, 23, plank_height=5)
    c.rect(0, 0, 16, 1, shade(WOOD_LIGHT, 1.05))
    c.rect(0, 15, 16, 1, shade(WOOD_DARK, 1.0))
    return c


def block_leg():
    c = Canvas(16, 16)
    noise = Noise(7)
    for x in range(16):
        column = 0.9 + 0.2 * noise.next() + (0.06 if x % 5 == 0 else 0)
        for y in range(16):
            c.set(x, y, shade(WOOD_DARK, column * (0.95 + 0.1 * noise.next()) * 1.15))
    for y in (3, 11):
        c.rect(0, y, 16, 1, shade(WOOD_DARK, 0.7))
    return c


def block_back():
    c = Canvas(16, 16)
    planks(c, 0, 0, 16, 16, WOOD_PALE, 5, plank_height=16)
    base = rgb(0xA67F4B)
    c.rect(0, 0, 16, 16, base)
    c.outline(0, 0, 16, 16, shade(WOOD_DARK, 1.1))
    for y in range(2, 15, 3):
        for x in range(2, 15, 3):
            c.set(x, y, shade(WOOD_DARK, 0.9))
    # tools hanging from the pegboard: a saw, a ruler, a brush
    c.rect(2, 3, 5, 3, METAL)
    c.rect(2, 6, 5, 1, METAL_DARK)
    c.rect(7, 4, 2, 1, WOOD_DARK)
    c.rect(10, 2, 1, 9, rgb(0xD9B25F))
    c.outline(10, 2, 1, 9, rgb(0x8F6E2E))
    c.rect(13, 4, 1, 3, rgb(0xB03A2E))
    c.rect(13, 7, 1, 4, WOOD_DARK)
    c.rect(3, 9, 4, 3, rgb(0x7FA2C9))
    c.outline(3, 9, 4, 3, rgb(0x3E5F82))
    return c


def block_sign():
    c = Canvas(16, 16)
    c.rect(0, 0, 16, 16, WOOD_MID)
    c.rect(1, 1, 14, 14, CANVAS)
    c.outline(1, 1, 14, 14, shade(CANVAS, 0.82))
    for x in range(3, 13, 2):
        c.set(x, 4, STITCH)
        c.set(x, 8, STITCH)
        c.set(x, 11, STITCH)
    return c


# ---------------------------------------------------------------- GUI sprites

PANEL, WHITE, SHADOW, BLACK = rgb(0xC6C6C6), rgb(0xFFFFFF), rgb(0x555555), rgb(0x000000)
SLOT_FILL, DARK_EDGE = rgb(0x8B8B8B), rgb(0x373737)
AMBER, AMBER_DARK = rgb(0xF2B84B), rgb(0xA8751C)
STAGE = rgb(0x2A2C33)


def bevel_box(w, h, fill, light, dark, outline=None, open_bottom=False, corner_cut=True, open_right=False):
    """A box with a 1px outline and a 2px bevel, lit from the top left like vanilla's windows."""
    c = Canvas(w, h)
    for y in range(h):
        for x in range(w):
            d_top, d_left, d_right, d_bottom = y, x, w - 1 - x, h - 1 - y
            if open_bottom:
                d_bottom = 99
            if open_right:
                d_right = 99
            edge = min(d_top, d_left, d_right, d_bottom)
            if edge == 0 and outline is not None:
                color = outline
            elif edge <= 2 and outline is not None or edge <= 1 and outline is None:
                if outline is None:
                    level = edge
                else:
                    level = edge - 1
                if level < 0:
                    level = 0
                if min(d_top, d_left) < min(d_right, d_bottom):
                    color = light
                elif min(d_top, d_left) > min(d_right, d_bottom):
                    color = dark
                else:
                    color = fill
            else:
                color = fill
            c.set(x, y, color)
    if corner_cut and outline is not None:
        for cx, cy in ((0, 0), (w - 1, 0), (0, h - 1), (w - 1, h - 1)):
            if not (open_bottom and cy == h - 1) and not (open_right and cx == w - 1):
                c.set(cx, cy, CLEAR)
    return c


def sprite_panel():
    return bevel_box(16, 16, PANEL, WHITE, SHADOW, outline=BLACK)


def sprite_tab(state):
    """A tab on the left edge of the panel, 30 wide and 28 tall. The selected one opens to the right, into the panel."""
    fill = {"normal": rgb(0x9B9B9B), "hover": rgb(0xB0B0B0), "selected": PANEL}[state]
    light = {"normal": rgb(0xC0C0C0), "hover": rgb(0xD8D8D8), "selected": WHITE}[state]
    c = bevel_box(30, 28, fill, light, SHADOW, outline=BLACK, open_right=(state == "selected"))
    if state == "selected":
        c.rect(27, 1, 3, 26, PANEL)  # merges into the panel next to it
        c.rect(27, 0, 3, 1, BLACK)
        c.rect(27, 27, 3, 1, BLACK)
        c.rect(27, 1, 3, 1, PANEL)
        # amber accent on the active tab
        c.rect(3, 3, 2, 22, AMBER)
        c.rect(5, 3, 1, 22, AMBER_DARK)
    return c


def sprite_inset(size, fill=STAGE):
    return bevel_box(size, size, fill, DARK_EDGE, WHITE, outline=None, corner_cut=False)


def sprite_output():
    c = bevel_box(26, 26, SLOT_FILL, DARK_EDGE, WHITE, outline=None, corner_cut=False)
    return c


def sprite_button(state):
    fill = {"normal": rgb(0x8B8B8B), "hover": rgb(0xA3A3A3), "selected": rgb(0x8B8B8B)}[state]
    light = {"normal": WHITE, "hover": WHITE, "selected": DARK_EDGE}[state]
    dark = {"normal": DARK_EDGE, "hover": DARK_EDGE, "selected": WHITE}[state]
    c = bevel_box(22, 22, fill, light, dark, outline=BLACK)
    if state == "selected":
        c.outline(1, 1, 20, 20, AMBER)
        c.outline(2, 2, 18, 18, AMBER_DARK)
    return c


def sprite_arrow(ready):
    c = Canvas(22, 15)
    body = rgb(0xF2B84B) if ready else rgb(0x7A7A7A)
    edge = rgb(0x8A5A12) if ready else rgb(0x4A4A4A)
    for y in range(15):
        half = min(y, 14 - y)
        if 4 <= y <= 10:
            c.rect(0, y, 14, 1, body)
        if y <= 14:
            width = min(half, 7) + 1
            if half <= 7:
                c.rect(14, y, width, 1, body) if False else None
    # arrow head: a triangle pointing right
    for i in range(8):
        c.rect(13 + i, 7 - (7 - i), 1, 1 + 2 * (7 - i), body) if False else None
    for x in range(14, 22):
        reach = 21 - x
        c.rect(x, 7 - reach, 1, 1 + 2 * reach, body)
    c.rect(0, 4, 14, 7, body)
    # outline
    for y in range(15):
        for x in range(22):
            if c.get(x, y) == body:
                for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1)):
                    nx, ny = x + dx, y + dy
                    if not (0 <= nx < 22 and 0 <= ny < 15) or c.get(nx, ny) == CLEAR:
                        c.set(x, y, edge)
                        break
    return c


HINT = (0x4A, 0x4A, 0x4A, 150)


def hint_material():
    c = Canvas(16, 16)
    c.outline(2, 3, 12, 11, HINT)
    c.rect(3, 7, 10, 1, HINT)
    c.rect(3, 10, 10, 1, HINT)
    c.rect(8, 4, 1, 3, HINT)
    c.rect(5, 8, 1, 2, HINT)
    return c


def hint_dye():
    c = Canvas(16, 16)
    rows = [(7, 1), (6, 3), (6, 3), (5, 5), (4, 7), (4, 7), (3, 9), (3, 9), (3, 9), (4, 7), (5, 5)]
    for i, (x, w) in enumerate(rows):
        c.rect(x + 1, 2 + i, w, 1, HINT)
    return c


def hint_frame():
    c = Canvas(16, 16)
    c.outline(1, 2, 14, 8, HINT)
    c.outline(2, 3, 12, 6, HINT)
    c.rect(7, 10, 2, 5, HINT)
    return c


def hint_board():
    c = Canvas(16, 16)
    c.rect(2, 4, 12, 7, HINT)
    return c


def hint_sign():
    c = Canvas(16, 16)
    c.outline(1, 2, 14, 8, HINT)
    c.rect(3, 4, 10, 4, HINT)
    c.rect(7, 10, 2, 5, HINT)
    return c


def icon_wash():
    c = Canvas(16, 16)
    water, edge = rgb(0x4F8FD6), rgb(0x24508A)
    rows = [(7, 2), (6, 4), (6, 4), (5, 6), (4, 8), (4, 8), (3, 10), (3, 10), (3, 10), (4, 8), (5, 6)]
    for i, (x, w) in enumerate(rows):
        c.rect(x, 2 + i, w, 1, water)
    for y in range(16):
        for x in range(16):
            if c.get(x, y) == water:
                for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1)):
                    nx, ny = x + dx, y + dy
                    if not (0 <= nx < 16 and 0 <= ny < 16) or c.get(nx, ny) == CLEAR:
                        c.set(x, y, edge)
                        break
    c.set(6, 6, rgb(0xCFE6FF))
    c.set(6, 7, rgb(0xCFE6FF))
    return c


def px(canvas, rows, palette, ox=0, oy=0):
    """Draws a picture given as rows of characters; '.' is empty and every other character is looked up in palette."""
    for y, row in enumerate(rows):
        for x, ch in enumerate(row):
            if ch != ".":
                canvas.set(ox + x, oy + y, palette[ch])


PLANK_PALETTE = {"w": WOOD_LIGHT, "d": WOOD_DARK, "m": WOOD_MID, "g": rgb(0xB8BDC4), "G": rgb(0x6E747C), "k": rgb(0x2B2B2B),
                 "c": CANVAS, "s": CANVAS_SHADE, "b": rgb(0x3E7BD1), "B": rgb(0x24508A), "r": rgb(0xD64B3C), "R": rgb(0x8F2A20),
                 "y": rgb(0xF2B84B), "Y": AMBER_DARK}


def icon_make():
    c = Canvas(16, 16)
    px(c, [
        "................",
        "..........gggg..",
        ".........gggggG.",
        ".........gggggG.",
        "..........GGGGG.",
        "........dd......",
        ".......dd.......",
        "......dd........",
        ".....dd.........",
        "..wwwwwwwwwwww..",
        "..wmmmmmmmmmmw..",
        "..wwwwwwwwwwww..",
        "..kkkkkkkkkkkk..",
        "................",
        "................",
        "................",
    ], PLANK_PALETTE)
    return c


def icon_paint():
    c = Canvas(16, 16)
    px(c, [
        "................",
        "............bb..",
        "...........bbbB.",
        "..........bbbbB.",
        ".........GgbbB..",
        "........GgG.....",
        ".......dd.......",
        "......dd........",
        ".....dd.........",
        "....dd..........",
        "...dd...........",
        "..dd............",
        "................",
        "...rr..yy.......",
        "..rrrR.yyY......",
        "...RR...YY......",
    ], PLANK_PALETTE)
    return c


def icon_assemble():
    c = Canvas(16, 16)
    px(c, [
        "................",
        "................",
        ".dddddddddddddd.",
        ".dccccccccccccd.",
        ".dcsssssssssscd.",
        ".dccccccccccccd.",
        ".dcsssssssssscd.",
        ".dccccccccccccd.",
        ".dddddddddddddd.",
        ".......dd.......",
        ".......dd.......",
        ".......dd.......",
        ".......dd.......",
        ".......dd.......",
        "................",
        "................",
    ], PLANK_PALETTE)
    return c


def icon_disassemble():
    c = Canvas(16, 16)
    px(c, [
        "......dddddddd..",
        "......dccccccd..",
        "......dcsssscd..",
        "......dccccccd..",
        "......dddddddd..",
        "................",
        "....y...........",
        "...yyy..........",
        "..yyyyy.........",
        "....y...........",
        ".dddddddddd.....",
        ".d........d.....",
        ".d........d.....",
        ".dddddddddd.....",
        "....dd..........",
        "....dd..........",
    ], PLANK_PALETTE)
    return c


def main():
    block_top().save("block/sign_workbench_top.png")
    block_side().save("block/sign_workbench_side.png")
    block_leg().save("block/sign_workbench_leg.png")
    block_back().save("block/sign_workbench_back.png")
    block_sign().save("block/sign_workbench_sign.png")

    base = "gui/sprites/workbench/"
    sprite_panel().save(base + "panel.png")
    for state in ("normal", "hover", "selected"):
        sprite_tab(state).save(base + "tab_" + state + ".png")
        sprite_button(state).save(base + "button_" + state + ".png")
    sprite_inset(12).save(base + "preview.png")
    sprite_output().save(base + "output.png")
    sprite_arrow(False).save(base + "arrow.png")
    sprite_arrow(True).save(base + "arrow_ready.png")
    hint_material().save(base + "hint_material.png")
    hint_dye().save(base + "hint_dye.png")
    hint_frame().save(base + "hint_frame.png")
    hint_board().save(base + "hint_board.png")
    hint_sign().save(base + "hint_sign.png")
    icon_wash().save(base + "icon_wash.png")
    icon_make().save(base + "icon_make.png")
    icon_paint().save(base + "icon_paint.png")
    icon_assemble().save(base + "icon_assemble.png")
    icon_disassemble().save(base + "icon_disassemble.png")
    print("art written to", os.path.normpath(ROOT))


if __name__ == "__main__":
    main()
