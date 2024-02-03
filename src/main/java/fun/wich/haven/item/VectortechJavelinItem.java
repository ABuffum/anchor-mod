package fun.wich.haven.item;

import fun.wich.haven.entity.VectortechJavelinEntity;
import fun.wich.item.ConditionalLoreItem;
import fun.wich.item.JavelinItem;
import fun.wich.origins.power.PowersUtil;
import io.github.apace100.origins.origin.Origin;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class VectortechJavelinItem extends JavelinItem implements ConditionalLoreItem {
	public VectortechJavelinItem(double attackDamage, float attackSpeed, Identifier texture, Settings settings) {
		super(attackDamage, attackSpeed, texture, settings);
		this.FACTORY = VectortechJavelinEntity::new;
	}

	@Override
	public boolean decrementWhenThrown() { return false; }

	@Override
	public void ApplyConditionalLore(ItemStack stack, PlayerEntity player, TooltipContext context, List<Text> list) {
		if (stack.hasCustomName() && stack.getName().asString().equals("Information Highway")) {
			Origin origin = PowersUtil.GetOrigin(player);
			if (origin != null) {
				String originId = origin.getIdentifier().toString();
				switch (originId) {
					case "haven:soleil" -> {
						list.add(MakeLoreText("This weapon whirs exhuberantly,", 0xaaffff));
						list.add(MakeLoreText("eager to move, fly, and explore", 0xaaffff));
						return;
					}
					case "haven:ferris" -> {
						list.add(MakeLoreText("The shaft of this weapon stings", 0xffaaaa));
						list.add(MakeLoreText("with a cold rejection, as if it", 0xffaaaa));
						list.add(MakeLoreText("knows it is in the wrong hands", 0xffaaaa));
						return;
					}
					case "haven:sleep" -> {
						list.add(MakeLoreText("This weapon hums softly,", 0x9966aa));
						list.add(MakeLoreText("trusting that you will return", 0x9966aa));
						list.add(MakeLoreText("it to its true owner in time", 0x9966aa));
						return;
					}
				}
			}
			list.add(MakeLoreText("This weapon feels heavy and", Formatting.GRAY));
			list.add(MakeLoreText("unbalanced in your hands. It", Formatting.GRAY));
			list.add(MakeLoreText("is not meant for you to wield", Formatting.GRAY));
		}
	}
}
