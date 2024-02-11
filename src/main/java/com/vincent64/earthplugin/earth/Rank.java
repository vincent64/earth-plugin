package com.vincent64.earthplugin.earth;

import static com.vincent64.earthplugin.earth.Requirement.RequirementType.*;

public enum Rank {
    SURVIVOR("Survivor", "survivor",
            new Requirement(DISTANCE_WALKED, 0)),
    WANDERER("Wanderer", "wanderer",
            new Requirement(DISTANCE_WALKED, 20_000)),
    ADVENTURER("Adventurer", "adventurer",
            new Requirement(DISTANCE_WALKED, 50_000)),
    CONQUEROR("Conqueror", "conqueror",
            new Requirement(DISTANCE_WALKED, 100_000)),

    BUILDER("Builder", "builder",
            new Requirement(BLOCK_PLACED, 2000)),
    PIONEER("Pioneer", "pioneer",
            new Requirement(BLOCK_PLACED, 8000)),
    ARCHITECT("Architect", "architect",
            new Requirement(BLOCK_PLACED, 16_000)),

    DIPLOMAT("Diplomat", "diplomat",
            new Requirement(MESSAGES_SENT, 400)),
    EMPEROR("Emperor", "emperor",
            new Requirement(MESSAGES_SENT, 800)),
    SOVEREIGN("Sovereign", "sovereign",
            new Requirement(MESSAGES_SENT, 1500));

    private String name;
    private String id;
    private Requirement requirement;

    Rank(String name, String id, Requirement requirement) {
        this.name = name;
        this.id = id;
        this.requirement = requirement;
    }

    public static Rank getRank(String id) {
        for(Rank rank : values()) {
            if(rank.id.equals(id)) {
                return rank;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Requirement getRequirement() {
        return requirement;
    }
}
