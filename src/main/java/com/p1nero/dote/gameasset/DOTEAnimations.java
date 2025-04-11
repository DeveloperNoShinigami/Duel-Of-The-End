package com.p1nero.dote.gameasset;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.client.DOTESounds;
import com.p1nero.dote.entity.ai.epicfight.api.IModifyAttackSpeedEntityPatch;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DOTEAnimations {
    public static StaticAnimation S_STEP_BACKWARD;
    public static StaticAnimation WIND_SLASH;
    public static StaticAnimation LION_CLAW;


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(DuelOfTheEndMod.MOD_ID, DOTEAnimations::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;

        S_STEP_BACKWARD = new DodgeAnimation(0.05F, 0.55F, "biped/dodge/step_backward", 0.6F, 1.65F, biped)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.20F, (entitypatch, animation, params) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.playSound(DOTESounds.DODGE.get(), 1.5F, 1.5F);
                            entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                        }, AnimationEvent.Side.CLIENT)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        WIND_SLASH = new AttackAnimation(0.2F, "biped/skill/wind_slash", biped,
                new AttackAnimation.Phase(.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, Boolean.TRUE)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);

        LION_CLAW = new AttackAnimation(0.08F, 1.54F, 1.55F, 1.6F, 3.0F, null, biped.toolR, "biped/skill/lion_claw", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get()).addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addEvents(
                        AnimationEvent.TimeStampedEvent.create(0.2F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(EpicFightSounds.WHOOSH_BIG.get()));


        if (WOMAnimations.TIME_TRAVEL != null) {
            WOMAnimations.TIME_TRAVEL.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> {
                if (livingEntityPatch instanceof IModifyAttackSpeedEntityPatch patch) {
                    return patch.getAttackSpeed();
                }
                return 1.0F;
            }));
        }

    }

}


