#Play a spooky sound (wither spawn noise) and display "Something Wicked this way comes" in big purple text on everyone's screens
playsound haven:ambient.miasma_coming ambient @a ~ ~ ~ 1 1
title @a subtitle {"text":"this way comes","color":"light_purple"}
title @a title {"text":"Something wicked","color":"light_purple"}
#putting anathema on the purple team makes his name purple in the tab menu and makes his glowing outline purple
team join Purple Knife_Moth
#anathema is now marked by miasma
effect give Knife_Moth haven:marked 9999 0 false