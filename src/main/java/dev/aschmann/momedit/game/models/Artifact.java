package dev.aschmann.momedit.game.models;

import java.util.HashMap;
import java.util.Map;

public class Artifact {

    private final int id;

    private int totalOffset;

    private String name;

    private String type;

    private int typeNumeric;

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

    public Artifact(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(int type) {
        this.type = resolveTypeToString(type);
        this.typeNumeric = type;
    }

    public int getTypeNumeric() {
        return typeNumeric;
    }

    public void setTypeNumeric(int typeNumeric) {
        this.typeNumeric = typeNumeric;
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

    private String resolveTypeToString(int type) {
        return switch (type) {
            case 1 -> "Sword";
            case 2 -> "Bow";
            case 3 -> "SwordWand";
            case 4 -> "Wand";
            case 5 -> "Shield";
            case 6 -> "Amulet";
            default -> "unknown";
        };
    }
}
