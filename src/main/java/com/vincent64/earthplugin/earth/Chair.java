package com.vincent64.earthplugin.earth;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;

import java.util.UUID;

public class Chair {
    private UUID sitter;
    private Arrow arrow;
    private Block chair;

    public Chair(UUID sitter, Arrow arrow, Block chair) {
        this.sitter = sitter;
        this.arrow = arrow;
        this.chair = chair;
    }

    public void destroyChair() {
        arrow.remove();
    }

    public UUID getSitter() {
        return sitter;
    }

    public Arrow getArrow() {
        return arrow;
    }

    public Block getChair() {
        return chair;
    }
}
