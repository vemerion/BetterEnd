package ru.betterend.integration.byg.biomes;

import java.util.List;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import ru.betterend.BetterEnd;
import ru.betterend.integration.Integrations;
import ru.betterend.integration.byg.features.BYGFeatures;
import ru.betterend.registry.EndFeatures;
import ru.betterend.world.biome.BiomeDefinition;
import ru.betterend.world.biome.EndBiome;

public class NightshadeRedwoods extends EndBiome {
	public NightshadeRedwoods() {
		super(makeDef());
	}
	
	private static BiomeDefinition makeDef() {
		Biome biome = Integrations.BYG.getBiome("nightshade_forest");
		BiomeSpecialEffects effects = biome.getSpecialEffects();
		
		BiomeDefinition def = new BiomeDefinition("nightshade_redwoods")
				.setFogColor(140, 108, 47)
				.setFogDensity(1.5F)
				.setWaterAndFogColor(55, 70, 186)
				.setFoliageColor(122, 17, 155)
				.setParticles(ParticleTypes.REVERSE_PORTAL, 0.002F)
				.setSurface(biome.getGenerationSettings().getSurfaceBuilder().get())
				.setGrassColor(48, 13, 89)
				.setPlantsColor(200, 125, 9)
				.addFeature(EndFeatures.END_LAKE_RARE)
				.addFeature(BYGFeatures.NIGHTSHADE_REDWOOD_TREE)
				.addFeature(BYGFeatures.NIGHTSHADE_MOSS_WOOD)
				.addFeature(BYGFeatures.NIGHTSHADE_MOSS);
		
		if (BetterEnd.isClient()) {
			SoundEvent loop = effects.getAmbientLoopSoundEvent().get();
			SoundEvent music = effects.getBackgroundMusic().get().getEvent();
			SoundEvent additions = effects.getAmbientAdditionsSettings().get().getSoundEvent();
			SoundEvent mood = effects.getAmbientMoodSettings().get().getSoundEvent();
			def.setLoop(loop).setMusic(music).setAdditions(additions).setMood(mood);
		}
		biome.getGenerationSettings().features().forEach((list) -> {
			list.forEach((feature) -> {
				def.addFeature(Decoration.VEGETAL_DECORATION, feature.get());
			});
		});
		
		for (MobCategory group: MobCategory.values()) {
			List<SpawnerData> list = biome.getMobSettings().getMobs(group);
			list.forEach((entry) -> {
				def.addMobSpawn(entry);
			});
		}
		
		return def;
	}
}
