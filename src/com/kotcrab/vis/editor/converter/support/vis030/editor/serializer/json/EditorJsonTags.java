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

package com.kotcrab.vis.editor.converter.support.vis030.editor.serializer.json;

import com.kotcrab.vis.editor.converter.support.vis030.editor.component.PixelsPerUnit;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.SpriterProperties;
import com.kotcrab.vis.editor.converter.support.vis030.editor.component.VisUUID;
import com.kotcrab.vis.editor.converter.support.vis030.editor.scene.EditorLayer;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.util.json.JsonTagRegistrar;
import com.kotcrab.vis.editor.converter.support.vis030.runtime.util.json.RuntimeJsonTags;
import com.kotcrab.vis.editor.entity.EntityScheme;
import com.kotcrab.vis.editor.entity.ExporterDropsComponent;

/** @author Kotcrab */
public class EditorJsonTags {
	public static void registerTags (JsonTagRegistrar registrar) {
		RuntimeJsonTags.registerTags(registrar);

		registrar.register("EditorLayer", EditorLayer.class);
		registrar.register("EntityScheme", EntityScheme.class);

		registrar.register("VisUUID", VisUUID.class);
		registrar.register("PixelsPerUnit", PixelsPerUnit.class);
		registrar.register("ExporterDropsComponent", ExporterDropsComponent.class);
		registrar.register("SpriterProperties", SpriterProperties.class);
	}
}
