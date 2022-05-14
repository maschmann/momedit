package dev.aschmann.momedit.game;

import java.util.HashMap;
import java.util.Map;

public class AddressMap {

    private final Map<String, String> abilities = new HashMap<String, String>();

    private final Map<String, String> base = new HashMap<String, String>();

    private final Map<String, String> books = new HashMap<String, String>();

    private final Map<String, String> nature = new HashMap<String, String>();

    private final Map<String, String> sorcery = new HashMap<String, String>();

    private final Map<String, String> chaos = new HashMap<String, String>();

    private final Map<String, String> life = new HashMap<String, String>();

    private final Map<String, String> death = new HashMap<String, String>();

    private final Map<String, String> arcane = new HashMap<String, String>();

    public AddressMap() {
        initAbilities();
        initBase();
        initBooks();
        initNature();
        initSorcery();
        initChaos();
        initLife();
        initDeath();
        initArcane();
    }

    public static void main() {
        AddressMap map = new AddressMap();
    }

    private void initAbilities() {
        abilities.put("Alchemy", "A4C");
        abilities.put("Warlord", "A4D");
        abilities.put("Chaos Master", "A4E");
        abilities.put("Nature Master", "A4F");
        abilities.put("Sorcery Master", "A50");
        abilities.put("Infernal Power", "A51");
        abilities.put("Divine Power", "A52");
        abilities.put("Sage Master", "A53");
        abilities.put("Channeller", "A54");
        abilities.put("Myrran", "A55");
        abilities.put("Archmage", "A56");
        abilities.put("Mana Focusing", "A57");
        abilities.put("Node Mastery", "A58");
        abilities.put("Famous", "A59");
        abilities.put("Runemaster", "A5A");
        abilities.put("Conjurer", "A5B");
        abilities.put("Charismatic", "A5C");
        abilities.put("Artificer", "A5D");
    }

    private void initBase() {
        base.put("Mana", "C44");
        base.put("Casting Skill", "C46");
        base.put("Money", "D3E");
        base.put("Casting Skill adj.", "D40");
    }

    private void initBooks() {
        books.put("Nature Book", "A42");
        books.put("Sorcery Book", "A44");
        books.put("Chaos Book", "A46");
        books.put("Life Book", "A48");
        books.put("Death Book", "A4A");
    }

    private void initNature() {
        nature.put("Earth to Mud", "C4C");
        nature.put("Resist Elements", "C4D");
        nature.put("Wall Of Stone", "C4E");
        nature.put("Giant Strength", "C4F");
        nature.put("Web", "C50");
        nature.put("War Bears", "C51");
        nature.put("Stone Skin", "C52");
        nature.put("Water Walking", "C53");
        nature.put("Sprites", "C54");
        nature.put("Earth lore", "C55");
        nature.put("Cracks Call", "C56");
        nature.put("Natures eye", "C57");
        nature.put("Ice Bolt", "C58");
        nature.put("Giant Spiders", "C59");
        nature.put("Change Terrain", "C5A");
        nature.put("Pathfinding", "C5B");
        nature.put("Cockatrices", "C5C");
        nature.put("Transmute", "C5D");
        nature.put("Natures Cures", "C5E");
        nature.put("Basilisk", "C5F");
        nature.put("Elemental Armor", "C60");
        nature.put("Petrify", "C61");
        nature.put("Stone Giant", "C62");
        nature.put("Iron Skin", "C63");
        nature.put("Ice Storm", "C64");
        nature.put("Earthquake", "C65");
        nature.put("Gorgons", "C66");
        nature.put("Move Fortress", "C67");
        nature.put("Gaia's Blessing", "C68");
        nature.put("Earth Elemental", "C69");
        nature.put("Regeneration", "C6A");
        nature.put("Behemoth", "C6B");
        nature.put("Entangle", "C6C");
        nature.put("Natures Awareness", "C6D");
        nature.put("Call Lighting", "C6E");
        nature.put("Colossus", "C6F");
        nature.put("Earth Gate", "C70");
        nature.put("Herb Mastery", "C71");
        nature.put("Great Wyrm", "C72");
        nature.put("Nature's Wrath", "C73");
    }

