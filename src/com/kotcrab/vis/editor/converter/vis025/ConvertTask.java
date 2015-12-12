package com.kotcrab.vis.editor.converter.vis025;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.module.project.FileAccessModule;
import com.kotcrab.vis.editor.module.project.Project;
import com.kotcrab.vis.editor.module.project.ProjectLibGDX;
import com.kotcrab.vis.editor.module.project.ProjectModuleContainer;
import com.kotcrab.vis.editor.util.SteppedAsyncTask;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/** @author Kotcrab */
public class ConvertTask extends SteppedAsyncTask {
	private final ProjectModuleContainer projectMC;
	private final Project project;
	private final FileHandle outputFolder;

	private Array<FileHandle> sceneFiles;

	private FileAccessModule fileAccess;

	private File logFile;
	private PrintWriter logFileWriter;

	public ConvertTask (ProjectModuleContainer projectMC, FileHandle outputFolder) {
		super("ProjectConverterTask");
		this.projectMC = projectMC;
		this.outputFolder = outputFolder;
		projectMC.injectModules(this);

		this.project = projectMC.getProject();
	}

	@Override
	public void execute () throws Exception {
		setMessage("Initializing...");

		logFile = new File(outputFolder.file(), "conversion-log.txt");
		logFile.createNewFile();
		logFileWriter = new PrintWriter(new FileWriter(logFile, true));

		log("VisEditor ProjectConverter " + ProjectConverterPlugin.VERSION);
		log("Converting from 0.2.5/0.2.6 to 0.3.0");
		log("Project type: " + project.getClass().getSimpleName());
		log();

		sceneFiles = fileAccess.getSceneFiles();

		calculateSteps();

		if (project instanceof ProjectLibGDX) {
			String root = ((ProjectLibGDX) project).getRoot();
			FileHandle[] files = Gdx.files.absolute(root).list();
			for (FileHandle file : files) {
				String fileName = file.name();
				if (fileName.equals("vis")) continue;

				nextStep();
				setMessage("Copying existing resources... (" + fileName + ")");
				log("Copy " + file.path());
				file.copyTo(outputFolder.child(fileName));
			}
		}

		log();

		for (FileHandle scene : sceneFiles) {
			nextStep();

			String fileName = scene.name();

			setMessage("Converting scene " + fileName + "...");
			log("Convert scene: " + fileName);

		}

		log();

		log("Update project files");
		nextStep();
		setMessage("Updating project files...");

		log();
		log("===================");
		log("Conversion finished");
		log("===================");

		logFileWriter.close();
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

	private void log (String msg) {
		logFileWriter.println(msg);
	}

	private void log () {
		logFileWriter.println();
	}
}
