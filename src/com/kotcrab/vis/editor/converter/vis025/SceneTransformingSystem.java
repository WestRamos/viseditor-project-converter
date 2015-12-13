package com.kotcrab.vis.editor.converter.vis025;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kotcrab.vis.editor.converter.vis025.support.editor.component.PixelsPerUnit;
import com.kotcrab.vis.editor.converter.vis025.support.editor.component.SpriterProperties;
import com.kotcrab.vis.editor.converter.vis025.support.editor.component.VisUUID;
import com.kotcrab.vis.editor.converter.vis025.support.editor.scene.EntityScheme;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.*;
import com.kotcrab.vis.editor.converter.vis025.transformer.*;
import com.kotcrab.vis.editor.entity.ExporterDropsComponent;
import com.kotcrab.vis.editor.entity.PixelsPerUnitComponent;
import com.kotcrab.vis.editor.entity.SpriterPropertiesComponent;
import com.kotcrab.vis.editor.entity.UUIDComponent;
import com.kotcrab.vis.runtime.component.*;

/** @author Kotcrab */
public class SceneTransformingSystem extends EntityProcessingSystem {
	private ConvertTask task;

	private ObjectMap<Class, ComponentTransformer> transformers = new ObjectMap<>();
	private Array<ConditionalComponentTransformers> condTransformers = new Array<>();

	private Bag<Component> sourceComponents = new Bag<>();

	private Bag<Component> targetComponents = new Bag<>();
	private Array<EntityScheme> results = new Array<>();

	public SceneTransformingSystem (ConvertTask task) {
		super(Aspect.getEmpty());
		this.task = task;
		setPassive(true);

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
		transformers.put(ExporterDropsComponent.class, new DoNothingTransformer());

		condTransformers.add(new AudioPositionComponentTransformer());
	}

	public Array<EntityScheme> convertScene () {
		results.clear();
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
				for (ConditionalComponentTransformers condTransformer : condTransformers) {
					if (condTransformer.getSourceComponentClass().equals(component.getClass()) && condTransformer.accept(entity, sourceComponents)) {
						transformer = condTransformer;
					}
				}
			}

			if (transformer != null) {
				task.log("\tComponent : " + component.getClass().getSimpleName() + ", using " + transformer.getClass().getSimpleName());
				transformer.transform(entity, sourceComponents, targetComponents, component);
			} else {
				task.logError("\tMissing transformer for type: " + component.getClass().getSimpleName());
			}
		}

		results.add(new EntityScheme(targetComponents));
	}
}
