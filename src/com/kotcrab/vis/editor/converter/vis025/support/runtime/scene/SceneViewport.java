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

package com.kotcrab.vis.editor.converter.vis025.support.runtime.scene;

import com.kotcrab.vis.editor.converter.vis025.support.runtime.util.PrettyEnum;

/**
 * Enum with possible viewport types used for {@link Scene}
 * @author Kotcrab
 */
public enum SceneViewport implements PrettyEnum {
	STRETCH {
		@Override
		public String toPrettyString () {
			return "Stretch Viewport";
		}
	}, FIT {
		@Override
		public String toPrettyString () {
			return "Fit Viewport";
		}
	}, FILL {
		@Override
		public String toPrettyString () {
			return "Fill Viewport";
		}
	}, SCREEN {
		@Override
		public String toPrettyString () {
			return "Screen Viewport";
		}
	}, EXTEND {
		@Override
		public String toPrettyString () {
			return "Extend Viewport";
		}
	}
}
