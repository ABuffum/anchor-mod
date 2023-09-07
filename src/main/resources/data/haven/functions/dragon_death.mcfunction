particle minecraft:dragon_breath ~ ~ ~ 1 1.25 1 0.1 500 normal
summon minecraft:area_effect_cloud ~ ~ ~ {Duration:40, Particle:dragon_breath, Radius:1, RadiusPerTick:0.2, Effects:[{Id:7,Amplifier:0.3,Duration:60}]}
playsound minecraft:entity.generic.explode master @a[distance=..30] ~ ~ ~ 50 0
summon haven:dropped_dragon_breath ~ ~ ~