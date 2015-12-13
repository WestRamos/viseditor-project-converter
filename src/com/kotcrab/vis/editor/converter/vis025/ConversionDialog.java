package com.kotcrab.vis.editor.converter.vis025;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kotcrab.vis.editor.module.editor.FileChooserModule;
import com.kotcrab.vis.editor.module.project.ProjectModuleContainer;
import com.kotcrab.vis.editor.ui.dialog.AsyncTaskProgressDialog;
import com.kotcrab.vis.editor.util.gdx.VisChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.form.FormValidator;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;

/** @author Kotcrab */
public class ConversionDialog extends VisWindow {
	private FileChooserModule fileChooser;

	private VisValidatableTextField pathField;
	private VisTextButton chooseButton;

	private VisLabel errorLabel;
	private VisTextButton cancelButton;
	private VisTextButton convertButton;

	public ConversionDialog (ProjectModuleContainer projectMC) {
		super("Project Converter");
		projectMC.injectModules(this);

		TableUtils.setSpacingDefaults(this);

		setModal(true);
		addCloseButton();
		closeOnEscape();

		VisTable pathTable = new VisTable(true);
		pathTable.add(new VisLabel("Output folder"));
		pathTable.add(pathField = new VisValidatableTextField("F:\\Poligon\\Converter")).width(300); //TODO remove debug path
		pathTable.add(chooseButton = new VisTextButton("Choose..."));

		VisTable buttonTable = new VisTable(true);
		buttonTable.add(errorLabel = new VisLabel("")).growX();
		buttonTable.add(cancelButton = new VisTextButton("Cancel"));
		buttonTable.add(convertButton = new VisTextButton("Convert"));

		add(new VisLabel("This tool will help you convert your project to VisEditor 0.3.0 format.")).padBottom(8).row();
		add(pathTable).row();
		add(buttonTable).growX();

		FormValidator validator = new FormValidator(convertButton, errorLabel);
		validator.notEmpty(pathField, "Output folder cannot be empty!");
		validator.directory(pathField, "Selected folder is not a directory!");
		validator.directoryEmpty(pathField, "Selected directory is not empty!");

		convertButton.addListener(new VisChangeListener((changeEvent, actor) -> {
			ConvertTask task = new ConvertTask(projectMC, Gdx.files.absolute(pathField.getText()));
			getStage().addActor(new AsyncTaskProgressDialog("Converting", task).fadeIn());
			fadeOut();
		}));
		chooseButton.addListener(new VisChangeListener((changeEvent, actor) -> {
			fileChooser.pickDirectory(new FileChooserAdapter() {
				@Override
				public void selected (FileHandle file) {
					pathField.setText(file.path());
				}
			});
		}));
		cancelButton.addListener(new VisChangeListener((changeEvent, actor) -> fadeOut()));

		pack();
		centerWindow();
	}
}
