/*
 *  This file is part of EnchantmentsPlus, a bukkit plugin.
 *  Copyright (c) 2015 - 2020 Zedly and Zenchantments contributors.
 *  Copyright (c) 2020 - 2021 Geolykt and EnchantmentsPlus contributors
 *   
 *  This program is free software: you can redistribute it and/or modify  
 *  it under the terms of the GNU General Public License as published by  
 *  the Free Software Foundation, version 3.
 *  
 *  This program is distributed in the hope that it will be useful, but 
 *  WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 *  General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License 
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.util.Tool;

public class Speed extends CustomEnchantment {

    public static final int ID = 55;

    @Override
    public Builder<Speed> defaults() {
        return new Builder<>(Speed::new, ID)
            .all("Gives the player a speed boost",
                    new Tool[]{Tool.BOOTS},
                    "Speed",
                    4,
                    Hand.NONE,
                    BaseEnchantments.MEADOR, BaseEnchantments.WEIGHT);
    }

    public Speed() {
        super(BaseEnchantments.SPEED);
    }

    @Override
    public boolean onScan(Player player, int level, boolean usedHand) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, (int) (level * power)));
        return true;
    }
}
