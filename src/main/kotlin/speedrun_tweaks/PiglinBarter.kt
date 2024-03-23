/*
 * Copyright (c) 2024 pihta24 <admin@pihta24.ru>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ru.pihta24.lockout.speedrun_tweaks

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PiglinBarterEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

class PiglinBarter : Listener {
    private enum class Loots {
        Book, Boots, Nugget, SplashPotion, Potion, Quartz, GlowStone, Cream, Pearl,
        String, Fireball, Gravel, Leather, Brick, Obsidian, CryingObsidian, Sand
    }

    private val loot = mutableListOf<Loots>()

    init {
        for (i in 1..5) loot.add(Loots.Book)
        for (i in 1..8) loot.add(Loots.Boots)
        for (i in 1..10) {
            loot.add(Loots.Nugget)
            loot.add(Loots.SplashPotion)
            loot.add(Loots.Potion)
        }
        for (i in 1..20) {
            loot.add(Loots.Quartz)
            loot.add(Loots.GlowStone)
            loot.add(Loots.Cream)
            loot.add(Loots.Pearl)
            loot.add(Loots.String)
        }
        for (i in 1..40) {
            loot.add(Loots.Fireball)
            loot.add(Loots.Gravel)
            loot.add(Loots.Leather)
            loot.add(Loots.Brick)
            loot.add(Loots.Obsidian)
            loot.add(Loots.CryingObsidian)
            loot.add(Loots.Sand)
        }

        loot.shuffle()
        loot.shuffle()
        loot.shuffle()
    }

    @EventHandler
    fun onBarter(event: PiglinBarterEvent) {
        loot.shuffle()
        lateinit var item: ItemStack
        when (loot[0]) {
            Loots.Book -> {
                item = ItemStack(Material.ENCHANTED_BOOK, 1)
                val meta = item.itemMeta as EnchantmentStorageMeta
                meta.addStoredEnchant(Enchantment.SOUL_SPEED, (1..3).random(), false)
                item.itemMeta = meta
            }

            Loots.Boots -> {
                item = ItemStack(Material.IRON_BOOTS, 1)
                val meta = item.itemMeta
                meta.addEnchant(Enchantment.SOUL_SPEED, (1..3).random(), false)
                item.itemMeta = meta
            }

            Loots.Nugget -> item = ItemStack(Material.IRON_NUGGET, (9..39).random())
            Loots.SplashPotion -> {
                item = ItemStack(Material.SPLASH_POTION, 1)
                val meta = item.itemMeta as PotionMeta
                meta.basePotionData = PotionData(PotionType.FIRE_RESISTANCE)
                item.itemMeta = meta
            }

            Loots.Potion -> {
                item = ItemStack(Material.POTION, 1)
                val meta = item.itemMeta as PotionMeta
                meta.basePotionData = PotionData(PotionType.FIRE_RESISTANCE)
                item.itemMeta = meta
            }

            Loots.Quartz -> item = ItemStack(Material.QUARTZ, (8..16).random())
            Loots.GlowStone -> item = ItemStack(Material.GLOWSTONE_DUST, (5..12).random())
            Loots.Cream -> item = ItemStack(Material.MAGMA_CREAM, (2..6).random())
            Loots.Pearl -> item = ItemStack(Material.ENDER_PEARL, (4..8).random())
            Loots.String -> item = ItemStack(Material.STRING, (8..24).random())
            Loots.Fireball -> item = ItemStack(Material.FIRE_CHARGE, (1..5).random())
            Loots.Gravel -> item = ItemStack(Material.GRAVEL, (8..16).random())
            Loots.Leather -> item = ItemStack(Material.LEATHER, (4..10).random())
            Loots.Brick -> item = ItemStack(Material.NETHER_BRICK, (4..16).random())
            Loots.Obsidian -> item = ItemStack(Material.OBSIDIAN, 1)
            Loots.CryingObsidian -> item = ItemStack(Material.CRYING_OBSIDIAN, (1..3).random())
            Loots.Sand -> item = ItemStack(Material.SOUL_SAND, (4..16).random())
        }

        event.outcome.clear()
        event.outcome.add(item)
    }
}