package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Point;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.PointComponent;

/** @author Kotcrab */
public class PointTransformer extends ComponentTransformer<PointComponent> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Bag<Component> components, PointComponent component) {
		Transform transform = new Transform();
		transform.setPosition(component.x, component.y);

		components.add(new Point());
		components.add(transform);
	}
}
