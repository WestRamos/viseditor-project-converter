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

		classMap.put(TextComponent.class, VisText.class);
		classMap.put(SoundComponent.class, VisSound.class);
		classMap.put(MusicComponent.class, VisMusic.class);
		classMap.put(ParticleComponent.class, VisParticle.class);
		classMap.put(SpriterComponent.class, VisSprite.class);
		classMap.put(SpriteComponent.class, VisSprite.class);
		classMap.put(IDComponent.class, VisID.class);
		classMap.put(GroupComponent.class, VisGroup.class);
		classMap.put(VariablesComponent.class, Variables.class);
		classMap.put(ShaderComponent.class, Shader.class);
		classMap.put(RenderableComponent.class, Renderable.class);
		classMap.put(PolygonComponent.class, VisPolygon.class);
		classMap.put(PointComponent.class, Point.class);
		classMap.put(PhysicsSpriteComponent.class, PhysicsSprite.class);
		classMap.put(PhysicsPropertiesComponent.class, PhysicsPropertiesComponent.class);
		classMap.put(PhysicsComponent.class, PhysicsBody.class);
		classMap.put(LayerComponent.class, Layer.class);
		classMap.put(InvisibleComponent.class, Invisible.class);
		classMap.put(AssetComponent.class, AssetReference.class);

		classMap.put(UUIDComponent.class, VisUUID.class);
		classMap.put(PixelsPerUnitComponent.class, PixelsPerUnit.class);
		classMap.put(SpriterPropertiesComponent.class, SpriterProperties.class);
		classMap.put(EditorPositionComponent.class, Transform.class);

		this.task = task;
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
