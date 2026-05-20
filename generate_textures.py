#!/usr/bin/env python3
"""
Generate all PNG textures for the Ender Rooms mod.
Run once after cloning:  python generate_textures.py
Requires Pillow:         pip install Pillow
"""
import os
from PIL import Image

BASE = r"C:\Projects\ender_rooms\src\main\resources\assets\ender_rooms\textures"

# ── Palette ──────────────────────────────────────────────────────────────────
TRANS  = (  0,   0,   0,   0)
VOID   = ( 10,   0,  20, 255)   # near-black void purple
D_PUR  = ( 45,   0,  85, 255)   # dark purple
M_PUR  = ( 90,  10, 160, 255)   # mid purple
B_PUR  = (150,  30, 220, 255)   # bright purple
P_PUR  = (200, 130, 255, 255)   # pale/highlight purple
C_BRT  = (  0, 220, 255, 255)   # bright cyan
C_DIM  = (  0, 110, 170, 255)   # dim cyan
WHITE  = (230, 200, 255, 255)   # white-purple tint

def img16(bg=VOID):
    return Image.new("RGBA", (16, 16), bg)

def put(px, coords, color):
    for (x, y) in coords:
        if 0 <= x < 16 and 0 <= y < 16:
            px[x, y] = color

def save(image, rel):
    path = os.path.join(BASE, rel)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    image.save(path, "PNG")
    print(f"  saved  {path}")


# ── ender_door_bottom ─────────────────────────────────────────────────────────
# A dark void panel with two glowing cyan rune windows (left panel top,
# right panel bottom) and a bright-purple border.
def door_bottom():
    im = img16(VOID)
    px = im.load()
    # border
    for x in range(16):
        for y in range(16):
            if x == 0 or x == 15 or y == 0 or y == 15:
                px[x, y] = B_PUR
            elif x == 1 or x == 14 or y == 1 or y == 14:
                px[x, y] = M_PUR
    # vertical centre divider
    for y in range(2, 14):
        px[7, y] = D_PUR
        px[8, y] = D_PUR
    # horizontal mid rail
    for x in range(2, 15):
        px[x, 7] = D_PUR
        px[x, 8] = D_PUR
    # left-top panel: cyan glow box  (cols 2-6, rows 2-6)
    for x in range(2, 7):
        for y in range(2, 7):
            if x in (2, 6) or y in (2, 6):
                px[x, y] = C_BRT
            else:
                px[x, y] = C_DIM
    # right-bottom panel: same (cols 9-13, rows 9-13)
    for x in range(9, 14):
        for y in range(9, 14):
            if x in (9, 13) or y in (9, 13):
                px[x, y] = C_BRT
            else:
                px[x, y] = C_DIM
    # corner highlights
    put(px, [(0,0),(0,15),(15,0),(15,15)], P_PUR)
    return im


# ── ender_door_top ────────────────────────────────────────────────────────────
# Same border; an ender-eye glyph occupies the centre of each panel.
def door_top():
    im = img16(VOID)
    px = im.load()
    # border
    for x in range(16):
        for y in range(16):
            if x == 0 or x == 15 or y == 0 or y == 15:
                px[x, y] = B_PUR
            elif x == 1 or x == 14 or y == 1 or y == 14:
                px[x, y] = M_PUR
    # dividers
    for y in range(2, 14):
        px[7, y] = D_PUR; px[8, y] = D_PUR
    for x in range(2, 15):
        px[x, 7] = D_PUR; px[x, 8] = D_PUR
    # left-top panel glyph  (eye shape, centre 4,4)
    eye_l = [(4,2),(3,3),(5,3),(2,4),(6,4),(3,5),(5,5),(4,6)]
    put(px, eye_l, C_BRT)
    px[4, 4] = C_DIM          # iris
    px[4, 3] = M_PUR           # pupil hint
    # right-bottom panel glyph (centre 11,11)
    eye_r = [(11,9),(10,10),(12,10),(9,11),(13,11),(10,12),(12,12),(11,13)]
    put(px, eye_r, C_BRT)
    px[11, 11] = C_DIM
    px[11, 10] = M_PUR
    # corner highlights
    put(px, [(0,0),(0,15),(15,0),(15,15)], P_PUR)
    return im


