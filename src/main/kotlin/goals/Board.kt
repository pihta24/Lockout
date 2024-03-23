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
import kotlin.reflect.typeOf

class Board {
    private val _goals = mutableListOf<Goal>()
    val goals
        get() = listOf(_goals)

    private var needsStatistics = false
    private var needsMovement = false
    private var needsAdvancements = false
    private var needsInventory = false
    private var needsInteract = false

    fun generate() {
        checkNeeds()
    }

    private fun checkNeeds(){
        needsStatistics = false
        needsMovement = false
        needsAdvancements = false
        needsInventory = false
        needsInteract = false

        _goals.forEach {
            needsStatistics = needsStatistics || it.needsStatistics
            needsMovement = needsMovement || it.needsMovement
            needsAdvancements = needsAdvancements || it.needsAdvancements
            needsInventory = needsInventory || it.needsInventory
            needsInteract = needsInteract || it.needsInteract
        }
    }

    fun checkEvent(event: PlayerEvent) {
        if (
            (event is PlayerStatisticIncrementEvent && needsStatistics) ||
            (event is PlayerMoveEvent && needsMovement) ||
            (event is PlayerAdvancementDoneEvent && needsAdvancements) ||
            (event is PlayerInventorySlotChangeEvent && needsInventory) ||
            (event is PlayerInteractEvent && needsInteract)
            ) _goals.forEach { it.checkEvent(event) }
    }
}