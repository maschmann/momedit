package dev.aschmann.momedit.game.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Artifact {

    private final int id;

    private int vaultStorage;

    private int offset;

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

    private final int[] enchantments;

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

        enchantments = new int[] {0, 0, 0, 0};
    }

    public static void main(int id) {
        Artifact artifact = new Artifact(id);
    }

    public int getId() {
        return id;
    }

    public int getOffset() {
        return offset;
    }

    public int getOffset(int valOffset) {
        return offset + valOffset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
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
        return movementBonus / 2;
    }

    public void setMovementBonus(int movementBonus) {
        this.movementBonus = movementBonus * 2;
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

    public int getVaultStorage() {
        return vaultStorage;
    }

    public void setVaultStorage(int vaultStorage) {
        this.vaultStorage = vaultStorage;
    }

    public void addEnchantment(String compositeId) {
        String[] idPartials = compositeId.split("_");
        handleEnchantmentUpdate(idPartials[0], Integer.parseInt(idPartials[1]));
    }

    public void removeEnchantment(String compositeId) {
        String[] idPartials = compositeId.split("_");
        // make it a negative value, so we can simply add it
        handleEnchantmentUpdate(idPartials[0], (Integer.parseInt(idPartials[1]) * -1));
    }

    public int getEnchantments1() {
        return enchantments[0];
    }

    public void setEnchantments1(int value) {
        enchantments[0] = value;
    }

    public int getEnchantments2() {
        return enchantments[1];
    }

    public void setEnchantments2(int value) {
        enchantments[1] = value;
    }

    public int getEnchantments3() {
        return enchantments[2];
    }

    public void setEnchantments3(int value) {
        enchantments[2] = value;
    }

    public int getEnchantments4() {
        return enchantments[3];
    }

    public void setEnchantments4(int value) {
        enchantments[3] = value;
    }

    private void handleEnchantmentUpdate(String type, int value) {
        int slot = switch (type) {
            case "47" -> 1;
            case "48" -> 2;
            case "49" -> 3;
            default -> 0; // & 46
        };

        enchantments[slot] = enchantments[slot] + value;
    }
}
