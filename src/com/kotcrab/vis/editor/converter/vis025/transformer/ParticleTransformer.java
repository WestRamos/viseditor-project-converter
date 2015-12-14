package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.Transform;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisParticle;
import com.kotcrab.vis.runtime.component.ParticleComponent;

/** @author Kotcrab */
public class ParticleTransformer extends ComponentTransformer<ParticleComponent> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Array<Component> components, ParticleComponent component) {
		VisParticle particle = new VisParticle(component.effect);
		particle.setActiveOnStart(component.active);

		Transform transform = new Transform();
		transform.setPosition(component.getX(), component.getY());

		components.add(particle);
		components.add(transform);
	}
}
