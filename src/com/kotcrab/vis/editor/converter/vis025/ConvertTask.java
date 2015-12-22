package com.kotcrab.vis.editor.converter.vis025;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kotcrab.vis.editor.VersionCodes;
import com.kotcrab.vis.editor.converter.support.vis030.editor.scene.EntityScheme;
import com.kotcrab.vis.editor.converter.vis025.module.SupportGsonModule;
import com.kotcrab.vis.editor.module.editor.ExtensionStorageModule;
import com.kotcrab.vis.editor.module.project.*;
import com.kotcrab.vis.editor.module.scene.SceneModuleContainer;
import com.kotcrab.vis.editor.scene.EditorScene;
import com.kotcrab.vis.editor.util.FileUtils;
import com.kotcrab.vis.editor.util.SteppedAsyncTask;
import com.kotcrab.vis.runtime.util.EntityEngine;
import com.kotcrab.vis.runtime.util.EntityEngineConfiguration;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/** @author Kotcrab */
public class ConvertTask extends SteppedAsyncTask {
	private final ProjectModuleContainer projectMC;
	private final Project project;
	private final FileHandle outputFolder;

	private Array<FileHandle> sceneFiles;

	private SceneCacheModule sceneCache;
	private FileAccessModule fileAccess;
	private ExtensionStorageModule extensionStorage;
	private Stage stage;

	private SupportGsonModule gsonModule;

	private File logFile;
	private File errorLogFile;
	private PrintWriter logFileWriter;
	private PrintWriter errorLogFileWriter;

	private boolean errorOccurred = false;

	private ClassLoader mainClassLoader;

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

					nextStep();
					setMessage("Copying existing resources... (" + fileName + ")");
					log("Copy " + file.path());
					file.copyTo(outputFolder.child(fileName));
				}
			} else {
				for (FileHandle file : project.getVisDirectory().list()) {
					String fileName = file.name();

					nextStep();
					setMessage("Copying existing resources... (" + fileName + ")");
					log("Copy " + file.path());
					file.copyTo(outputFolder.child(fileName));
				}
			}

			FileHandle outVisFolder;

			if (project instanceof ProjectLibGDX)
				outVisFolder = outputFolder.child("vis");
			else //project generic
				outVisFolder = outputFolder;

			log();
			log("Delete assets/scenes/*.scene (will be reconverted)");
			FileUtils.streamRecursively(outVisFolder.child("assets/scene"), file -> {
				if (file.extension().equals("scene")) {
					log("Delete " + file.path());
					file.delete();
				}
				return false;
			});
			log();

			for (FileHandle scene : sceneFiles) {
				nextStep();

				String fileName = scene.name();

				setMessage("Converting scene " + fileName + "...");
				log("Convert scene: " + fileName);
				convertScene(outVisFolder, scene);
				log();
			}

			log();

			log("Update project files");
			nextStep();
			setMessage("Updating project files...");

			log("Deleting modules/supportDescriptor.json");
			outVisFolder.child("modules/supportDescriptor.json").delete();

			log("Deleting modules/version.json");
			outVisFolder.child("modules/version.json").delete();
			log("Create modules/version.json for VisEditor 0.3.0");
			ProjectVersionModule.getNewJson().toJson(new ProjectVersionDescriptor(VersionCodes.EDITOR_030, "0.3.0"), outVisFolder.child("modules/version.json"));

			log("Deleting project.data");
			outVisFolder.child("project.data").delete();
			log("Create project.json for VisEditor 0.3.0");
			FileWriter writer = new FileWriter(outVisFolder.child("project.json").file());
			gsonModule.getCommonGson().toJson(project, writer);
			writer.close();

			log("Deleting modules/.sceneBackup");
			outVisFolder.child("modules/.sceneBackup").deleteDirectory();

			log("Create modules/assetsMetadata.json");
			createMetadata(outVisFolder.child("modules/assetsMetadata.json"));

			log();
			log("===================");
			log("Conversion finished");
			log("===================");

		} finally {
			logFileWriter.close();

			if (errorOccurred) {
				errorLogFileWriter.close();
				DialogUtils.showOKDialog(stage, "Warning", "There were some problems during converting, please check log.\n" + errorLogFile.getAbsolutePath());
			}
		}
	}

	private void createMetadata (FileHandle file) throws IOException {
		ObjectMap<String, String> metadata = new ObjectMap<>();

		FileUtils.streamDirectoriesRecursively(fileAccess.getAssetsFolder(), dir -> {
			String relativePath = fileAccess.relativizeToAssetsFolder(dir);

			if (relativePath.startsWith("music")) {
				log("\tSave " + relativePath + " as music");
				metadata.put(relativePath + "/", "com.kotcrab.vis.editor.directory.Music");
			}

			if (relativePath.startsWith("sound")) {
				log("\tSave " + relativePath + " as sound");
				metadata.put(relativePath + "/", "com.kotcrab.vis.editor.directory.Sound");
			}

			if (relativePath.startsWith("spine") && dir.parent().equals(fileAccess.getAssetsFolder())) {
				log("\tSave " + relativePath + " as Spine");
				metadata.put(relativePath + "/", "com.kotcrab.vis.editor.plugin.spine.directory.Spine");
			}

			if (relativePath.startsWith("spriter") && dir.parent().equals(fileAccess.getAssetsFolder()) == false) {
				log("\tSave " + relativePath + " as Spriter");
				metadata.put(relativePath + "/", "com.kotcrab.vis.editor.directory.Spriter");

			}
		});

		FileWriter writer = new FileWriter(file.file());
		gsonModule.getCommonGson().toJson(metadata, writer);
		writer.close();
	}

	private void convertScene (FileHandle outVisFolder, FileHandle sceneFile) {
		//all scene will be closed at this point
		executeOnOpenGL(() -> {
			EditorScene scene = sceneCache.get(sceneFile);

			EntityEngineConfiguration config = new EntityEngineConfiguration();
			SceneTransformingSystem sceneTransforming = new SceneTransformingSystem(this, extensionStorage);
			config.setSystem(sceneTransforming);
			EntityEngine engine = new EntityEngine(config);
			SceneModuleContainer.populateEngine(engine, scene);
			engine.process();
			Array<EntityScheme> results = sceneTransforming.convertScene();

			com.kotcrab.vis.editor.converter.support.vis030.editor.scene.EditorScene convertedScene =
					new com.kotcrab.vis.editor.converter.support.vis030.editor.scene.EditorScene(scene, results);

			try {
				FileHandle file = outVisFolder.child(fileAccess.relativizeToVisFolder(sceneFile));
				file.parent().mkdirs();
				log("Save converted scene " + file.path());

				FileWriter writer = new FileWriter(file.file());
				gsonModule.getCommonGson().toJson(convertedScene, writer);
				writer.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		});
	}

	private void calculateSteps () {
		int steps = 0;

		if (project instanceof ProjectLibGDX) {
			String root = ((ProjectLibGDX) project).getRoot();
			steps = Gdx.files.absolute(root).list().length - 1; //-1 because it should not count vis folder
		} else {
			steps = project.getAssetOutputDirectory().list().length;
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

	public void logError (String msg) {
		try {
			if (errorLogFile == null) {
				errorLogFile = new File(outputFolder.file(), "conversion-error-log.txt");
				errorLogFile.createNewFile();
				errorLogFileWriter = new PrintWriter(new FileWriter(errorLogFile, true));
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		errorOccurred = true;
		logFileWriter.println("**ERROR**: " + msg);
		errorLogFileWriter.println(msg);
	}

}
