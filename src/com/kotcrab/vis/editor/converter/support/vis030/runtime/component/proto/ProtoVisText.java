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

package com.kotcrab.vis.editor.converter.support.vis030.runtime.component.proto;

import com.kotcrab.vis.editor.converter.support.vis030.runtime.component.VisText;

/**
 * {@link ProtoComponent} for {@link VisText}
 * @author Kotcrab
 * @see TextInflater
 */
public class ProtoVisText extends ProtoComponent<VisText>{
	public String text;
	public boolean autoSetOriginToCenter;
	public boolean isUsesDistanceField;

	public ProtoVisText () {
	}

	public ProtoVisText (VisText component) {
		text = component.getText();
		autoSetOriginToCenter = component.isAutoSetOriginToCenter();

		isUsesDistanceField = component.isDistanceFieldShaderEnabled();
	}

	@Override
	public void fill (VisText component) {
		component.setText(text);
		component.setAutoSetOriginToCenter(autoSetOriginToCenter);
		component.setDistanceFieldShaderEnabled(isUsesDistanceField);
	}
}