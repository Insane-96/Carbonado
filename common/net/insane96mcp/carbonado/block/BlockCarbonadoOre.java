package net.insane96mcp.carbonado.block;

import java.util.Random;

import net.insane96mcp.carbonado.Carbonado;
import net.insane96mcp.carbonado.init.ModItems;
import net.insane96mcp.carbonado.lib.Names;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCarbonadoOre extends BlockOre{
	
	public BlockCarbonadoOre() {
		super();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + Carbonado.RESOURCE_PREFIX + Names.CARBONADO_ORE;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.carbonadoItem;
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return Carbonado.random.nextInt(7) + 8;
	}
}
