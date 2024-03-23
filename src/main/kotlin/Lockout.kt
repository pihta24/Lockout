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

package ru.pihta24.lockout

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import ru.pihta24.lockout.goals.Goal
import ru.pihta24.lockout.speedrun_tweaks.PiglinBarter
import ru.pihta24.lockout.utils.Lang
import java.io.File
import java.util.logging.Level

class Lockout : JavaPlugin(), Listener {
    override fun onEnable() {
        saveDefaultConfig()
        this.saveResource("en.yml", false)

        val locale = config.getString("locale", "en")

        var langFile = File(dataFolder, "$locale.yml")

        if (langFile.exists()) {
            Bukkit.getLogger().log(Level.WARNING, "File '$locale.yml' not found")
            langFile = File(dataFolder, "en.yml")
        }

        Lang.LangConfig = YamlConfiguration.loadConfiguration(langFile)
        Lang.DefaultLangConfig = YamlConfiguration.loadConfiguration(getResource("en.yml")!!.reader())


        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(PiglinBarter(), this)
    }


    @EventHandler
    fun handlePlayerEvents(event: PlayerEvent) {
        //TODO: send to board checkEvent
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun setResourcePack(event: PlayerJoinEvent) {
        // event.player.setResourcePack()
        // TODO: set url and hash
    }
}