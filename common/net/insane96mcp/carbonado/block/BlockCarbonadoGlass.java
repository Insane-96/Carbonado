package net.insane96mcp.carbonado.block;

import java.util.List;

import javax.annotation.Nullable;

import net.insane96mcp.carbonado.Carbonado;
import net.insane96mcp.carbonado.lib.Strings.Names;
import net.insane96mcp.carbonado.lib.Strings.Tooltips;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCarbonadoGlass extends BlockGlass{

	public BlockCarbonadoGlass() {
		super(Material.GLASS, false);
		this.setHardness(0.9f);
		this.setResistance(10f);
		this.setLightOpacity(15);
		this.setSoundType(SoundType.GLASS);
	}

	
	@Override
	public String getTranslationKey() {
		return "tile." + Carbonado.RESOURCE_PREFIX + Names.CARBONADO_GLASS;
	}
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(Tooltips.GlassCreation.base_info));
		tooltip.add("");
		tooltip.add(I18n.format(Tooltips.GlassCreation.base_createGlass));
	}
}
