package mcjty.rftoolsbase.modules.informationscreen.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mcjty.lib.typed.TypedMap;
import mcjty.rftoolsbase.modules.informationscreen.InformationScreenModule;
import mcjty.rftoolsbase.modules.informationscreen.blocks.InformationScreenTileEntity;
import mcjty.rftoolsbase.modules.informationscreen.network.PacketGetMonitorLog;
import mcjty.rftoolsbase.setup.RFToolsBaseMessages;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Direction;

import javax.annotation.Nonnull;

public class InformationScreenRenderer implements BlockEntityRenderer<InformationScreenTileEntity> {

    public InformationScreenRenderer(BlockEntityRendererProvider.Context dispatcher) {
    }

    @Override
    public void render(InformationScreenTileEntity te, float v, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int i, int i1) {
        long t = System.currentTimeMillis();
        if (t - te.getLastUpdateTime() > 250) {
            RFToolsBaseMessages.INSTANCE.sendToServer(new PacketGetMonitorLog(te.getBlockPos()));
            te.setLastUpdateTime(t);
        }
        Direction orientation = te.getBlockOrientation();
        if (orientation == null) {
            return;
        }

        te.getInfo().ifPresent(h -> {
            TypedMap data = te.getClientData();
            h.render(te.getMode(), matrixStack, buffer, data, orientation, 0.3f);
        });
    }

    public static void register() {
        BlockEntityRenderers.register(InformationScreenModule.TYPE_INFORMATION_SCREEN.get(), InformationScreenRenderer::new);
    }
}
