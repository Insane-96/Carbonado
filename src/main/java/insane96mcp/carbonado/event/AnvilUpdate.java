package insane96mcp.carbonado.event;

import insane96mcp.carbonado.Carbonado;
import insane96mcp.carbonado.setup.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Carbonado.MOD_ID)
public class AnvilUpdate {
	final static ArrayList<EquipmentUpgrade> EQUIPMENT_UPGRADES = new ArrayList<>();

	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event){
		if (EQUIPMENT_UPGRADES.isEmpty()){
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_PICKAXE, ModItems.carbonadoPickaxe, 2));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_SWORD, ModItems.carbonadoSword, 1));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_AXE, ModItems.carbonadoAxe, 2));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_SHOVEL, ModItems.carbonadoShovel, 1));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_HOE, ModItems.carbonadoHoe, 1));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_HELMET, ModItems.carbonadoHelmet, 3));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_CHESTPLATE, ModItems.carbonadoChestplate, 4));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_LEGGINGS, ModItems.carbonadoLeggings, 4));
			EQUIPMENT_UPGRADES.add(new EquipmentUpgrade(Items.DIAMOND_BOOTS, ModItems.carbonadoBoots, 2));
		}

		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();

		if (!right.getItem().equals(ModItems.carbonado))
			return;

		for (EquipmentUpgrade equipmentUpgrade : EQUIPMENT_UPGRADES) {
			if (!left.getItem().equals(equipmentUpgrade.inputItem))
				continue;

			if (right.getCount() < equipmentUpgrade.materialAmount)
				continue;

			ItemStack output = new ItemStack(equipmentUpgrade.outputItem, 1);
			CompoundNBT tags;
			if (left.getTag() != null)
				tags = left.getTag().copy();
			else
				tags = new CompoundNBT();
			tags.putInt("Damage", 0);
			output.setTag(tags);
			event.setOutput(output);
			event.setMaterialCost(equipmentUpgrade.materialAmount);

			int minCost = ModList.get().isLoaded("charm") ? 0 : 1;

			int cost = minCost;

			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
			for (Enchantment enchantment : enchantments.keySet()) {
				int lvl = enchantments.get(enchantment);
				int baseCost = 0;
				switch (enchantment.getRarity()) {
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
			cost *= 0.667f;

			event.setCost(MathHelper.clamp(cost, minCost, 39));
		}
	}

	private static class EquipmentUpgrade {
		public Item inputItem;
		public Item outputItem;
		public int materialAmount;

		public EquipmentUpgrade(Item inputItem, Item outputItem, int materialAmount) {
			this.inputItem = inputItem;
			this.outputItem = outputItem;
			this.materialAmount = materialAmount;
		}
	}
}