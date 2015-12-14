package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Transform;
import com.kotcrab.vis.editor.entity.EditorPositionComponent;
import com.kotcrab.vis.runtime.component.MusicComponent;
import com.kotcrab.vis.runtime.component.SoundComponent;

/** @author Kotcrab */
public class AudioPositionComponentTransformer extends ConditionalComponentTransformers<EditorPositionComponent> {
	@Override
	public Class getSourceComponentClass () {
		return EditorPositionComponent.class;
	}

	@Override
	public boolean accept (Entity entity, Bag sourceComponents) {
		return entity.getComponent(SoundComponent.class) != null || entity.getComponent(MusicComponent.class) != null;
	}

	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Array<Component> components, EditorPositionComponent pos) {
		Transform transform = new Transform();
		transform.setPosition(pos.x, pos.y);
		components.add(transform);
	}
}
