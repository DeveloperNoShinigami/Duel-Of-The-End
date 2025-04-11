package com.p1nero.dote.event;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.DOTEBlockEntities;
import com.p1nero.dote.block.renderer.BetterStructureBlockRenderer;
import com.p1nero.dote.entity.DOTEEntities;
import com.p1nero.dote.entity.custom.boss.dark_advance.client.DarkAdvanceRenderer;
import com.p1nero.dote.entity.custom.boss.goldenflame.client.BlackHoleRenderer;
import com.p1nero.dote.entity.custom.boss.goldenflame.client.FlameCircleRenderer;
import com.p1nero.dote.entity.custom.boss.goldenflame.client.GoldenFlamePatchedRenderer;
import com.p1nero.dote.entity.custom.boss.goldenflame.client.GoldenFlameRenderer;
import com.p1nero.dote.entity.custom.boss.liu_guang.client.LiuGuangRenderer;
import com.p1nero.dote.entity.custom.boss.ms_abyss.client.MsAbyssRenderer;
import com.p1nero.dote.entity.custom.boss.reaper.client.ReaperRenderer;
import com.p1nero.dote.entity.custom.boss.sand_captain.client.SandCaptainRenderer;
import com.p1nero.dote.entity.custom.boss.senbai.client.SenbaiRenderer;
import com.p1nero.dote.entity.custom.boss.slaughter_general.client.SlaughterGeneralRenderer;
import com.p1nero.dote.entity.custom.npc.abyss_dweller.client.AbyssDwellerRenderer;
import com.p1nero.dote.item.model.GoldenDragonArmorModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid = DuelOfTheEndMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        //BOSS
        EntityRenderers.register(DOTEEntities.REAPER.get(), ReaperRenderer::new);
        EntityRenderers.register(DOTEEntities.DARK_ADVANCE.get(), DarkAdvanceRenderer::new);
        EntityRenderers.register(DOTEEntities.SAND_CAPTAIN.get(), SandCaptainRenderer::new);
        EntityRenderers.register(DOTEEntities.SENBAI_DEVIL.get(), SenbaiRenderer::new);
        EntityRenderers.register(DOTEEntities.SLAUGHTER_GENERAL.get(), SlaughterGeneralRenderer::new);

        EntityRenderers.register(DOTEEntities.GOLDEN_FLAME.get(), GoldenFlameRenderer::new);
        EntityRenderers.register(DOTEEntities.BLACK_HOLE.get(), BlackHoleRenderer::new);
        EntityRenderers.register(DOTEEntities.FLAME_CIRCLE.get(), FlameCircleRenderer::new);

        EntityRenderers.register(DOTEEntities.MS_ABYSS.get(), MsAbyssRenderer::new);
        EntityRenderers.register(DOTEEntities.LIU_GUANG.get(), LiuGuangRenderer::new);

        //NPC
        EntityRenderers.register(DOTEEntities.ABYSS_DWELLER.get(), AbyssDwellerRenderer::new);

    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(DOTEBlockEntities.BETTER_STRUCTURE_BLOCK_ENTITY.get(), BetterStructureBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GoldenDragonArmorModel.LAYER_LOCATION, GoldenDragonArmorModel::createBodyLayer);
    }

    /**
     * 需要先绑定Patch和 Armature
     * {@link DOTEEntities#setArmature(ModelBuildEvent.ArmatureBuild)}
     * {@link DOTEEntities#setPatch(EntityPatchRegistryEvent)}
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderPatched(PatchedRenderersEvent.Add event) {
        EntityRendererProvider.Context context = event.getContext();
        //BOSS
        event.addPatchedEntityRenderer(DOTEEntities.GOLDEN_FLAME.get(), (entityType) -> new GoldenFlamePatchedRenderer(() -> Meshes.SKELETON, context, entityType).initLayerLast(context, entityType));
        //NPC
        event.addPatchedEntityRenderer(DOTEEntities.ABYSS_DWELLER.get(), (entityType) -> new PHumanoidRenderer<>(() -> Meshes.BIPED, context, entityType).initLayerLast(context, entityType));
    }

}