package me.frandma.sausage.util;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.structure.Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureLocator {
//  private static final int SEARCH_RADIUS_CHUNKS = 100;
//  public static Map<Structure, List<ChunkPos>> getAllStructures(long seed, int centerX, int centerZ) {
//    Map<Structure, List<ChunkPos>> structures = new HashMap<>();
//
//    structures.put(Structure.DESERT_PYRAMID,
//        getRandomSpreadStructures(seed, Structure.DESERT_PYRAMID, centerX, centerZ));
//    structures.put(Structure.BURIED_TREASURE,
//        getRandomSpreadStructures(seed, Structure.BURIED_TREASURE, centerX, centerZ));
//    structures.put(Structure.OCEAN_MONUMENT,
//        getMonumentStructures(seed, centerX, centerZ));
//
//    return structures;
//  }
//  private static List<ChunkPos> getRandomSpreadStructures(long seed, Structure structure, int centerX, int centerZ) {
//    List<ChunkPos> positions = new ArrayList<>();
//    RandomSpreadStructurePlacement placement = (RandomSpreadStructurePlacement) structure.getPlacement();
//    int spacing = placement.getSpacing();
//    int separation = placement.getSeparation();
//    int salt = placement.getSalt();
//    for (int x = -SEARCH_RADIUS_CHUNKS; x <= SEARCH_RADIUS_CHUNKS; x++) {
//      for (int z = -SEARCH_RADIUS_CHUNKS; z <= SEARCH_RADIUS_CHUNKS; z++) {
//        int chunkX = centerX + x;
//        int chunkZ = centerZ + z;
//        RandomSeed.XoroshiroSeed
//        Random random = new XoroshiroRandomSeed(seed + salt).nextLong();
//        int regionX = Math.floorDiv(chunkX, spacing);
//        int regionZ = Math.floorDiv(chunkZ, spacing);
//        random.setSeed(regionX * 341873128712L + regionZ * 132897987541L);
//        int offsetX = random.nextInt(spacing - separation);
//        int offsetZ = random.nextInt(spacing - separation);
//        if (chunkX == regionX * spacing + offsetX &&
//            chunkZ == regionZ * spacing + offsetZ) {
//          positions.add(new ChunkPos(chunkX, chunkZ));
//        }
//      }
//    }
//    return positions;
//  }
//
//  private static List<ChunkPos> getMonumentStructures(long seed, int centerX, int centerZ) {
//    List<ChunkPos> positions = new ArrayList<>();
//    StructurePlacement placement = Structure.OCEAN_MONUMENT.getPlacement();
//    int spacing = 32;
//    int salt = ((RandomSpreadStructurePlacement) placement).getSalt();
//    for (int x = -SEARCH_RADIUS_CHUNKS; x <= SEARCH_RADIUS_CHUNKS; x++) {
//      for (int z = -SEARCH_RADIUS_CHUNKS; z <= SEARCH_RADIUS_CHUNKS; z++) {
//        int chunkX = centerX + x;
//        int chunkZ = centerZ + z;
//        int gridX = Math.floorDiv(chunkX, spacing);
//        int gridZ = Math.floorDiv(chunkZ, spacing);
//        Random random = new XoroshiroRandomSeed(seed + salt).nextLong();
//        random.setSeed(gridX * 16384L + gridZ * 32768L);
//        gridX *= spacing;
//        gridZ *= spacing;
//        gridX += random.nextInt(spacing - 8);
//        gridZ += random.nextInt(spacing - 8);
//        if (chunkX == gridX && chunkZ == gridZ) {
//          positions.add(new ChunkPos(chunkX, chunkZ));
//        }
//      }
//    }
//    return positions;
//  }
}
