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
package ru.pihta24.lockout.goals

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerStatisticIncrementEvent

class Goal(
    val name: String,
    private val statistics: ((PlayerStatisticIncrementEvent) -> Boolean)? = null,
    private val movement: ((PlayerMoveEvent) -> Boolean)? = null,
    private val advancement: String? = null,
    private val inventory: ((PlayerInventorySlotChangeEvent) -> Boolean)? = null,
    private val interact: ((PlayerInteractEvent) -> Boolean)? = null
) {
    var complete = false
        private set

    val needsStatistics
        get() = (statistics != null) && !complete
    val needsMovement
        get() = (movement != null) && !complete
    val needsAdvancements
        get() = (advancement != null) && !complete
    val needsInventory
        get() = (inventory != null) && !complete
    val needsInteract
        get() = (interact != null) && !complete

    fun checkEvent(event: PlayerEvent) {
        when (event) {
            is PlayerStatisticIncrementEvent -> {
                checkStatistics(event)
            }

            is PlayerMoveEvent -> {
                checkMovement(event)
            }

            is PlayerAdvancementDoneEvent -> {
                checkAdvancement(event)
            }

            is PlayerInventorySlotChangeEvent -> {
                checkInventory(event)
            }

            is PlayerInteractEvent -> {
                checkInteract(event)
            }
        }
    }

    private fun checkStatistics(event: PlayerStatisticIncrementEvent): Boolean {
        if (statistics == null) return false
        complete = (statistics)(event)
        return complete
    }

    private fun checkMovement(event: PlayerMoveEvent): Boolean {
        if (movement == null) return false
        complete = (movement)(event)
        return complete
    }

    private fun checkAdvancement(event: PlayerAdvancementDoneEvent): Boolean {
        if (advancement == null) return false
        complete = advancement == event.advancement.key.key
        return complete
    }

    private fun checkInventory(event: PlayerInventorySlotChangeEvent): Boolean {
        if (inventory == null) return false
        complete = (inventory)(event)
        return complete
    }

    private fun checkInteract(event: PlayerInteractEvent): Boolean {
        if (interact == null) return false
        complete = (interact)(event)
        return complete
    }
}