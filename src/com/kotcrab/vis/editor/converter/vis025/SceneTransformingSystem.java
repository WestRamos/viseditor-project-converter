package com.kotcrab.vis.editor.converter.vis025;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kotcrab.vis.editor.VersionCodes;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.PixelsPerUnit;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.SpriterProperties;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.VisUUID;
import com.kotcrab.vis.editor.converter.support.vis030.editor.scene.EntityScheme;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.*;
import com.kotcrab.vis.editor.converter.vis025.transformer.*;
import com.kotcrab.vis.editor.entity.ExporterDropsComponent;
import com.kotcrab.vis.editor.entity.PixelsPerUnitComponent;
import com.kotcrab.vis.editor.entity.SpriterPropertiesComponent;
import com.kotcrab.vis.editor.entity.UUIDComponent;
import com.kotcrab.vis.editor.module.editor.ExtensionStorageModule;
import com.kotcrab.vis.editor.plugin.api.ComponentTransformerProvider;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;
import com.kotcrab.vis.editor.plugin.api.support.ConditionalComponentTransformer;
import com.kotcrab.vis.editor.plugin.api.support.RemapTransformer;
import com.kotcrab.vis.runtime.component.*;

/** @author Kotcrab */
public class SceneTransformingSystem extends EntityProcessingSystem {
	private ConvertTask task;

	private ObjectMap<Class, ComponentTransformer> transformers = new ObjectMap<>();
	private Array<ConditionalComponentTransformer> condTransformers = new Array<>();

	private Bag<Component> sourceComponents = new Bag<>();

	private Array<Component> targetComponents = new Array<>();
	private Array<EntityScheme> results = new Array<>();

	public SceneTransformingSystem (ConvertTask task, ExtensionStorageModule extensionStorage) {
		super(Aspect.getEmpty());
		this.task = task;
		setPassive(true);

		transformers.put(SpriteComponent.class, new SpriteTransformer());
		transformers.put(TextComponent.class, new TextTransformer());
		transformers.put(SoundComponent.class, new SoundTransformer());
		transformers.put(MusicComponent.class, new MusicTransformer());
		transformers.put(ParticleComponent.class, new ParticleTransformer());
		transformers.put(SpriterComponent.class, new SpriterTransformer());

		transformers.put(PointComponent.class, new PointTransformer());
		transformers.put(AssetComponent.class, new AssetTransformer());

		transformers.put(IDComponent.class, new RemapTransformer(VisID.class));
		transformers.put(GroupComponent.class, new RemapTransformer(VisGroup.class));
		transformers.put(VariablesComponent.class, new RemapTransformer(Variables.class));
		transformers.put(ShaderComponent.class, new RemapTransformer(Shader.class));
		transformers.put(RenderableComponent.class, new RemapTransformer(Renderable.class));
		transformers.put(PolygonComponent.class, new RemapTransformer(VisPolygon.class));
		transformers.put(PhysicsSpriteComponent.class, new RemapTransformer(PhysicsSprite.class));
		transformers.put(PhysicsPropertiesComponent.class, new RemapTransformer(PhysicsProperties.class));
		transformers.put(PhysicsComponent.class, new RemapTransformer(PhysicsBody.class));
		transformers.put(LayerComponent.class, new RemapTransformer(Layer.class));
		transformers.put(InvisibleComponent.class, new RemapTransformer(Invisible.class));

		transformers.put(UUIDComponent.class, new RemapTransformer(VisUUID.class));
		transformers.put(PixelsPerUnitComponent.class, new RemapTransformer(PixelsPerUnit.class));
		transformers.put(SpriterPropertiesComponent.class, new RemapTransformer(SpriterProperties.class));

		ExporterDropsComponentTransformer exporterDropsTransformer = new ExporterDropsComponentTransformer(task);
		transformers.put(ExporterDropsComponent.class, exporterDropsTransformer);

		condTransformers.add(new AudioPositionComponentTransformer());

		for (ComponentTransformerProvider provider : extensionStorage.getComponentTransformerProviders()) {
			if (provider.getSourceProjectVersions() == VersionCodes.EDITOR_026 && provider.getTargetProjectVersions() == VersionCodes.EDITOR_030) {
				provider.registerTransformers(transformers);
				provider.registerConditionalTransformers(condTransformers);

				provider.registerClassMaps(exporterDropsTransformer.getClassMap());
			}
		}
	}

	public Array<EntityScheme> convertScene () {
		results = new Array<>();
		process();
		return results;
	}

	@Override
	protected void process (Entity entity) {
		task.log("Process entity " + entity.getId());
		sourceComponents.clear();
		targetComponents.clear();
		entity.getComponents(sourceComponents);

		for (Component component : sourceComponents) {
			ComponentTransformer transformer = transformers.get(component.getClass());

			if (transformer == null) {
				for (ConditionalComponentTransformer condTransformer : condTransformers) {
					if (condTransformer.getSourceComponentClass().equals(component.getClass()) && condTransformer.accept(entity, sourceComponents)) {
						transformer = condTransformer;
					}
				}
			}

			if (transformer != null) {
				task.log("\tComponent: " + component.getClass().getSimpleName() + ", using " + transformer.getClass().getSimpleName());
				transformer.transform(entity, targetComponents, component);
			} else {
				task.logError("\tMissing transformer for type: " + component.getClass().getSimpleName());
			}
		}

		Array<Component> arrayClone = new Array<>(targetComponents.size);
		arrayClone.addAll(targetComponents);
		results.add(new EntityScheme(arrayClone));
	}
}
