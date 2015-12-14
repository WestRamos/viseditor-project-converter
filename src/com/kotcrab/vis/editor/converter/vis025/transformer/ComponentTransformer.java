package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;

/** @author Kotcrab */
public abstract class ComponentTransformer<T extends Component> {
	public abstract void transform (Entity entity, Bag<Component> sourceComponents, Array<Component> components, T component);
}
