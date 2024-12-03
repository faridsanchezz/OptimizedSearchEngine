package control;

import static control.ApiServer.*;
import static spark.Spark.port;

public class Main {
	public static void main(String[] args) {

		port(8080);
		enableCORS("*", "GET,POST,OPTIONS", "Content-Type,Authorization");

		// Paths for Docker
		String WORDS_DATAMART_PATH_FilePerWord = "/app/datamart/words/";
		String DATALAKE_PATH_FilePerWord = "/app/datalake/";
		String METADATA_FILE_PATH_FilePerWord = "/app/datamart/metadata/metadata";

		QueryEngineFilePerWord queryEngineFilePerWord = new QueryEngineFilePerWord(METADATA_FILE_PATH_FilePerWord);
		BookController bookController = new BookController(WORDS_DATAMART_PATH_FilePerWord, DATALAKE_PATH_FilePerWord, METADATA_FILE_PATH_FilePerWord, queryEngineFilePerWord);

		configureRoutes(bookController);
	}
}
