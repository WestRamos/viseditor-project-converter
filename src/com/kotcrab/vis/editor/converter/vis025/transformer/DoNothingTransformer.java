package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;

/** @author Kotcrab */
public class DoNothingTransformer extends ComponentTransformer<Component> {
	@Override
	public void transform (Entity entity, Array<Component> components, Component component) {
		components.add(component);
	}
}
