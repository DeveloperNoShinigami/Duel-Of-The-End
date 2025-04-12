package com.p1nero.dote.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.worldgen.biome.DOTEBiomeProvider;
import com.p1nero.dote.worldgen.dimension.DOTEChunkGenerator;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * 从暮色得到灵感，在关键方法判断该区是否是指定的位置。
 */
public class PositionPlacement extends StructurePlacement {
    public static final Codec<PositionPlacement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.STRING.fieldOf("structure").forGetter(p -> p.structure)
    ).apply(inst, PositionPlacement::new));

    public final String structure;

    public boolean hasGenerated;

    public PositionPlacement(String structure) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1f, 0, Optional.empty()); // None of these params matter except for possibly flat-world or whatever
        this.structure = structure;
        this.hasGenerated = false;
    }

    public boolean isDOTEPlacementChunk(DOTEChunkGenerator chunkGen, int chunkX, int chunkZ) {
        if (chunkGen.getBiomeSource() instanceof DOTEBiomeProvider) {
            return checkStructure(DOTEStructurePoses.valueOf(this.structure), chunkX << 4, chunkZ << 4);
        }
        return false;
    }

    /**
     * 判断该区块是否符合所指定的结构的位置
     */
    private boolean checkStructure(DOTEStructurePoses structure, int correctX, int correctZ) {
        int deOffsetX = structure.getPos().getX() - structure.getOffsetX();
        int deOffsetZ = structure.getPos().getZ() - structure.getOffsetZ();
        int size = structure.getSize();
        //存在误差，所以用一个范围好，而且大结构需要多个区块
        if (DOTEStructurePoses.valueOf(this.structure) == structure
                && correctX >= deOffsetX - size && correctZ >= deOffsetZ - size
                && correctX <= deOffsetX + size && correctZ <= deOffsetZ + size
                && !hasGenerated) {
            hasGenerated = true;//防止多次生成
            DuelOfTheEndMod.LOGGER.info("Loading structure [" + structure + "] at (" + deOffsetX + ", " + deOffsetZ + ")");
            return true;
        }

        return false;
    }

    @Override
    protected boolean isPlacementChunk(@NotNull ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
        //反正应该或许不会被调用吧
        return false;
    }

    @Override
    public @NotNull StructurePlacementType<?> type() {
        return DOTEStructurePlacementTypes.SPECIFIC_LOCATION_PLACEMENT_TYPE.get();
    }

}
