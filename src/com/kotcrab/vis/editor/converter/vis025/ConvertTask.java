package com.kotcrab.vis.editor.converter.vis025;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.module.project.*;
import com.kotcrab.vis.editor.module.scene.SceneModuleContainer;
import com.kotcrab.vis.editor.scene.EditorScene;
import com.kotcrab.vis.editor.util.SteppedAsyncTask;
import com.kotcrab.vis.runtime.util.EntityEngine;
import com.kotcrab.vis.runtime.util.EntityEngineConfiguration;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/** @author Kotcrab */
public class ConvertTask extends SteppedAsyncTask {
	private final ProjectModuleContainer projectMC;
	private final Project project;
	private final FileHandle outputFolder;

	private Array<FileHandle> sceneFiles;

	private SceneCacheModule sceneCache;
	private FileAccessModule fileAccess;
	private Stage stage;

	private File logFile;
	private File errorLogFile;
	private PrintWriter logFileWriter;
	private PrintWriter errorLogFileWriter;

	private boolean errorOccured = false;

	public ConvertTask (ProjectModuleContainer projectMC, FileHandle outputFolder) {
		super("ProjectConverterTask");
		this.projectMC = projectMC;
		this.outputFolder = outputFolder;
		projectMC.injectModules(this);

		this.project = projectMC.getProject();
	}

	@Override
	public void execute () throws Exception {
		try {
			setMessage("Initializing...");

			logFile = new File(outputFolder.file(), "conversion-log.txt");
			logFile.createNewFile();
			logFileWriter = new PrintWriter(new FileWriter(logFile, true));
			errorLogFile = new File(outputFolder.file(), "conversion-error-log.txt");
			errorLogFile.createNewFile();
			errorLogFileWriter = new PrintWriter(new FileWriter(errorLogFile, true));

			log("VisEditor ProjectConverter " + ProjectConverterPlugin.VERSION);
			log("Converting from 0.2.5/0.2.6 to 0.3.0");
			log("Project type: " + project.getClass().getSimpleName());
			log();

			sceneFiles = fileAccess.getSceneFiles();

			calculateSteps();

//		if (project instanceof ProjectLibGDX) {
//			String root = ((ProjectLibGDX) project).getRoot();
//			FileHandle[] files = Gdx.files.absolute(root).list();
//			for (FileHandle file : files) {
//				String fileName = file.name();
//				if (fileName.equals("vis")) continue;
//
//				nextStep();
//				setMessage("Copying existing resources... (" + fileName + ")");
//				log("Copy " + file.path());
//				file.copyTo(outputFolder.child(fileName));
//			}
//		}

			log();

			for (FileHandle scene : sceneFiles) {
				nextStep();

				String fileName = scene.name();

				setMessage("Converting scene " + fileName + "...");
				log("Convert scene: " + fileName);
				convertScene(scene);
				log();
			}

			log();

			log("Update project files");

			FileHandle outVisFolder;

			if (project instanceof ProjectLibGDX)
				outVisFolder = outputFolder.child("vis");
			else //project generic
				outVisFolder = outputFolder;

			outVisFolder.child("modules/supportDescriptor.json").delete();
			outVisFolder.child("modules/version.json").delete(); //TODO recreate version file
			outVisFolder.child("modules/.sceneBackup").deleteDirectory();

			nextStep();
			setMessage("Updating project files...");

			log();
			log("===================");
			log("Conversion finished");
			log("===================");

		} finally {
			logFileWriter.close();
			errorLogFileWriter.close();

			if (errorOccured) {
				DialogUtils.showOKDialog(stage, "Warning", "There were some problems during converting, please check log.\n" + errorLogFile.getAbsolutePath());
			}
		}
	}

	private void convertScene (FileHandle sceneFile) {
		//all scene will be closed at this point
		executeOnOpenGL(() -> {
			EditorScene scene = sceneCache.get(sceneFile);

			EntityEngineConfiguration config = new EntityEngineConfiguration();
			SceneTransformingSystem sceneTransforming = new SceneTransformingSystem(this);
			config.setSystem(sceneTransforming);
			EntityEngine engine = new EntityEngine(config);
			SceneModuleContainer.populateEngine(engine, scene);
			engine.process();
			sceneTransforming.process();
		});
	}

	private void calculateSteps () {
		int steps = 0;

		if (project instanceof ProjectLibGDX) {
			String root = ((ProjectLibGDX) project).getRoot();
			steps = Gdx.files.absolute(root).list().length - 1; //-1 because it should not count vis folder
		}

		steps += sceneFiles.size;

		steps++; //update project config files

		setTotalSteps(steps);
	}

	void log (String msg) {
		logFileWriter.println(msg);
	}

	void log () {
		logFileWriter.println();
	}

	void logError (String msg) {
		errorOccured = true;
		logFileWriter.println("**ERROR**: " + msg);
		errorLogFileWriter.println(msg);
	}

}