    private void initSorcery() {
        sorcery.put("Resist Magic", "C74");
        sorcery.put("Dispel Magic True", "C75");
        sorcery.put("Floating Island", "C76");
        sorcery.put("Guardian Wind", "C77");
        sorcery.put("Phantom Warriors", "C78");
        sorcery.put("Confusion", "C79");
        sorcery.put("Word of Recall", "C7A");
        sorcery.put("Counter Magic", "C7B");
        sorcery.put("Nagas", "C7C");
        sorcery.put("Psionic Blast", "C7D");
        sorcery.put("Blur", "C7E");
        sorcery.put("Disenchant True", "C7F");
        sorcery.put("Vertigo", "C80");
        sorcery.put("Spell Lock", "C81");
        sorcery.put("Enchant Road", "C82");
        sorcery.put("Flight", "C83");
        sorcery.put("Wind Mastery", "C84");
        sorcery.put("Spell Blast", "C85");
        sorcery.put("Aura of Mastery", "C86");
        sorcery.put("Phantom Beast", "C87");
        sorcery.put("Disjuntion True", "C88");
        sorcery.put("Invisibility", "C89");
        sorcery.put("Wind Walking", "C8A");
        sorcery.put("Banish", "C8B");
        sorcery.put("Storm Giant", "C8C");
        sorcery.put("Air Elemental", "C8D");
        sorcery.put("Mind Storm", "C8E");
        sorcery.put("Stasis", "C8F");
        sorcery.put("Magic Immunity", "C90");
        sorcery.put("Haste", "C91");
        sorcery.put("Djinn", "C92");
        sorcery.put("Spell Ward", "C93");
        sorcery.put("Creature Binding", "C94");
        sorcery.put("Mass Invisibility", "C95");
        sorcery.put("Great UnSummoning", "C96");
        sorcery.put("Spell Binding", "C97");
        sorcery.put("Flying Fortress", "C98");
        sorcery.put("Sky Drake", "C99");
        sorcery.put("Suppress Magic", "C9A");
        sorcery.put("Time Stop", "C9B");
    }

    private void initChaos() {
        chaos.put("Warp Wood", "C9C");
        chaos.put("Disrupt", "C9D");
        chaos.put("Fire Bolt", "C9E");
        chaos.put("Hell Hounds", "C9F");
        chaos.put("Corruption", "CA0");
        chaos.put("Eldritch Weapon", "CA1");
        chaos.put("Wall of Fire", "CA2");
        chaos.put("Shatter", "CA3");
        chaos.put("Warp Creature", "CA4");
        chaos.put("Fire Elemental", "CA5");
        chaos.put("Lightning Bolt", "CA6");
        chaos.put("Fire Giant", "CA7");
        chaos.put("Chaos Channels", "CA8");
        chaos.put("Flame Blade", "CA9");
        chaos.put("Gargoyles", "CAA");
        chaos.put("Fireball", "CAB");
        chaos.put("Doombat", "CAC");
        chaos.put("Raise Volcano", "CAD");
        chaos.put("Immolation", "CAE");
        chaos.put("Chimeras", "CAF");
        chaos.put("Warp Lightning", "CB0");
        chaos.put("Metal Fires", "CB1");
        chaos.put("Chaos Spawn", "CB2");
        chaos.put("Doom Bolt", "CB3");
        chaos.put("Magic Vortex", "CB4");
        chaos.put("Efreet", "CB5");
        chaos.put("Fire Storm", "CB6");
        chaos.put("Warp Reality", "CB7");
        chaos.put("Flame Strike", "CB8");
        chaos.put("Chaos Rift", "CB9");
        chaos.put("Hydra", "CBA");
        chaos.put("Disintegrate", "CBB");
        chaos.put("Meteor Storm", "CBC");
        chaos.put("Great Wasting", "CBD");
        chaos.put("Call Chaos", "CBE");
        chaos.put("Chaos Surge", "CBF");
        chaos.put("Doom Mastery", "CC0");
        chaos.put("Great Drake", "CC1");
        chaos.put("Call the Void", "CC2");
        chaos.put("Armageddon", "CC3");
    }

