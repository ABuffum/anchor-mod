package fun.mousewich.gen.data.fabric;

/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import fun.mousewich.gen.data.minecraft.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;

/**
 * Extend this class and implement {@link FabricModelProvider#generateBlockStateModels} and {@link FabricModelProvider#generateItemModels}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}
 */
public abstract class FabricModelProvider extends ModelProvider {
	protected final FabricDataGenerator dataGenerator;

	public FabricModelProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
		this.dataGenerator = dataGenerator;
	}

	public abstract void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator);

	public abstract void generateItemModels(ItemModelGenerator itemModelGenerator);

	public String getName() { return "Models"; }
}
