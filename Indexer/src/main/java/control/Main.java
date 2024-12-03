package control;

import control.interfaces.*;

import java.io.File;
import java.io.IOException;


public class Main {
	public static void main(String[] args) throws IOException {

		// Paths for Docker
		String datamartDirectory = "";
		String datalakeDirectory = "";

		DirectoryManager.createDirectory(new File(datamartDirectory));
		DirectoryManager.createDirectory(new File(datalakeDirectory));

		WordCleanerManager wordCleaner = new WordCleaner();

		MetadataExtractorManager metadataExtractor = new MetadataExtractor();
		MetadataSerializerManager metadataSerializer = new MetadataSerializer();
		MetadataStoreManager metadataStoreFilePerWord = new MetadataStoreFilePerWord(datamartDirectory, metadataSerializer);

		WordExtractorManager wordExtractor = new WordExtractor(wordCleaner);
		WordSerializerManager wordSerializerFilePerWord = new WordSerializerFilePerWord();
		// WordStoreManager wordStoreFilePerWord = new WordStoreFilePerWord(datamartDirectory, wordSerializerFilePerWord);
		WordStoreManager wordStoreMultipleDirectories = new WordStoreMultipleDirectories(datamartDirectory, wordSerializerFilePerWord);

		Indexer indexerFilePerWord = new Indexer(wordStoreMultipleDirectories, metadataStoreFilePerWord, metadataExtractor, wordExtractor);
		EventsWatcher eventsWatcher = new EventsWatcher(datalakeDirectory, indexerFilePerWord);
		eventsWatcher.run();

	}
}
