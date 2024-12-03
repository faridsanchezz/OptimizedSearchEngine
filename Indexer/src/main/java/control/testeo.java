package control;

import java.io.File;
import java.nio.file.Paths;

public class testeo {
	public static void main(String[] args) {
		File datamartFilePath = Paths.get("main", "submain", "home", "a", "word").toFile();
		System.out.println(datamartFilePath);
	}
}
