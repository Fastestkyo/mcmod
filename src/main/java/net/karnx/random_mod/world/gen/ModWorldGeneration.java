package net.karnx.random_mod.world.gen;


public class ModWorldGeneration {
    public static void generateModWorldGen() {
        ModOreGeneration.generateOres();
        ModTreeGeneration.generateModTrees();
        ModBushGeneration.generateBushes();
        ModEntitySpawns.addSpawns();
    }
}