    private void initLife() {
        life.put("Bless", "CC4");
        life.put("Star Fires", "CCB");
        life.put("Endurance", "CC6");
        life.put("Holy Weapon", "CC7");
        life.put("Healing", "CC8");
        life.put("Holy Armor", "CC9");
        life.put("Just Cause", "CCA");
        life.put("True Light", "CCB");
        life.put("Guardian Spirit", "CCC");
        life.put("Heroism", "CCD");
        life.put("True Sight", "CCE");
        life.put("Plane Shift", "CCF");
        life.put("Resurrection", "CD0");
        life.put("Dispel Evil", "CD1");
        life.put("Planar Seal", "CD2");
        life.put("Unicorns", "CD3");
        life.put("Raise Dead", "CD4");
        life.put("Planar Travel", "CD5");
        life.put("Heavenly Light", "CD6");
        life.put("Prayer", "CD7");
        life.put("Lionhearted", "CD8");
        life.put("Incarnation", "CD9");
        life.put("Invulnerability", "CDA");
        life.put("Righteousness", "CDB");
        life.put("Prosperity", "CDC");
        life.put("Alter of Battle", "CDD");
        life.put("Angel", "CDE");
        life.put("Stream of Life", "CDF");
        life.put("Mass Healing", "CE0");
        life.put("Holy Word", "CE1");
        life.put("High Prayer", "CE2");
        life.put("Inspirations", "CE3");
        life.put("Astral Gate", "CE4");
        life.put("Holy Arms", "CE5");
        life.put("Consecration", "CE6");
        life.put("Life Force", "CE7");
        life.put("Tranquility", "CE8");
        life.put("Crusade", "CE9");
        life.put("Arch Angel", "CEA");
        life.put("Charm of Life", "CEB");
    }

    private void initDeath() {
        death.put("Skeletons", "CEC");
        death.put("Weakness", "CED");
        death.put("Dark Rituals", "CEE");
        death.put("Cloak of Fear", "CEF");
        death.put("Black Sleep", "CF0");
        death.put("Ghouls", "CF1");
        death.put("Life Drain", "CF2");
        death.put("Terror", "CF3");
        death.put("Darkness", "CF4");
        death.put("Mana Leak", "CF5");
        death.put("Drain Power", "CF6");
        death.put("Possession", "CF7");
        death.put("Lycanthropy", "CF8");
        death.put("Black Prayer", "CF9");
        death.put("Black Channels", "CFA");
        death.put("Night Stalker", "CFB");
        death.put("Subversion", "CFC");
        death.put("Wall of Darkness", "CFD");
        death.put("Berserk", "CFE");
        death.put("Shadow Demons", "CFF");
        death.put("Wraith Form", "D00");
        death.put("Wrack", "D01");
        death.put("Evil Presence", "D02");
        death.put("Wraiths", "D03");
        death.put("Cloud of Shadow", "D04");
        death.put("Warp Node", "D05");
        death.put("Black Wind", "D06");
        death.put("Zombie Mastery", "D07");
        death.put("Famine", "D08");
        death.put("Cursed lands", "D09");
        death.put("Cruel Unminding", "D0A");
        death.put("Word of Death", "D0B");
        death.put("Death Knights", "D0C");
        death.put("Death Spell", "D0D");
        death.put("Animate Dead", "D0E");
        death.put("Pestilence", "D0F");
        death.put("Eternal Night", "D10");
        death.put("Evil Omens", "D11");
        death.put("Death Wish", "D12");
        death.put("Demon Lord", "D13");
    }

    private void initArcane() {
        arcane.put("Magic Spirit", "D14");
        arcane.put("Dispel Magic", "D15");
        arcane.put("Summoning Circle", "D16");
        arcane.put("Disenchant Area", "D17");
        arcane.put("Recall Hero", "D18");
        arcane.put("Detect Magic", "D19");
        arcane.put("Enchant Item", "D1A");
        arcane.put("Summon Hero", "D1B");
        arcane.put("Awareness", "D1C");
        arcane.put("Disjunction", "D1D");
        arcane.put("Create Artifact", "D1E");
        arcane.put("Summon Champion", "D1F");
        arcane.put("Spell of Mastery", "D20");
        arcane.put("Spell of Return", "D21");
    }

    public Map<String, String> abilities() {
        return abilities;
    }

    public Map<String, String> base() {
        return base;
    }

    public Map<String, String> books() {
        return books;
    }

    public Map<String, String> nature() {
        return nature;
    }

    public Map<String, String> sorcery() {
        return sorcery;
    }

    public Map<String, String> life() {
        return life;
    }

    public Map<String, String> death() {
        return death;
    }

    public Map<String, String> chaos() {
        return chaos;
    }

    public Map<String, String> arcane() {
        return arcane;
    }
}
