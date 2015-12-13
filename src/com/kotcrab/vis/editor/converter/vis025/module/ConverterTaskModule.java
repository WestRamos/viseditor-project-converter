package com.kotcrab.vis.editor.converter.vis025.module;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.editor.Editor;
import com.kotcrab.vis.editor.converter.vis025.ConversionDialog;
import com.kotcrab.vis.editor.converter.vis025.ConvertProjectEvent;
import com.kotcrab.vis.editor.module.EventBusSubscriber;
import com.kotcrab.vis.editor.module.editor.TabsModule;
import com.kotcrab.vis.editor.module.project.ProjectModule;
import com.kotcrab.vis.editor.plugin.ContainerExtension;
import com.kotcrab.vis.runtime.plugin.VisPlugin;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;

/** @author Kotcrab */
@VisPlugin
@EventBusSubscriber
public class ConverterTaskModule extends ProjectModule implements ContainerExtension {
	private TabsModule tabsModule;

	private Stage stage;

	@Subscribe
	public void handleConvertProject (ConvertProjectEvent event) {
		if (tabsModule.getTabs().size != 0)
			DialogUtils.showOKDialog(stage, "Warning", "Please close all open tabs before convering");
		else
			Editor.instance.getStage().addActor(new ConversionDialog(projectContainer).fadeIn());
	}

	@Override
	public ExtensionScope getScope () {
		return ExtensionScope.PROJECT;
	}
}
