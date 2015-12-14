package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;

/** @author Kotcrab */
public class DoNothingTransformer extends ComponentTransformer<Component> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Array<Component> components, Component component) {
		components.add(component);
	}
}
