package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Origin;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Tint;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Transform;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.SpriteComponent;

/** @author Kotcrab */
public class SpriteTransformer extends ComponentTransformer<SpriteComponent> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Bag<Component> components, SpriteComponent sprite) {
		Transform transform = new Transform();
		Origin origin = new Origin();
		Tint tint = new Tint();
		VisSprite visSprite = new VisSprite(sprite.sprite);

		visSprite.setSize(sprite.getWidth(), sprite.getHeight());
		visSprite.setFlip(sprite.isFlipX(), sprite.isFlipY());

		transform.setPosition(sprite.getX(), sprite.getY());
		transform.setRotation(sprite.getRotation());
		transform.setScale(sprite.getScaleX(), sprite.getScaleY());
		tint.setTint(sprite.getColor());
		origin.setOrigin(sprite.getOriginX(), sprite.getOriginY());

		components.add(visSprite);
		components.add(transform);
		components.add(tint);
		components.add(origin);
	}
}
