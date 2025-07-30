package net.sinny.journeyreforged.villager;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.util.List;
import java.util.function.Consumer;

public class ModVillagerTrades {
    public static void removeVillagerTrades() {
        Consumer<List<TradeOffers.Factory>> removeMendingFactory = factories -> {
            factories.removeIf(factory -> {
                TradeOffer offer = factory.create(null, Random.create());
                if (offer == null) {
                    return false;
                }

                ItemStack sellStack = offer.getSellItem();
                if (sellStack.isOf(Items.ENCHANTED_BOOK)) {
                    ItemEnchantmentsComponent enchantments = sellStack.get(DataComponentTypes.STORED_ENCHANTMENTS);
                    if (enchantments != null) {
                        return enchantments.getEnchantments().stream()
                                .anyMatch(entry -> entry.matchesKey(Enchantments.MENDING));
                    }
                }
                return false;
            });
        };

        for (int level = 1; level <= 5; level++) {
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, level, removeMendingFactory);
        }
    }
}