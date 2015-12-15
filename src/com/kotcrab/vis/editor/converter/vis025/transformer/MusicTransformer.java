package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisMusic;
import com.kotcrab.vis.editor.plugin.api.support.ComponentTransformer;
import com.kotcrab.vis.runtime.component.MusicComponent;

/** @author Kotcrab */
public class MusicTransformer extends ComponentTransformer<MusicComponent> {
	@Override
	public void transform (Entity entity, Array<Component> components, MusicComponent component) {
		components.add(new VisMusic(component.music));
	}
}
