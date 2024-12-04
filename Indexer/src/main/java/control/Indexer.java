package control;


import control.interfaces.MetadataExtractorManager;
import control.interfaces.MetadataStoreManager;
import control.interfaces.WordExtractorManager;
import control.interfaces.WordStoreManager;
import model.Metadata;
import model.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;


public class Indexer {

	private final WordStoreManager wordStoreManager;
	private final MetadataStoreManager metadataStoreManager;
	private final MetadataExtractorManager metadataExtractor;
	private final WordExtractorManager wordExtractor;

	public Indexer(WordStoreManager wordStoreManager, MetadataStoreManager metadataStoreManager, MetadataExtractorManager metadataExtractor, WordExtractorManager wordExtractor) {
		this.wordStoreManager = wordStoreManager;
		this.metadataStoreManager = metadataStoreManager;
		this.metadataExtractor = metadataExtractor;
		this.wordExtractor = wordExtractor;
	}


	public void execute(Path bookDatalakePath) throws IOException {
		Metadata metadata;
		Set<Word> wordSet;

		try (BufferedReader book = new BufferedReader(new FileReader(bookDatalakePath.toFile()))) {
			book.mark(5 * 1024 * 1024);
			metadata = metadataExtractor.getMetadata(book, bookDatalakePath.getFileName().toString());
			metadataStoreManager.update(metadata);
			book.reset();
			wordSet = wordExtractor.getWords(book, metadata.getBookID());
			wordStoreManager.update(wordSet);

			System.out.println("------------------------- Finish indexing book ------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}




