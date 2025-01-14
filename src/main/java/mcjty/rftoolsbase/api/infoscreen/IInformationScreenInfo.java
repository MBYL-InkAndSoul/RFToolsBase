package mcjty.rftoolsbase.api.infoscreen;

import com.mojang.blaze3d.vertex.PoseStack;
import mcjty.lib.typed.TypedMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;

import javax.annotation.Nonnull;

/**
 * Implement this capability based interface in your TE if you want the information screen
 * from RFTools Base to be able to display information from your block.
 */
public interface IInformationScreenInfo {

    public static final int MODE_POWER = 0;            // General power screen
    public static final int MODE_POWER_GRAPHICAL = 1;  // Graphical power screen

    /**
     * Return a list of supported modes. Index MODE_POWER and MODE_POWER_GRAPHICAL are reserved.
     * Other indices can be used by your block
     */
    int[] getSupportedModes();

    /**
     * The information screen will call tick on this so that timed information calculations
     * (like rf/tick) can happen
     */
    void tick();

    /**
     * Called server-side to get information that can be used to render the needed info client-side
     */
    @Nonnull
    TypedMap getInfo(int mode);

    /**
     * Called client-side to actually render the information
     */
    void render(int mode, PoseStack matrixStack, MultiBufferSource buffer, @Nonnull TypedMap info, Direction orientation, double scale);
}