# ── item/ender_door ───────────────────────────────────────────────────────────
# Door silhouette (2-px frame, 12 wide) on transparent background.
def item_door():
    im = img16(TRANS)
    px = im.load()
    # outer frame
    for x in range(2, 14):
        for y in range(0, 16):
            if x in (2, 13) or y in (0, 15):
                px[x, y] = B_PUR
            elif x in (3, 12) or y in (1, 14):
                px[x, y] = M_PUR
            else:
                px[x, y] = VOID
    # horizontal centre rail
    for x in range(3, 13):
        px[x, 7] = D_PUR; px[x, 8] = D_PUR
    # top panel rune
    for x in range(5, 11):
        px[x, 3] = C_DIM
        px[x, 6] = C_DIM
    for y in range(3, 7):
        px[5, y] = C_DIM; px[10, y] = C_DIM
    px[7, 4] = C_BRT; px[8, 4] = C_BRT
    px[7, 5] = C_BRT; px[8, 5] = C_BRT
    # door handle
    px[11, 9] = C_BRT; px[11, 10] = C_BRT; px[11, 11] = C_BRT
    px[10, 9] = C_DIM
    return im


# ── item/enhanced_eye_of_ender ────────────────────────────────────────────────
# Elliptical eye, purple iris, dark pupil, pale highlight, cyan sparkles.
def item_eye():
    im = img16(TRANS)
    px = im.load()
    # outer glow ring (rough ellipse)
    glow = [
        (5,1),(6,1),(7,1),(8,1),(9,1),(10,1),
        (3,2),(4,2),(11,2),(12,2),
        (2,3),(13,3),(2,4),(13,4),
        (2,5),(13,5),(2,6),(13,6),(2,7),(13,7),
        (2,8),(13,8),(2,9),(13,9),(2,10),(13,10),
        (3,11),(4,11),(11,11),(12,11),
        (5,12),(6,12),(7,12),(8,12),(9,12),(10,12),
    ]
    put(px, glow, (*B_PUR[:3], 80))   # semi-transparent outer glow

    # eye white (ellipse)
    eye_white = [
        (5,3),(6,3),(7,3),(8,3),(9,3),(10,3),
        (4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4),(11,4),
        (4,5),(5,5),(6,5),(7,5),(8,5),(9,5),(10,5),(11,5),
        (4,6),(5,6),(6,6),(7,6),(8,6),(9,6),(10,6),(11,6),
        (4,7),(5,7),(6,7),(7,7),(8,7),(9,7),(10,7),(11,7),
        (4,8),(5,8),(6,8),(7,8),(8,8),(9,8),(10,8),(11,8),
        (4,9),(5,9),(6,9),(7,9),(8,9),(9,9),(10,9),(11,9),
        (5,10),(6,10),(7,10),(8,10),(9,10),(10,10),
    ]
    put(px, eye_white, WHITE)

    # iris (purple ellipse, inset 1)
    iris = [
        (6,4),(7,4),(8,4),(9,4),
        (5,5),(6,5),(7,5),(8,5),(9,5),(10,5),
        (5,6),(6,6),(7,6),(8,6),(9,6),(10,6),
        (5,7),(6,7),(7,7),(8,7),(9,7),(10,7),
        (5,8),(6,8),(7,8),(8,8),(9,8),(10,8),
        (5,9),(6,9),(7,9),(8,9),(9,9),(10,9),
        (6,10),(7,10),(8,10),(9,10),
    ]
    put(px, iris, B_PUR)

    # pupil (dark centre)
    pupil = [
        (7,5),(8,5),(7,6),(8,6),(6,6),(9,6),
        (6,7),(7,7),(8,7),(9,7),
        (6,8),(7,8),(8,8),(9,8),
        (7,9),(8,9),
    ]
    put(px, pupil, VOID)

    # highlight
    put(px, [(6,5),(7,5)], WHITE)
    put(px, [(6,6)], (*WHITE[:3], 160))

    # sparkle tips
    put(px, [(7,0),(8,0),(7,15),(8,15),(0,7),(0,8),(15,7),(15,8)], C_BRT)
    put(px, [(1,1),(14,1),(1,13),(14,13)], C_DIM)

    return im


# ── Main ──────────────────────────────────────────────────────────────────────
if __name__ == "__main__":
    textures = {
        r"block\ender_door_bottom.png":        door_bottom(),
        r"block\ender_door_top.png":           door_top(),
        r"item\ender_door.png":                item_door(),
        r"item\enhanced_eye_of_ender.png":     item_eye(),
    }
    print("Generating Ender Rooms textures...")
    for rel_path, image in textures.items():
        save(image, rel_path)
    print(f"\nDone — {len(textures)} textures written.")
