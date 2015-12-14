package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisSound;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;
import com.kotcrab.vis.runtime.component.SoundComponent;

/** @author Kotcrab */
public class SoundTransformer extends ComponentTransformer<SoundComponent> {
	@Override
	public void transform (Entity entity, Array<Component> components, SoundComponent component) {
		components.add(new VisSound(component.sound));
	}
}
