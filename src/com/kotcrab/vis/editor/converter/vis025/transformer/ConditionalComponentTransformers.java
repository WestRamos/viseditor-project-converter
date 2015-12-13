package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;

/** @author Kotcrab */
public abstract class ConditionalComponentTransformers<T extends Component> extends ComponentTransformer<T> {
	public abstract Class getSourceComponentClass();

	public abstract boolean accept (Entity entity, Bag<Component> sourceComponents);
}
