package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/** @author Kotcrab */
public class RemapTransformer extends ComponentTransformer<Component> {
	private Class<? extends Component> targetClass;

	public RemapTransformer (Class<? extends Component> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	public void transform (Entity entity, Array<Component> components, Component source) {
		try {
			Constructor constructor = targetClass.getConstructor();
			Component target = (Component) constructor.newInstance();

			for (Field field : source.getClass().getDeclaredFields()) {
				Field targetField = targetClass.getDeclaredField(field.getName());
				targetField.setAccessible(true);
				field.setAccessible(true);
				targetField.set(target, field.get(source));
			}
			components.add(target);
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}
}
