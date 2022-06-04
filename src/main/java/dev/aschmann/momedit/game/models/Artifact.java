package dev.aschmann.momedit.game.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Artifact {

    private final int id;

    private int totalOffset;

    private String name;

    private int type;

    private String typeString;

    private int graphics;

    private int manaPrice;

    private int attackBonus;

    private int hitBonus;

    private int defenseBonus;

    private int movementBonus;

    private int resistanceBonus;

    private int spellSkill;

    private int spellSave;

    private int spellCharges;

    private int spell;

    private final Map<Integer, String> types;

    public Artifact(int id) {
        this.id = id;

        types = new HashMap<>();
        types.put(1, "Sword");
        types.put(2, "Bow");
        types.put(3, "SwordWand");
        types.put(4, "Wand");
        types.put(5, "Shield");
        types.put(6, "Amulet");
    }

    public static void main(int id) {
        Artifact artifact = new Artifact(id);
    }

    public int getId() {
        return id;
    }

    public int getTotalOffset() {
        return totalOffset;
    }

    public void setTotalOffset(int totalOffset) {
        this.totalOffset = totalOffset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        this.typeString = resolveTypeToString(type);
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.type = resolveTypeToInt(typeString);
        this.typeString = typeString;
    }

    public int getGraphics() {
        return graphics;
    }

    public void setGraphics(int graphics) {
        this.graphics = graphics;
    }

    public int getManaPrice() {
        return manaPrice;
    }

    public void setManaPrice(int manaPrice) {
        this.manaPrice = manaPrice;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getHitBonus() {
        return hitBonus;
    }

    public void setHitBonus(int hitBonus) {
        this.hitBonus = hitBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }

    public int getMovementBonus() {
        return movementBonus;
    }

    public void setMovementBonus(int movementBonus) {
        this.movementBonus = movementBonus;
    }

    public int getResistanceBonus() {
        return resistanceBonus;
    }

    public void setResistanceBonus(int resistanceBonus) {
        this.resistanceBonus = resistanceBonus;
    }

    public int getSpellSkill() {
        return spellSkill;
    }

    public void setSpellSkill(int spellSkill) {
        this.spellSkill = spellSkill;
    }

    public int getSpellSave() {
        return spellSave;
    }

    public void setSpellSave(int spellSave) {
        this.spellSave = spellSave;
    }

    public int getSpellCharges() {
        return spellCharges;
    }

    public void setSpellCharges(int spellCharges) {
        this.spellCharges = spellCharges;
    }

    public int getSpell() {
        return spell;
    }

    public void setSpell(int spell) {
        this.spell = spell;
    }

    public String resolveTypeToString(int type) {
        return types.getOrDefault(type, "unknown");
    }

    public int resolveTypeToInt(String type) {
        // circumvent mutability issue in lambdas >.<
        AtomicInteger result = new AtomicInteger();
        if (types.containsValue(type)) {
            types.forEach((key, value) -> {
                if (value.equals(type)) {
                    result.set(key);
                }
            });
        }

        return result.get();
    }

    public Map<Integer, String> getTypes() {
        return types;
    }
}
