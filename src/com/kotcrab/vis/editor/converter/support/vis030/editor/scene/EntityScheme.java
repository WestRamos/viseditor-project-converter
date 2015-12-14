package com.kotcrab.vis.editor.converter.support.vis030.editor.scene;

import com.artemis.Component;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

/** @author Kotcrab */
public class EntityScheme {
	public ImmutableBag<Component> components;

	public EntityScheme (Bag<Component> components) {
		this.components = components;
	}
}
