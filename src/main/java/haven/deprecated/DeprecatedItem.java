package haven.deprecated;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.List;
public class DeprecatedItem extends Item {
	public Item replacement;
	public DeprecatedItem(Item replacement) {
		super(Deprecated.ItemSettings());
		this.replacement = replacement;
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setStackInHand(hand, new ItemStack(replacement, user.getStackInHand(hand).getCount()));
		return TypedActionResult.success(user.getStackInHand(hand));
	}
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.of("Use to replace with minecraft:" + replacement));
	}
}
