package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryManager {

	public static String createDatamartDirectory(String directory) {
		File datamartDirectory = new File(directory, "datamart");
		if (!datamartDirectory.exists()) {
			datamartDirectory.mkdirs();
			System.out.println("Directory created: " + datamartDirectory);
		}
		return datamartDirectory.toString();
	}

	public static String createDatalakeDirectory(String directory) {
		File datalakeDirectory = new File(directory, "datalake");
		if (!datalakeDirectory.exists()) {
			datalakeDirectory.mkdirs();
			System.out.println("Directory created: " + datalakeDirectory);
		}
		return datalakeDirectory.toString();
	}
}

