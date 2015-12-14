package com.kotcrab.vis.editor.converter.support.vis030.editor.scene;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.properties.UsesProtoComponent;

/** @author Kotcrab */
public class EntityScheme {

	public Array<Component> components;

	public EntityScheme (Array<Component> origComponents) {
		components = new Array<>(origComponents.size);

		for (Component component : origComponents) {
			if (component.getClass().isAnnotationPresent(Transient.class)) continue;

			if (component instanceof UsesProtoComponent) {
				components.add(((UsesProtoComponent) component).toProtoComponent());
			} else {
				components.add(component);
			}
		}
	}
}
