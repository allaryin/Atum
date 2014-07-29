package com.teammetallurgy.atum;

import com.teammetallurgy.atum.blocks.AtumBlocks;
import com.teammetallurgy.atum.blocks.BlockFlax;
import com.teammetallurgy.atum.blocks.BlockPalmSapling;
import com.teammetallurgy.atum.entity.*;
import com.teammetallurgy.atum.items.AtumItems;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import java.util.Random;

public class AtumEventListener {
	@SubscribeEvent
	public void onFallDamage(LivingFallEvent event) {
		if (event.entity instanceof EntityGhost || event.entity instanceof EntityPharaoh) {
			event.distance = 0.0F;
		}

	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if (event.source.getDamageType().equals("drown") && (event.entity instanceof EntityPharaoh || event.entity instanceof EntityGhost || event.entity instanceof EntityMummy || event.entity instanceof EntityDustySkeleton || event.entity instanceof EntityStoneSoldier)) {
			event.setCanceled(true);
		}

	}

	@SubscribeEvent
	public boolean onBonemeal(BonemealEvent event) {
		if (event.world.isRemote) {
			return true;
		} else {
			Block block = event.world.getBlock(event.x, event.y, event.z);
			if (block == AtumBlocks.BLOCK_PALMSAPLING) {
				((BlockPalmSapling) ((BlockPalmSapling) AtumBlocks.BLOCK_PALMSAPLING)).growTree(event.world, event.x, event.y, event.z, new Random());
				event.setResult(Result.ALLOW);
			}

			if (block == AtumBlocks.BLOCK_FLAX && event.world.getBlockMetadata(event.x, event.y, event.z) < 5) {
				((BlockFlax) ((BlockFlax) AtumBlocks.BLOCK_FLAX)).fertilize(event.world, event.x, event.y, event.z);
				event.setResult(Result.ALLOW);
			}

			return false;
		}
	}

	@SubscribeEvent
	public boolean onHoeEvent(UseHoeEvent event) {
		Block block = event.world.getBlock(event.x, event.y, event.z);
		if (block == AtumBlocks.BLOCK_FERTILESOIL) {
			byte block2 = 0;
			if (event.current.getItem() == AtumItems.gebsBlessing) {
				block2 = 4;
			}

			event.world.setBlock(event.x, event.y, event.z, AtumBlocks.BLOCK_FERTILESOILTILLED);
			event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, block2, 2);
			event.setResult(Result.ALLOW);
			event.world.playSoundEffect((double) event.x, (double) event.y, (double) event.z, block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			return true;
		} else if ((block == Blocks.dirt || block == Blocks.grass) && event.current.getItem() == AtumItems.gebsBlessing) {
			event.world.setBlock(event.x, event.y, event.z, AtumBlocks.BLOCK_FERTILESOILTILLED);
			event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, 12, 2);
			event.setResult(Result.ALLOW);
			event.world.playSoundEffect((double) event.x, (double) event.y, (double) event.z, block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			return true;
		} else {
			return false;
		}
	}
}