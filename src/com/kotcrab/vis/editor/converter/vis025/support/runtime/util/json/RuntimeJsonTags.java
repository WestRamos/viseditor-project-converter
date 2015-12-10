/*
 * Copyright 2014-2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotcrab.vis.editor.converter.vis025.support.runtime.util.json;

import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.AtlasRegionAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.BmpFontAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.MusicAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.ParticleAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.PathAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.ShaderAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.SoundAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.SpriterAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.TextureRegionAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.assets.TtfFontAsset;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.AssetReference;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Invisible;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Layer;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Origin;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.PhysicsProperties;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Point;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Renderable;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Tint;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Transform;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.Variables;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.VisGroup;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.VisID;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.VisPolygon;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoShader;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoVisMusic;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoVisParticle;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoVisSound;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoVisSprite;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoVisSpriter;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.component.proto.ProtoVisText;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.data.SceneData;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.scene.LayerCordsSystem;
import com.kotcrab.vis.editor.converter.vis025.support.runtime.scene.SceneViewport;

/** @author Kotcrab */
public class RuntimeJsonTags {
	public static void registerTags (JsonTagRegistrar registrar) {
		registrar.register("String", String.class);
		registrar.register("Class", Class.class);

		registrar.register("SceneData", SceneData.class);
		registrar.register("SceneViewport", SceneViewport.class);
		registrar.register("LayerCordsSystem", LayerCordsSystem.class);

		registrar.register("AtlasRegionAsset", AtlasRegionAsset.class);
		registrar.register("BmpFontAsset", BmpFontAsset.class);
		registrar.register("MusicAsset", MusicAsset.class);
		registrar.register("ParticleAsset", ParticleAsset.class);
		registrar.register("PathAsset", PathAsset.class);
		registrar.register("ShaderAsset", ShaderAsset.class);
		registrar.register("SoundAsset", SoundAsset.class);
		registrar.register("SpriterAsset", SpriterAsset.class);
		registrar.register("TextureRegionAsset", TextureRegionAsset.class);
		registrar.register("TtfFontAsset", TtfFontAsset.class);

		registrar.register("Transform", Transform.class);
		registrar.register("Tint", Tint.class);
		registrar.register("Origin", Origin.class);

		registrar.register("AssetReference", AssetReference.class);
		registrar.register("VisGroup", VisGroup.class);
		registrar.register("VisID", VisID.class);
		registrar.register("VisPolygon", VisPolygon.class);
		registrar.register("Invisible", Invisible.class);
		registrar.register("Layer", Layer.class);
		registrar.register("Renderable", Renderable.class);
		registrar.register("Variables", Variables.class);
		registrar.register("PhysicsProperties", PhysicsProperties.class);
		registrar.register("Point", Point.class);

		registrar.register("ProtoVisSprite", ProtoVisSprite.class);
		registrar.register("ProtoVisMusic", ProtoVisMusic.class);
		registrar.register("ProtoVisSound", ProtoVisSound.class);
		registrar.register("ProtoVisParticle", ProtoVisParticle.class);
		registrar.register("ProtoVisText", ProtoVisText.class);
		registrar.register("ProtoShader", ProtoShader.class);
		registrar.register("ProtoVisSpriter", ProtoVisSpriter.class);
	}
}
