package com.kotcrab.vis.editor.converter.vis025.module;

import com.kotcrab.vis.editor.App;
import com.kotcrab.vis.editor.converter.vis025.ConvertProjectEvent;
import com.kotcrab.vis.editor.module.editor.EditorModule;
import com.kotcrab.vis.editor.module.editor.MenuBarModule;
import com.kotcrab.vis.editor.module.editor.MenuBarModule.ControllerPolicy;
import com.kotcrab.vis.editor.plugin.ContainerExtension;
import com.kotcrab.vis.runtime.plugin.VisPlugin;

/** @author Kotcrab */
@VisPlugin
public class ConverterMenuModule extends EditorModule implements ContainerExtension {
	private MenuBarModule menuBar;

	@Override
	public void init () {
		super.init();
	}

	@Override
	public void postInit () {
		menuBar.getToolsMenu().addItem(menuBar.createMenuItem(ControllerPolicy.PROJECT, "Convert to VisEditor 0.3.0", () -> {
			App.eventBus.post(new ConvertProjectEvent());
		}));
		super.postInit();
	}

	@Override
	public ExtensionScope getScope () {
		return ExtensionScope.EDITOR;
	}
}
