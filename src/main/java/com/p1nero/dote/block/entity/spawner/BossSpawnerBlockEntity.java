package com.p1nero.dote.block.entity.spawner;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.entity.api.HomePointEntity;
import com.p1nero.dote.entity.custom.boss.DOTEBoss;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import yesman.epicfight.world.entity.WitherGhostClone;

public abstract class BossSpawnerBlockEntity<T extends DOTEBoss> extends EntitySpawnerBlockEntity<T> implements GeoBlockEntity {
	protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, entityType, pos, state);
	}


	public boolean canSummon(BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit) {
		return this.getMyEntity() == null && player.getItemInHand(hand).is(DOTEItems.IMMORTALESSENCE.get());
	}

	public void summonParticles(ServerLevel serverLevel, Player player, BlockPos pos){
		for (int i = 0; i < 10; i++) {
			double rx = pos.getX() + serverLevel.getRandom().nextFloat();
			double ry = pos.getY() + serverLevel.getRandom().nextFloat();
			double rz = pos.getZ() + serverLevel.getRandom().nextFloat();
			serverLevel.sendParticles(ParticleTypes.SOUL, rx, ry + 2.0F, rz, 1, 0.0D, 0.01D, 0.0D, 0.01);
		}
	}

	public void onSummonFail(ServerLevel serverLevel, Player pPlayer, BlockPos pos){
		pPlayer.displayClientMessage(DuelOfTheEndMod.getInfo("tip1").append(this.getEntityType().getDescription()), true);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState state, BlockEntity blockEntity) {
		if(blockEntity instanceof BossSpawnerBlockEntity<?> spawnerBlockEntity){
			if(pLevel instanceof ServerLevel serverLevel){
				if(spawnerBlockEntity.getSpawnerParticle() != null){
					double rx = pPos.getX() + pLevel.getRandom().nextFloat();
					double ry = pPos.getY() + 1 + pLevel.getRandom().nextFloat();
					double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
					serverLevel.sendParticles(spawnerBlockEntity.getSpawnerParticle(), rx, ry, rz ,1, 0.0D, 0.0D, 0.0D, 0);
				}
				if(spawnerBlockEntity.myEntity instanceof HomePointEntity homePointEntity){
					if(spawnerBlockEntity.getBorderParticle() != null && spawnerBlockEntity.myEntity != null){
						for (int angle = 0; angle < 360; angle += 2) {
							double radians = Math.toRadians(angle);
							int xOffset = (int) Math.round(homePointEntity.getHomeRadius() * Math.cos(radians));
							int zOffset = (int) Math.round(homePointEntity.getHomeRadius() * Math.sin(radians));
							serverLevel.sendParticles(spawnerBlockEntity.getBorderParticle(), pPos.getX() + xOffset, pPos.getY() + 1, pPos.getZ() + zOffset, 1, 0.0D, 0.1D, 0.0D, 0.01);
						}
					}

					int r = (int) (homePointEntity.getHomeRadius() + 3);//要比实际的大一点点，防止在边缘偷刀
					for(LivingEntity livingEntity : pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(pPos.offset(-r, -r, -r), pPos.offset(r, r, r)))){

						//跳过神王远程攻击物
						if(livingEntity instanceof WitherGhostClone){
							continue;
						}
						//同步boss，以防死后boss对象丢失
						if((spawnerBlockEntity.entityType.equals(livingEntity.getType())) && livingEntity instanceof DOTEBoss){
							spawnerBlockEntity.myEntity = livingEntity;
						}
					}
				}

				//击败boss清空状态
				if(spawnerBlockEntity.myEntity == null || !spawnerBlockEntity.myEntity.isAlive()){
					spawnerBlockEntity.myEntity = null;
                }
			}
		}

	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, this::deployAnimController));
	}

	protected <E extends BossSpawnerBlockEntity<?>> PlayState deployAnimController(final AnimationState<E> state) {
		return state.setAndContinue(IDLE);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

}
