package net.insane96mcp.carbonado.events;

import java.util.Map;

import net.insane96mcp.carbonado.Carbonado;
import net.insane96mcp.carbonado.init.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Carbonado.MOD_ID)
public class AnvilUpdate {
	
	@SubscribeEvent
	public static void EventAnvilOuput(AnvilUpdateEvent event) {
		
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		ItemStack output = null;
		
		if (!right.getItem().equals(ModItems.carbonado))
			return;

		ItemStack[] validInputs = new ItemStack[] {
			new ItemStack(Items.DIAMOND_AXE),
			new ItemStack(Items.DIAMOND_SHOVEL),
			new ItemStack(Items.DIAMOND_PICKAXE),
			new ItemStack(Items.DIAMOND_SWORD),
			new ItemStack(Items.DIAMOND_HOE),
			new ItemStack(Items.DIAMOND_HELMET),
			new ItemStack(Items.DIAMOND_CHESTPLATE),
			new ItemStack(Items.DIAMOND_LEGGINGS),
			new ItemStack(Items.DIAMOND_BOOTS),
		};
		
		for (ItemStack itemStack : validInputs) {
			if (!ItemStack.areItemsEqualIgnoreDurability(left, itemStack))
				continue;
			
			IRecipe recipe = ServerLifecycleHooks.getCurrentServer().getRecipeManager().getRecipe(left.getItem().getRegistryName());

			int carbonadoAmount = 0;
			for (Ingredient ingredient : recipe.getIngredients()) {
				if (ingredient.getMatchingStacks().length > 0 && ingredient.getMatchingStacks()[0].getItem().equals(Items.DIAMOND))
					carbonadoAmount++;
			}

			if (right.getCount() < carbonadoAmount)
				return;
			
			String itemName = left.getItem().getRegistryName().getPath();
			ResourceLocation carbonadoName = new ResourceLocation("carbonado", itemName.replace("diamond", "carbonado"));
			output = new ItemStack(ForgeRegistries.ITEMS.getValue(carbonadoName));
			//output = new ItemStack(Item.getByNameOrId(carbonadoName.toString()));
			NBTTagCompound tags = left.getTag();
			output.setTag(tags);
			event.setOutput(output);
			event.setMaterialCost(carbonadoAmount);
			
			int cost = 0;
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
			for (Enchantment enchantment : enchantments.keySet()) {
				int lvl = enchantments.get(enchantment);
				int baseCost = 0;
				switch (enchantment.getRarity())
                {
                    case COMMON:
                    	baseCost = 1;
                        break;
                    case UNCOMMON:
                    	baseCost = 2;
                        break;
                    case RARE:
                    	baseCost = 4;
                        break;
                    case VERY_RARE:
                    	baseCost = 8;
                }
				cost += baseCost * lvl;
			}
			cost *= 0.6f;
			event.setCost(MathHelper.clamp(cost, 1, 39));
		}
	}
}
