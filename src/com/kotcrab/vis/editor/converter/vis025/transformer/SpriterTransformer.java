package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.proto.ProtoVisSpriter;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;
import com.kotcrab.vis.runtime.component.SpriterComponent;

/** @author Kotcrab */
public class SpriterTransformer extends ComponentTransformer<SpriterComponent> {
	@Override
	public void transform (Entity entity, Array<Component> components, SpriterComponent component) {
		ProtoVisSpriter spriter = new ProtoVisSpriter();
		spriter.scale = component.player.getScale();

		spriter.defaultAnimation = component.defaultAnimation;
		spriter.playOnStart = component.playOnStart;

		spriter.flipX = component.isFlipX();
		spriter.flipY = component.isFlipY();

		components.add(spriter);
	}
}
