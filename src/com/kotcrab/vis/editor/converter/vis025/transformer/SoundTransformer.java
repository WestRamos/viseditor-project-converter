package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisSound;
import com.kotcrab.vis.runtime.component.SoundComponent;

/** @author Kotcrab */
public class SoundTransformer extends ComponentTransformer<SoundComponent> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Bag<Component> components, SoundComponent component) {
		components.add(new VisSound(component.sound));
	}
}
