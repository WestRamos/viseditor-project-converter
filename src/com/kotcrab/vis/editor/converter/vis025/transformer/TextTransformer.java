package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Origin;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Tint;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Transform;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisText;
import com.kotcrab.vis.runtime.component.TextComponent;

/** @author Kotcrab */
public class TextTransformer extends ComponentTransformer<TextComponent> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Bag<Component> components, TextComponent text) {
		Transform transform = new Transform();
		Origin origin = new Origin();
		Tint tint = new Tint();
		VisText visText = new VisText(text.getCache().getFont(), text.getText());
		visText.setAutoSetOriginToCenter(text.isAutoSetOriginToCenter());
		visText.setDistanceFieldShaderEnabled(text.isDistanceFieldShaderEnabled());

		transform.setPosition(text.getX(), text.getY());
		transform.setRotation(text.getRotation());
		transform.setScale(text.getScaleX(), text.getScaleY());
		tint.setTint(text.getColor());
		origin.setOrigin(text.getOriginX(), text.getOriginY());

		components.add(visText);
		components.add(transform);
		components.add(tint);
		components.add(origin);
	}
}
