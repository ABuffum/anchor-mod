package haven.rendering.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value= EnvType.CLIENT)
public class WardenAnimations {
	public static final Animation EMERGING = Animation.Builder.create(6.68f)
	.addBoneAnimation("body", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, -22.5f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41829(0.0f, 0.0f, -7.5f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41829(0.0f, 0.0f, 10.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41829(0.0f, 0.0f, 10.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(0.0f, 0.0f, 10.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(0.0f, 0.0f, 10.0f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41829(25.0f, 0.0f, -7.5f), Interpolations.field_37885),
			new Keyframe(3.92f, AnimationHelper.method_41829(35.0f, 0.0f, -7.5f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41829(25.0f, 0.0f, -7.5f), Interpolations.field_37885),
			new Keyframe(4.44f, AnimationHelper.method_41829(47.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41829(47.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41829(47.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(70.0f, 0.0f, 2.5f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41829(70.0f, 0.0f, 2.5f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("body", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, -63.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(0.0f, -56.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.16f, AnimationHelper.method_41823(0.0f, -27.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41823(0.0f, -14.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.92f, AnimationHelper.method_41823(0.0f, -11.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41823(0.0f, -14.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.44f, AnimationHelper.method_41823(0.0f, -6.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41823(0.0f, -4.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41823(0.0f, -6.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.0f, -3.0f, -4.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41823(0.0f, -3.0f, -4.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.92f, AnimationHelper.method_41829(0.74f, 0.0f, -40.38f), Interpolations.field_37885),
			new Keyframe(1.16f, AnimationHelper.method_41829(-67.5f, 0.0f, -2.5f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(-67.5f, 0.0f, -2.5f), Interpolations.field_37885),
			new Keyframe(1.32f, AnimationHelper.method_41829(-47.5f, 0.0f, -2.5f), Interpolations.field_37885),
			new Keyframe(1.4f, AnimationHelper.method_41829(-67.5f, 0.0f, -2.5f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41829(-67.5f, 0.0f, 15.0f), Interpolations.field_37885),
			new Keyframe(1.76f, AnimationHelper.method_41829(-67.5f, 0.0f, -5.0f), Interpolations.field_37885),
			new Keyframe(1.84f, AnimationHelper.method_41829(-52.5f, 0.0f, -5.0f), Interpolations.field_37885),
			new Keyframe(1.92f, AnimationHelper.method_41829(-67.5f, 0.0f, -5.0f), Interpolations.field_37885),
			new Keyframe(2.64f, AnimationHelper.method_41829(-17.5f, 0.0f, -10.0f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41829(70.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(4.04f, AnimationHelper.method_41829(70.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(4.12f, AnimationHelper.method_41829(80.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(4.24f, AnimationHelper.method_41829(70.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(77.5f, 0.0f, -2.5f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(-8.0f, -11.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.92f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41823(0.0f, 0.47f, -0.95f), Interpolations.field_37885),
			new Keyframe(1.32f, AnimationHelper.method_41823(0.0f, 0.47f, -0.95f), Interpolations.field_37885),
			new Keyframe(1.4f, AnimationHelper.method_41823(0.0f, 0.47f, -0.95f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41823(0.0f, 1.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(1.76f, AnimationHelper.method_41823(0.0f, 1.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(1.84f, AnimationHelper.method_41823(0.0f, 1.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(1.92f, AnimationHelper.method_41823(0.0f, 1.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(2.64f, AnimationHelper.method_41823(0.0f, -2.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41823(0.0f, -4.0f, 1.0f), Interpolations.field_37885),
			new Keyframe(4.04f, AnimationHelper.method_41823(0.0f, -1.0f, 1.0f), Interpolations.field_37885),
			new Keyframe(4.12f, AnimationHelper.method_41823(0.0f, -1.0f, 1.0f), Interpolations.field_37885),
			new Keyframe(4.24f, AnimationHelper.method_41823(0.0f, -1.0f, 1.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.0f, -1.0f, 1.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_ear", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_ear", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_ear", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_ear", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41829(-152.5f, 2.5f, 7.5f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41829(-180.0f, 12.5f, -10.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41829(-90.0f, 12.5f, -10.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(-90.0f, 12.5f, -10.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(-90.0f, 12.5f, -10.0f), Interpolations.field_37885),
			new Keyframe(3.08f, AnimationHelper.method_41829(-95.0f, 12.5f, -10.0f), Interpolations.field_37885),
			new Keyframe(3.24f, AnimationHelper.method_41829(-83.93f, 3.93f, 5.71f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41829(-80.0f, 7.5f, 17.5f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41829(-67.5f, 2.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41829(-67.5f, 2.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.44f, AnimationHelper.method_41829(-55.0f, 2.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41829(-60.0f, 2.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41829(-55.0f, 2.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(-67.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.56f, AnimationHelper.method_41829(-50.45f, 0.0f, 2.69f), Interpolations.field_37885),
			new Keyframe(6.08f, AnimationHelper.method_41829(-62.72f, 0.0f, 4.3f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41823(0.0f, -21.0f, 9.0f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41823(2.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41823(2.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(2.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(2.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.08f, AnimationHelper.method_41823(2.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.24f, AnimationHelper.method_41823(2.0f, 2.71f, 3.86f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41823(2.0f, 1.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41823(2.0f, 3.0f, 3.0f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41823(2.0f, 3.0f, 3.0f), Interpolations.field_37885),
			new Keyframe(4.44f, AnimationHelper.method_41823(2.67f, 4.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41823(2.67f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41823(2.67f, 4.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.67f, 3.0f, 4.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.12f, AnimationHelper.method_41829(-167.5f, -17.5f, -7.5f), Interpolations.field_37885),
			new Keyframe(0.6f, AnimationHelper.method_41829(-167.5f, -17.5f, -7.5f), Interpolations.field_37885),
			new Keyframe(0.88f, AnimationHelper.method_41829(-175.0f, -17.5f, 15.0f), Interpolations.field_37885),
			new Keyframe(1.16f, AnimationHelper.method_41829(-190.0f, -17.5f, 5.0f), Interpolations.field_37885),
			new Keyframe(1.28f, AnimationHelper.method_41829(-90.0f, -5.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41829(-90.0f, -17.5f, -12.5f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41829(-90.0f, -17.5f, -12.5f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(-90.0f, -17.5f, -12.5f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(-90.0f, -17.5f, -12.5f), Interpolations.field_37885),
			new Keyframe(3.04f, AnimationHelper.method_41829(-81.29f, -10.64f, -14.21f), Interpolations.field_37885),
			new Keyframe(3.16f, AnimationHelper.method_41829(-83.5f, -5.5f, -15.5f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41829(-62.5f, -7.5f, 5.0f), Interpolations.field_37885),
			new Keyframe(3.92f, AnimationHelper.method_41829(-58.75f, -3.75f, 5.0f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41829(-55.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.44f, AnimationHelper.method_41829(-52.5f, 0.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41829(-50.0f, 0.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41829(-52.5f, 0.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(-72.5f, -2.5f, 5.0f), Interpolations.field_37885),
			new Keyframe(5.56f, AnimationHelper.method_41829(-57.5f, -4.54f, 2.99f), Interpolations.field_37885),
			new Keyframe(6.08f, AnimationHelper.method_41829(-70.99f, -5.77f, 1.78f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.12f, AnimationHelper.method_41823(0.0f, -8.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.6f, AnimationHelper.method_41823(0.0f, -8.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.88f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41823(-2.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41823(-4.0f, 3.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41823(-4.0f, 3.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(-4.0f, 3.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(-4.0f, 3.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.04f, AnimationHelper.method_41823(-3.23f, 5.7f, 4.97f), Interpolations.field_37885),
			new Keyframe(3.16f, AnimationHelper.method_41823(-1.49f, 2.22f, 5.25f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41823(-1.14f, 1.71f, 1.86f), Interpolations.field_37885),
			new Keyframe(3.92f, AnimationHelper.method_41823(-1.14f, 1.21f, 3.86f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41823(-1.14f, 2.71f, 4.86f), Interpolations.field_37885),
			new Keyframe(4.44f, AnimationHelper.method_41823(-1.0f, 1.0f, 3.0f), Interpolations.field_37885),
			new Keyframe(4.56f, AnimationHelper.method_41823(0.0f, 1.0f, 1.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41823(0.0f, 1.0f, 3.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(-2.0f, 0.0f, 4.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_leg", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.32f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.48f, AnimationHelper.method_41829(55.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.6f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_leg", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, -63.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(0.0f, -56.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41823(0.0f, -22.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.76f, AnimationHelper.method_41823(0.0f, -12.28f, 2.48f), Interpolations.field_37885),
			new Keyframe(3.92f, AnimationHelper.method_41823(0.0f, -9.28f, 2.48f), Interpolations.field_37885),
			new Keyframe(4.08f, AnimationHelper.method_41823(0.0f, -12.28f, 2.48f), Interpolations.field_37885),
			new Keyframe(4.32f, AnimationHelper.method_41823(0.0f, -4.14f, 4.14f), Interpolations.field_37885),
			new Keyframe(4.48f, AnimationHelper.method_41823(0.0f, -0.57f, -8.43f), Interpolations.field_37885),
			new Keyframe(4.6f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_leg", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.84f, AnimationHelper.method_41829(20.0f, 0.0f, -17.5f), Interpolations.field_37885),
			new Keyframe(4.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41829(20.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.84f, AnimationHelper.method_41829(10.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_leg", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, -63.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.52f, AnimationHelper.method_41823(0.0f, -56.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.68f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41823(0.0f, -32.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.36f, AnimationHelper.method_41823(0.0f, -22.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.84f, AnimationHelper.method_41823(-4.0f, 2.0f, -7.0f), Interpolations.field_37885),
			new Keyframe(4.0f, AnimationHelper.method_41823(-4.0f, 0.0f, -5.0f), Interpolations.field_37885),
			new Keyframe(4.68f, AnimationHelper.method_41823(-4.0f, 0.0f, -9.0f), Interpolations.field_37885),
			new Keyframe(4.84f, AnimationHelper.method_41823(-2.0f, 2.0f, -3.5f), Interpolations.field_37885),
			new Keyframe(5.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(5.8f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(6.64f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).build();
	public static final Animation DIGGING = Animation.Builder.create(5.0f)
	.addBoneAnimation("body", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.25f, AnimationHelper.method_41829(4.13441f, 0.94736f, 1.2694f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41829(50.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41829(54.45407f, -13.53935f, -18.14183f), Interpolations.field_37885),
			new Keyframe(1.0417f, AnimationHelper.method_41829(59.46442f, -10.8885f, 35.7954f), Interpolations.field_37885),
			new Keyframe(1.3333f, AnimationHelper.method_41829(82.28261f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.625f, AnimationHelper.method_41829(53.23606f, 10.04715f, -29.72932f), Interpolations.field_37885),
			new Keyframe(2.2083f, AnimationHelper.method_41829(-17.71739f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.5417f, AnimationHelper.method_41829(112.28261f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.6667f, AnimationHelper.method_41829(116.06889f, 5.11581f, -24.50117f), Interpolations.field_37885),
			new Keyframe(2.8333f, AnimationHelper.method_41829(121.56244f, -4.17248f, 19.57737f), Interpolations.field_37885),
			new Keyframe(3.0417f, AnimationHelper.method_41829(138.5689f, 5.11581f, -24.50117f), Interpolations.field_37885),
			new Keyframe(3.25f, AnimationHelper.method_41829(144.06244f, -4.17248f, 19.57737f), Interpolations.field_37885),
			new Keyframe(3.375f, AnimationHelper.method_41829(147.28261f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.625f, AnimationHelper.method_41829(147.28261f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.875f, AnimationHelper.method_41829(134.36221f, 8.81113f, -8.90172f), Interpolations.field_37885),
			new Keyframe(4.0417f, AnimationHelper.method_41829(132.05966f, -8.35927f, 9.70506f), Interpolations.field_37885),
			new Keyframe(4.25f, AnimationHelper.method_41829(134.36221f, 8.81113f, -8.90172f), Interpolations.field_37885),
			new Keyframe(4.5f, AnimationHelper.method_41829(147.5f, 0.0f, 0.0f), Interpolations.field_37884) }
	)).addBoneAnimation("body", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41823(0.0f, -16.48454f, -6.5784f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41823(0.0f, -16.48454f, -6.5784f), Interpolations.field_37885),
			new Keyframe(1.0417f, AnimationHelper.method_41823(0.0f, -16.97f, -7.11f), Interpolations.field_37885),
			new Keyframe(1.625f, AnimationHelper.method_41823(0.0f, -13.97f, -7.11f), Interpolations.field_37885),
			new Keyframe(2.2083f, AnimationHelper.method_41823(0.0f, -11.48454f, -0.5784f), Interpolations.field_37885),
			new Keyframe(2.5417f, AnimationHelper.method_41823(0.0f, -16.48454f, -6.5784f), Interpolations.field_37885),
			new Keyframe(2.6667f, AnimationHelper.method_41823(0.0f, -20.27f, -5.42f), Interpolations.field_37885),
			new Keyframe(3.375f, AnimationHelper.method_41823(0.0f, -21.48454f, -5.5784f), Interpolations.field_37885),
			new Keyframe(4.0417f, AnimationHelper.method_41823(0.0f, -22.48454f, -5.5784f), Interpolations.field_37885),
			new Keyframe(4.5f, AnimationHelper.method_41823(0.0f, -40.0f, -8.0f), Interpolations.field_37884) }
	)).addBoneAnimation("head", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.6667f, AnimationHelper.method_41829(12.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.2083f, AnimationHelper.method_41829(12.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.75f, AnimationHelper.method_41829(45.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.375f, AnimationHelper.method_41829(-22.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.5417f, AnimationHelper.method_41829(67.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.375f, AnimationHelper.method_41829(67.5f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.375f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37884) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41829(-101.8036f, -21.29587f, 30.61478f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41829(-101.8036f, -21.29587f, 30.61478f), Interpolations.field_37885),
			new Keyframe(1.0f, AnimationHelper.method_41829(48.7585f, -17.61941f, 9.9865f), Interpolations.field_37885),
			new Keyframe(1.1667f, AnimationHelper.method_41829(48.7585f, -17.61941f, 9.9865f), Interpolations.field_37885),
			new Keyframe(1.4583f, AnimationHelper.method_41829(-101.8036f, -21.29587f, 30.61478f), Interpolations.field_37885),
			new Keyframe(1.75f, AnimationHelper.method_41829(-89.04994f, -4.19657f, -1.47845f), Interpolations.field_37885),
			new Keyframe(2.2083f, AnimationHelper.method_41829(-158.30728f, 3.7152f, -1.52352f), Interpolations.field_37885),
			new Keyframe(2.5417f, AnimationHelper.method_41829(-89.04994f, -4.19657f, -1.47845f), Interpolations.field_37885),
			new Keyframe(4.375f, AnimationHelper.method_41829(-120.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41823(2.22f, 0.0f, 0.86f), Interpolations.field_37885),
			new Keyframe(1.0f, AnimationHelper.method_41823(3.12f, 0.0f, 4.29f), Interpolations.field_37885),
			new Keyframe(2.2083f, AnimationHelper.method_41823(1.0f, 0.0f, 4.0f), Interpolations.field_37885),
			new Keyframe(4.375f, AnimationHelper.method_41823(0.0f, 0.0f, 4.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.2917f, AnimationHelper.method_41829(-63.89288f, -0.52011f, 2.09491f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41829(-63.89288f, -0.52011f, 2.09491f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41829(-62.87857f, 15.15061f, 9.97445f), Interpolations.field_37885),
			new Keyframe(0.9167f, AnimationHelper.method_41829(-86.93642f, 17.45026f, 4.05284f), Interpolations.field_37885),
			new Keyframe(1.1667f, AnimationHelper.method_41829(-86.93642f, 17.45026f, 4.05284f), Interpolations.field_37885),
			new Keyframe(1.4583f, AnimationHelper.method_41829(-86.93642f, 17.45026f, 4.05284f), Interpolations.field_37885),
			new Keyframe(1.6667f, AnimationHelper.method_41829(63.0984f, 8.83573f, -8.71284f), Interpolations.field_37885),
			new Keyframe(1.8333f, AnimationHelper.method_41829(35.5984f, 8.83573f, -8.71284f), Interpolations.field_37885),
			new Keyframe(2.2083f, AnimationHelper.method_41829(-153.27473f, -0.02953f, 3.5235f), Interpolations.field_37885),
			new Keyframe(2.5417f, AnimationHelper.method_41829(-87.07754f, -0.02625f, 3.132f), Interpolations.field_37885),
			new Keyframe(4.375f, AnimationHelper.method_41829(-120.0f, 0.0f, 0.0f), Interpolations.field_37884) }
			)).addBoneAnimation("left_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41823(-0.28f, 5.0f, 10.0f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41823(-1.51f, 4.35f, 4.33f), Interpolations.field_37885),
			new Keyframe(0.9167f, AnimationHelper.method_41823(-0.6f, 3.61f, 4.63f), Interpolations.field_37885),
			new Keyframe(1.1667f, AnimationHelper.method_41823(-0.6f, 3.61f, 0.63f), Interpolations.field_37885),
			new Keyframe(1.6667f, AnimationHelper.method_41823(-2.85f, -0.1f, 3.33f), Interpolations.field_37885),
			new Keyframe(2.2083f, AnimationHelper.method_41823(-1.0f, 0.0f, 4.0f), Interpolations.field_37885),
			new Keyframe(4.375f, AnimationHelper.method_41823(0.0f, 0.0f, 4.0f), Interpolations.field_37884) }
			)).addBoneAnimation("right_leg", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41829(113.27f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41829(113.27f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.3333f, AnimationHelper.method_41829(113.27f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.5833f, AnimationHelper.method_41829(182.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.8333f, AnimationHelper.method_41829(120.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.0833f, AnimationHelper.method_41829(182.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.2917f, AnimationHelper.method_41829(120.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.5f, AnimationHelper.method_41829(90.0f, 0.0f, 0.0f), Interpolations.field_37884) }
			)).addBoneAnimation("right_leg", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41823(0.0f, -13.98f, -2.37f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41823(0.0f, -13.98f, -2.37f), Interpolations.field_37885),
			new Keyframe(3.3333f, AnimationHelper.method_41823(0.0f, -13.98f, -2.37f), Interpolations.field_37885),
			new Keyframe(3.5833f, AnimationHelper.method_41823(0.0f, -7.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(3.8333f, AnimationHelper.method_41823(0.0f, -9.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(4.0833f, AnimationHelper.method_41823(0.0f, -16.71f, -3.69f), Interpolations.field_37885),
			new Keyframe(4.2917f, AnimationHelper.method_41823(0.0f, -28.0f, -5.0f), Interpolations.field_37884) }
			)).addBoneAnimation("left_leg", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41829(114.98f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41829(114.98f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.3333f, AnimationHelper.method_41829(114.98f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.5833f, AnimationHelper.method_41829(90.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.8333f, AnimationHelper.method_41829(172.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.0833f, AnimationHelper.method_41829(90.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.2917f, AnimationHelper.method_41829(197.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.5f, AnimationHelper.method_41829(90.0f, 0.0f, 0.0f), Interpolations.field_37884) }
			)).addBoneAnimation("left_leg", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.5f, AnimationHelper.method_41823(0.0f, -14.01f, -2.35f), Interpolations.field_37885),
			new Keyframe(0.7083f, AnimationHelper.method_41823(0.0f, -14.01f, -2.35f), Interpolations.field_37885),
			new Keyframe(3.3333f, AnimationHelper.method_41823(0.0f, -14.01f, -2.35f), Interpolations.field_37885),
			new Keyframe(3.5833f, AnimationHelper.method_41823(0.0f, -5.0f, -4.0f), Interpolations.field_37885),
			new Keyframe(3.8333f, AnimationHelper.method_41823(0.0f, -7.0f, -4.0f), Interpolations.field_37885),
			new Keyframe(4.0833f, AnimationHelper.method_41823(0.0f, -15.5f, -3.76f), Interpolations.field_37885),
			new Keyframe(4.2917f, AnimationHelper.method_41823(0.0f, -28.0f, -5.0f), Interpolations.field_37884) }
			)).build();
	public static final Animation ROARING = Animation.Builder.create(4.2f)
	.addBoneAnimation("body", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(-25.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.6f, AnimationHelper.method_41829(32.5f, 0.0f, -7.5f), Interpolations.field_37885),
			new Keyframe(1.84f, AnimationHelper.method_41829(38.33f, 0.0f, 2.99f), Interpolations.field_37885),
			new Keyframe(2.08f, AnimationHelper.method_41829(40.97f, 0.0f, -4.3f), Interpolations.field_37885),
			new Keyframe(2.36f, AnimationHelper.method_41829(44.41f, 0.0f, 6.29f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41829(47.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("body", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41823(0.0f, -1.0f, 3.0f), Interpolations.field_37885),
			new Keyframe(1.6f, AnimationHelper.method_41823(0.0f, -3.0f, -6.0f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41823(0.0f, -3.0f, -6.0f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(-32.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.6f, AnimationHelper.method_41829(-32.5f, 0.0f, -27.5f), Interpolations.field_37885),
			new Keyframe(1.8f, AnimationHelper.method_41829(-32.5f, 0.0f, 26.0f), Interpolations.field_37885),
			new Keyframe(2.04f, AnimationHelper.method_41829(-32.5f, 0.0f, -27.5f), Interpolations.field_37885),
			new Keyframe(2.44f, AnimationHelper.method_41829(-32.5f, 0.0f, 26.0f), Interpolations.field_37885),
			new Keyframe(2.84f, AnimationHelper.method_41829(-5.0f, 0.0f, -12.5f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.6f, AnimationHelper.method_41823(0.0f, -2.0f, -6.0f), Interpolations.field_37885),
			new Keyframe(2.2f, AnimationHelper.method_41823(0.0f, -2.0f, -6.0f), Interpolations.field_37885),
			new Keyframe(2.48f, AnimationHelper.method_41823(0.0f, -2.0f, -6.0f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_ear", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.76f, AnimationHelper.method_41829(0.0f, 0.0f, -10.85f), Interpolations.field_37885),
			new Keyframe(2.08f, AnimationHelper.method_41829(0.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(2.4f, AnimationHelper.method_41829(0.0f, 0.0f, -10.85f), Interpolations.field_37885),
			new Keyframe(2.72f, AnimationHelper.method_41829(0.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41829(0.0f, 0.0f, -10.85f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_ear", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.76f, AnimationHelper.method_41829(0.0f, 0.0f, -15.85f), Interpolations.field_37885),
			new Keyframe(2.08f, AnimationHelper.method_41829(0.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(2.4f, AnimationHelper.method_41829(0.0f, 0.0f, -15.85f), Interpolations.field_37885),
			new Keyframe(2.72f, AnimationHelper.method_41829(0.0f, 0.0f, 12.5f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41829(0.0f, 0.0f, -15.85f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.72f, AnimationHelper.method_41829(-120.0f, 0.0f, -20.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(-77.5f, 3.75f, 15.0f), Interpolations.field_37885),
			new Keyframe(1.48f, AnimationHelper.method_41829(67.5f, -32.5f, 20.0f), Interpolations.field_37885),
			new Keyframe(2.48f, AnimationHelper.method_41829(37.5f, -32.5f, 25.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(27.6f, -17.1f, 32.5f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.72f, AnimationHelper.method_41823(3.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.48f, AnimationHelper.method_41823(4.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.48f, AnimationHelper.method_41823(4.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.72f, AnimationHelper.method_41829(-125.0f, 0.0f, 20.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(-76.25f, -17.5f, -7.5f), Interpolations.field_37885),
			new Keyframe(1.48f, AnimationHelper.method_41829(62.5f, 42.5f, -12.5f), Interpolations.field_37885),
			new Keyframe(2.48f, AnimationHelper.method_41829(37.5f, 27.5f, -27.5f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(25.0f, 18.4f, -30.0f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.72f, AnimationHelper.method_41823(-3.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.48f, AnimationHelper.method_41823(-4.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.48f, AnimationHelper.method_41823(-4.0f, -2.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(4.2f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).build();
	public static final Animation SNIFFING = Animation.Builder.create(4.16f)
	.addBoneAnimation("body", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.56f, AnimationHelper.method_41829(17.5f, 32.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.96f, AnimationHelper.method_41829(0.0f, 32.5f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.2f, AnimationHelper.method_41829(10.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.8f, AnimationHelper.method_41829(10.0f, -30.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.32f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.68f, AnimationHelper.method_41829(0.0f, 40.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.96f, AnimationHelper.method_41829(-22.5f, 40.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.24f, AnimationHelper.method_41829(0.0f, 20.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.52f, AnimationHelper.method_41829(-35.0f, 20.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.76f, AnimationHelper.method_41829(0.0f, 20.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.28f, AnimationHelper.method_41829(0.0f, -20.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.88f, AnimationHelper.method_41829(0.0f, -20.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.32f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.96f, AnimationHelper.method_41829(17.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.76f, AnimationHelper.method_41829(-15.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.32f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.96f, AnimationHelper.method_41829(-15.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.2f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.76f, AnimationHelper.method_41829(17.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.32f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).build();
	public static final Animation ATTACKING = Animation.Builder.create(0.33333f)
	.addBoneAnimation("body", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41829(-22.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.2083f, AnimationHelper.method_41829(22.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("body", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.2083f, AnimationHelper.method_41823(0.0f, -1.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41829(22.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.25f, AnimationHelper.method_41829(-30.17493f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.25f, AnimationHelper.method_41823(0.0f, -2.0f, -2.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41829(-120.36119f, 40.78947f, -20.94102f), Interpolations.field_37885),
			new Keyframe(0.1667f, AnimationHelper.method_41829(-90.0f, -45.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41823(4.0f, 0.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(0.1667f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41829(-120.36119f, -40.78947f, 20.94102f), Interpolations.field_37885),
			new Keyframe(0.1667f, AnimationHelper.method_41829(-61.1632f, 42.85882f, 11.52421f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.0417f, AnimationHelper.method_41823(-4.0f, 0.0f, 5.0f), Interpolations.field_37885),
			new Keyframe(0.1667f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.3333f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).build();
	public static final Animation CHARGING_SONIC_BOOM = Animation.Builder.create(3.0f)
	.addBoneAnimation("body", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.0833f, AnimationHelper.method_41829(47.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.625f, AnimationHelper.method_41829(55.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.9167f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.0f, AnimationHelper.method_41829(-32.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.4583f, AnimationHelper.method_41829(-32.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.7083f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.875f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("body", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.0833f, AnimationHelper.method_41823(0.0f, -3.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.625f, AnimationHelper.method_41823(0.0f, -4.0f, -1.0f), Interpolations.field_37885),
			new Keyframe(1.9167f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.7083f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.875f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_ribcage", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.5417f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.7917f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.875f, AnimationHelper.method_41829(0.0f, 125.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.5f, AnimationHelper.method_41829(0.0f, 125.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.6667f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_ribcage", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.5417f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.7917f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.875f, AnimationHelper.method_41829(0.0f, -125.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.5f, AnimationHelper.method_41829(0.0f, -125.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.6667f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.0f, AnimationHelper.method_41829(67.5f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.75f, AnimationHelper.method_41829(80.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.9167f, AnimationHelper.method_41829(-45.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.5f, AnimationHelper.method_41829(-45.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.7083f, AnimationHelper.method_41829(-45.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.875f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("head", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.9167f, AnimationHelper.method_41823(0.0f, 0.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(2.5f, AnimationHelper.method_41823(0.0f, 0.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(2.7083f, AnimationHelper.method_41823(0.0f, 0.0f, -3.0f), Interpolations.field_37885),
			new Keyframe(2.875f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.875f, AnimationHelper.method_41829(-42.28659f, -32.69813f, -5.00825f), Interpolations.field_37885),
			new Keyframe(1.1667f, AnimationHelper.method_41829(-29.83757f, -35.39626f, -45.28089f), Interpolations.field_37885),
			new Keyframe(1.3333f, AnimationHelper.method_41829(-29.83757f, -35.39626f, -45.28089f), Interpolations.field_37885),
			new Keyframe(1.6667f, AnimationHelper.method_41829(-72.28659f, -32.69813f, -5.00825f), Interpolations.field_37885),
			new Keyframe(1.8333f, AnimationHelper.method_41829(35.26439f, -30.0f, 35.26439f), Interpolations.field_37885),
			new Keyframe(1.9167f, AnimationHelper.method_41829(73.75484f, -13.0931f, 19.20518f), Interpolations.field_37885),
			new Keyframe(2.5f, AnimationHelper.method_41829(73.75484f, -13.0931f, 19.20518f), Interpolations.field_37885),
			new Keyframe(2.75f, AnimationHelper.method_41829(58.20713f, -21.1064f, 28.7261f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("right_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8333f, AnimationHelper.method_41823(3.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.75f, AnimationHelper.method_41823(3.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.ROTATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(0.875f, AnimationHelper.method_41829(-33.80694f, 32.31058f, 6.87997f), Interpolations.field_37885),
			new Keyframe(1.1667f, AnimationHelper.method_41829(-17.87827f, 34.62115f, 49.02433f), Interpolations.field_37885),
			new Keyframe(1.3333f, AnimationHelper.method_41829(-17.87827f, 34.62115f, 49.02433f), Interpolations.field_37885),
			new Keyframe(1.6667f, AnimationHelper.method_41829(-51.30694f, 32.31058f, 6.87997f), Interpolations.field_37885),
			new Keyframe(1.8333f, AnimationHelper.method_41829(35.26439f, 30.0f, -35.26439f), Interpolations.field_37885),
			new Keyframe(1.9167f, AnimationHelper.method_41829(73.75484f, 13.0931f, -19.20518f), Interpolations.field_37885),
			new Keyframe(2.5f, AnimationHelper.method_41829(73.75484f, 13.0931f, -19.20518f), Interpolations.field_37885),
			new Keyframe(2.75f, AnimationHelper.method_41829(58.20713f, 21.1064f, -28.7261f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41829(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).addBoneAnimation("left_arm", new Transformation(Targets.TRANSLATE, new Keyframe[] {
			new Keyframe(0.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(1.8333f, AnimationHelper.method_41823(-3.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(2.75f, AnimationHelper.method_41823(-3.0f, 0.0f, 0.0f), Interpolations.field_37885),
			new Keyframe(3.0f, AnimationHelper.method_41823(0.0f, 0.0f, 0.0f), Interpolations.field_37885) }
	)).build();
}
