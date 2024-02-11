package com.vincent64.earthplugin.earth;

public class Requirement {
    private RequirementType type;
    private int level;

    public Requirement(RequirementType type, int level) {
        this.type = type;
        this.level = level;
    }

    public RequirementType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public enum RequirementType {
        DISTANCE_WALKED("Distance walked"),
        BLOCK_PLACED("Blocks placed"),
        MESSAGES_SENT("Messages sent");

        private String name;

        RequirementType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
