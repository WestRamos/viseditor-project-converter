package com.kotcrab.vis.editor.converter.vis025.transformer;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.assets.MusicAsset;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.assets.ParticleAsset;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.assets.SoundAsset;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.AssetReference;
import com.kotcrab.vis.runtime.assets.PathAsset;
import com.kotcrab.vis.runtime.component.AssetComponent;
import com.kotcrab.vis.runtime.component.MusicComponent;
import com.kotcrab.vis.runtime.component.ParticleComponent;
import com.kotcrab.vis.runtime.component.SoundComponent;

/** @author Kotcrab */
public class AssetTransformer extends ComponentTransformer<AssetComponent> {
	@Override
	public void transform (Entity entity, Bag<Component> sourceComponents, Array<Component> components, AssetComponent component) {
		if (entity.getComponent(ParticleComponent.class) != null) {
			PathAsset pathAsset = (PathAsset) component.getAsset();
			components.add(new AssetReference(new ParticleAsset(pathAsset.getPath())));
		} else if (entity.getComponent(SoundComponent.class) != null) {
			PathAsset pathAsset = (PathAsset) component.getAsset();
			components.add(new AssetReference(new SoundAsset(pathAsset.getPath())));
		} else if (entity.getComponent(MusicComponent.class) != null) {
			PathAsset pathAsset = (PathAsset) component.getAsset();
			components.add(new AssetReference(new MusicAsset(pathAsset.getPath())));
		} else {
			components.add(new AssetReference(component.getAsset()));
		}
	}
}
