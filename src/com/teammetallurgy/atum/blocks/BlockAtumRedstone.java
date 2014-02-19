package com.teammetallurgy.atum.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAtumRedstone extends Block {

	public BlockAtumRedstone(int par1) {
		super(par1, Material.rock);
		this.setUnlocalizedName("atum:redstoneOre");
		this.setTextureName("atum:AtumRedstone");
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 1 ? 9 : 0;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		this.glow(par1World, par2, par3, par4);
		super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	@Override
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		this.glow(par1World, par2, par3, par4);
		super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		this.glow(par1World, par2, par3, par4);
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}

	private void glow(World par1World, int par2, int par3, int par4) {
		this.sparkle(par1World, par2, par3, par4);
		int meta = par1World.getBlockId(par2, par3, par4);
		if(meta != 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
		}

	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if(meta == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if(meta == 1) {
			this.sparkle(par1World, par2, par3, par4);
		}

	}

	private void sparkle(World par1World, int par2, int par3, int par4) {
		Random random = par1World.rand;
		double d0 = 0.0625D;

		for(int l = 0; l < 6; ++l) {
			double d1 = (double) ((float) par2 + random.nextFloat());
			double d2 = (double) ((float) par3 + random.nextFloat());
			double d3 = (double) ((float) par4 + random.nextFloat());
			if(l == 0 && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4)) {
				d2 = (double) (par3 + 1) + d0;
			}

			if(l == 1 && !par1World.isBlockOpaqueCube(par2, par3 - 1, par4)) {
				d2 = (double) (par3 + 0) - d0;
			}

			if(l == 2 && !par1World.isBlockOpaqueCube(par2, par3, par4 + 1)) {
				d3 = (double) (par4 + 1) + d0;
			}

			if(l == 3 && !par1World.isBlockOpaqueCube(par2, par3, par4 - 1)) {
				d3 = (double) (par4 + 0) - d0;
			}

			if(l == 4 && !par1World.isBlockOpaqueCube(par2 + 1, par3, par4)) {
				d1 = (double) (par2 + 1) + d0;
			}

			if(l == 5 && !par1World.isBlockOpaqueCube(par2 - 1, par3, par4)) {
				d1 = (double) (par2 + 0) - d0;
			}

			if(d1 < (double) par2 || d1 > (double) (par2 + 1) || d2 < 0.0D || d2 > (double) (par3 + 1) || d3 < (double) par4 || d3 > (double) (par4 + 1)) {
				par1World.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}

	}

	@Override
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(AtumBlocks.BLOCK_REDSTONEORE);
	}
}