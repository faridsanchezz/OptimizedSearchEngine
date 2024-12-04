package control;

import control.interfaces.*;
import control.metadata.MetadataExtractor;
import control.metadata.MetadataSerializer;
import control.metadata.MetadataStoreOneFile;
import control.metadata.MetadataStoreSqlite;
import control.word.WordCleaner;
import control.word.WordExtractor;
import control.word.WordSerializerFilePerWord;
import control.word.WordStoreMultipleDirectories;

import java.io.File;
import java.io.IOException;


public class Main {
	public static void main(String[] args) throws IOException {

		// Paths for Docker
		String datamartDirectory = DirectoryManager.createDatamartDirectory("");
		String datalakeDirectory = DirectoryManager.createDatalakeDirectory("");

		WordCleanerManager wordCleaner = new WordCleaner();

		MetadataExtractorManager metadataExtractor = new MetadataExtractor();
		// MetadataSerializerManager metadataSerializer = new MetadataSerializer();
		// MetadataStoreManager metadataStoreFilePerWord = new MetadataStoreOneFile(datamartDirectory, metadataSerializer);
		MetadataStoreManager metadataStoreSqlite = new MetadataStoreSqlite(datamartDirectory);

		WordExtractorManager wordExtractor = new WordExtractor(wordCleaner);
		WordSerializerManager wordSerializerFilePerWord = new WordSerializerFilePerWord();
		// WordStoreManager wordStoreOneDirectory = new WordStoreOneDirectory(datamartDirectory, wordSerializerFilePerWord);
		WordStoreManager wordStoreMultipleDirectories = new WordStoreMultipleDirectories(datamartDirectory, wordSerializerFilePerWord);

		Indexer indexerFilePerWord = new Indexer(wordStoreMultipleDirectories, metadataStoreSqlite, metadataExtractor, wordExtractor);
		EventsWatcher eventsWatcher = new EventsWatcher(datalakeDirectory, indexerFilePerWord);
		eventsWatcher.run();

	}
}
