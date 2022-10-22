particle minecraft:item_slime ~ ~ ~ 1 1.25 1 0.1 500 normal
summon area_effect_cloud ~ ~ ~ {Particle:"item_slime",Radius:1f,RadiusPerTick:.1f,Duration:40,Effects:[{Id:9b,Amplifier:2b,Duration:100},{Id:19b,Amplifier:2b,Duration:100},{Id:20b,Amplifier:2b,Duration:100}]}
summon area_effect_cloud ~ ~ ~ {Particle:"large_smoke",Radius:0.4f,RadiusPerTick:0.1f,Duration:20}
effect give @p[distance=..0] resistance 4 1 false
playsound minecraft:entity.slime.squish master @a[distance=..30] ~ ~ ~ 50 0