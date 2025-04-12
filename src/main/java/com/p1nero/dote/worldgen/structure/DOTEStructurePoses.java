package com.p1nero.dote.worldgen.structure;

import com.p1nero.dote.util.EntityUtil;
import com.p1nero.dote.worldgen.dimension.DOTEChunkGenerator;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

import java.awt.*;
import java.util.List;

/**
 * 有新的建筑加进来只要在这里添加枚举类型即可，BiomeForceLandmark类和ChunkGenerator类会遍历这个枚举类型（顶级优化哈哈）
 * 以后看看能不能优化成json里面直接写类型而不是写数字（EnumCodec还不会）
 *
 * @author LZY
 * @see PositionPlacement
 * @see DOTEChunkGenerator
 */
public enum DOTEStructurePoses {

    //offset应为偏移的方块数量除以四。举例：FINAL偏移61格，应填15 。y填数据包里
    START_POINT(20, 0, 0, 0, 10, 0, 4),//0
    BURNING_RUINS(20, 0, 0, 150, -10, 75, 4),//1
    ENTRANCE_OF_ABYSS(20, 0, 0, 250, -10, 75, 4),//2
    SUNSET_THRONE(20, 0, 0, 470, 100, -2, 4),//3
    SHADOW_DOJO(20, 0, 0, 250, -10, -75, 4),//4
    BLOOD_SEA_PALACE(20, 0, 0, 150, -10, -75, 4),//5
    HELL_DUELING_ARENA(20, 0, 0, -109, 100, 1, 4),//6
    TRIAL_OF_STAR(20, 0, 0, 199, 99, -1,4),//7
    END_ARENA(20, 0, 0, 757, 111, 1, 4),//8
    DEMON_SUBDUING_PAGODA(20, 0, 0, -10, -10, -200, 4);//9

    final int size;
    final int offsetX;
    final int offsetZ;
    final Point point;
    final Vec3i pos;
    final int maxPlayerCnt;

    DOTEStructurePoses(int size, int offsetX, int offsetZ) {
        this(size, offsetX, offsetZ, 0, 0, 0, 4);
    }

    DOTEStructurePoses(int size, int offsetX, int offsetZ, int x, int y, int z, int maxPlayerCnt) {
        this.size = size;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.point = new Point(x, z);
        this.pos = new Vec3i(x, y, z);
        this.maxPlayerCnt = maxPlayerCnt;
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

    public Vec3i getPos() {
        return pos;
    }

    public void teleportTo(ServerPlayer serverPlayer){
        serverPlayer.teleportTo(pos.getX(), pos.getY(), pos.getZ());
        List<Player> currentPlayers = EntityUtil.getNearByPlayers(serverPlayer, Math.max(offsetX, offsetZ));
        if(currentPlayers.size() > maxPlayerCnt) {
            serverPlayer.setGameMode(GameType.SPECTATOR);
            serverPlayer.displayClientMessage(Component.translatable("tip.dote.press_enter_to_exit"), false);
        } else {
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 5));
        }
    }

}
