package com.p1nero.dote.worldgen.structure;

import com.p1nero.dote.worldgen.dimension.DOTEChunkGenerator;

import java.awt.*;

/**
 * 有新的建筑加进来只要在这里添加枚举类型即可，BiomeForceLandmark类和ChunkGenerator类会遍历这个枚举类型（顶级优化哈哈）
 * 以后看看能不能优化成json里面直接写类型而不是写数字（EnumCodec还不会）
 * @author LZY
 * @see PositionPlacement
 * @see DOTEChunkGenerator
 */
public enum DOTEStructurePoses {

    //offset应为偏移的方块数量除以四。举例：FINAL偏移61格，应填15 。y填数据包里
    START_POINT(2, 0, 0, 0, 0),//y 10
    BURNING_RUINS(2, 0, 0, 150, 75),//y -10
    ENTRANCE_OF_ABYSS(2,0,0, 250, 75),//y -10
    SUNSET_THRONE(2,0,0, 300, 0),//y -10
    SHADOW_DOJO(2,0,0, 250, -75),//y -10
    BLOOD_SEA_PALACE(2,0,0, 150, -75),//y -10
    HELL_DUELING_ARENA(2,0,0, 10, 0),//y -10
    TRIAL_OF_STAR(2,0,0, 10, 0),//y -10
    END_ARENA(2,0,0, 10, 0),//y -10
    DEMON_SUBDUING_PAGODA(2,0,0, -10, -200);//y -10

    final int size;
    final int offsetX;
    final int offsetZ;
    final Point point;
    DOTEStructurePoses(int size, int offsetX, int offsetZ) {
        this(size, offsetX, offsetZ, 0, 0);
    }

    DOTEStructurePoses(int size, int offsetX, int offsetZ, int x, int z) {
        this.size = size;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.point = new Point(x, z);
    }

    public int getSize() {
        return size;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetZ() {
        return offsetZ;
    }

    public Point getPoint() {
        return point;
    }
}
