package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.PixelsPerUnit;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.SpriterProperties;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.VisUUID;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.*;
import com.kotcrab.vis.editor.converter.vis025.ConvertTask;
import com.kotcrab.vis.editor.entity.*;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;
import com.kotcrab.vis.runtime.component.*;

/** @author Kotcrab */
public class ExporterDropsComponentTransformer extends ComponentTransformer<ExporterDropsComponent> {
	private final ConvertTask task;

	private ObjectMap<Class, Class> classMap = new ObjectMap<>();

	public ExporterDropsComponentTransformer (ConvertTask task) {
		register(TextComponent.class, VisText.class);
		register(SoundComponent.class, VisSound.class);
		register(MusicComponent.class, VisMusic.class);
		register(ParticleComponent.class, VisParticle.class);
		register(SpriterComponent.class, VisSprite.class);
		register(SpriteComponent.class, VisSprite.class);
		register(IDComponent.class, VisID.class);
		register(GroupComponent.class, VisGroup.class);
		register(VariablesComponent.class, Variables.class);
		register(ShaderComponent.class, Shader.class);
		register(RenderableComponent.class, Renderable.class);
		register(PolygonComponent.class, VisPolygon.class);
		register(PointComponent.class, Point.class);
		register(PhysicsSpriteComponent.class, PhysicsSprite.class);
		register(PhysicsPropertiesComponent.class, PhysicsPropertiesComponent.class);
		register(PhysicsComponent.class, PhysicsBody.class);
		register(LayerComponent.class, Layer.class);
		register(InvisibleComponent.class, Invisible.class);
		register(AssetComponent.class, AssetReference.class);

		register(UUIDComponent.class, VisUUID.class);
		register(PixelsPerUnitComponent.class, PixelsPerUnit.class);
		register(SpriterPropertiesComponent.class, SpriterProperties.class);
		register(EditorPositionComponent.class, Transform.class);

		this.task = task;
	}

	void register (Class sourceClass, Class targetClass) {
		classMap.put(sourceClass, targetClass);
	}

	public ObjectMap<Class, Class> getClassMap () {
		return classMap;
	}

	@Override
	public void transform (Entity entity, Array<Component> components, ExporterDropsComponent component) {
		ExporterDropsComponent newComponent = new ExporterDropsComponent();

		for (Class clazz : component.componentsToDrop) {
			Class newClazz = classMap.get(clazz);

			if (newClazz == null) {
				task.logError("\t\tMissing class map for: " + clazz.getSimpleName());
			} else {
				newComponent.componentsToDrop.add(newClazz);
			}
		}

		components.add(newComponent);
	}
}
