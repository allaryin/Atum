package com.teammetallurgy.atum;

import net.minecraftforge.client.event.EntityViewRenderEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AtumFogEventListener {

    @SubscribeEvent
    public void renderFog(EntityViewRenderEvent.RenderFogEvent event) {
        if (event.entity.dimension == AtumConfig.DIMENSION_ID) {
            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
            GL11.glFogf(GL11.GL_FOG_DENSITY, 0.08F);
        }
    }
    
}
