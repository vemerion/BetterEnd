package ru.betterend.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import ru.betterend.blocks.basis.BlockBaseNotFull;
import ru.betterend.patterns.Patterns;

public class EndPathBlock extends BlockBaseNotFull {
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 15, 16);
	
	public EndPathBlock(Block source) {
		super(FabricBlockSettings.copyOf(source).allowsSpawning((state, world, pos, type) -> { return false; }));
		if (source instanceof EndTerrainBlock) {
			EndTerrainBlock terrain = (EndTerrainBlock) source;
			terrain.setPathBlock(this);
		}
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool != null && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0) {
			return Collections.singletonList(new ItemStack(this));
		}
		return Collections.singletonList(new ItemStack(Blocks.END_STONE));
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return SHAPE;
	}
	
	@Override
	public String getModelPattern(String block) {
		String name = Registry.BLOCK.getId(this).getPath();
		Map<String, String> map = Maps.newHashMap();
		map.put("%top%", name + "_top");
		map.put("%side%", name.replace("_path", "") + "_side");
		return Patterns.createJson(Patterns.BLOCK_PATH, map);
	}
	
	@Override
	public Identifier statePatternId() {
		return Patterns.STATE_ROTATED_TOP;
	}
}
