package com.kotcrab.vis.editor.converter.vis025.module;

import com.google.common.eventbus.Subscribe;
import com.kotcrab.vis.editor.Editor;
import com.kotcrab.vis.editor.converter.vis025.ConvertProjectEvent;
import com.kotcrab.vis.editor.converter.vis025.ConversionDialog;
import com.kotcrab.vis.editor.module.EventBusSubscriber;
import com.kotcrab.vis.editor.module.project.ProjectModule;
import com.kotcrab.vis.editor.plugin.ContainerExtension;
import com.kotcrab.vis.runtime.plugin.VisPlugin;

/** @author Kotcrab */
@VisPlugin
@EventBusSubscriber
public class ConverterTaskModule extends ProjectModule implements ContainerExtension {
	@Subscribe
	public void handleConvertProject (ConvertProjectEvent event) {
		Editor.instance.getStage().addActor(new ConversionDialog(projectContainer).fadeIn());
	}

	@Override
	public ExtensionScope getScope () {
		return ExtensionScope.PROJECT;
	}
}
