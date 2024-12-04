package control.word;


import control.interfaces.WordSerializerManager;
import control.interfaces.WordStoreManager;
import model.Word;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;


public class WordStoreMultipleDirectories implements WordStoreManager {
	private final WordSerializerManager wordSerializer;
	private final Set<String> lockedDocuments = ConcurrentHashMap.newKeySet();
	private File wordsDatamartDirectory;

	public WordStoreMultipleDirectories(String generaldatamartDirectory, WordSerializerManager wordSerializer) throws IOException {
		this.wordSerializer = wordSerializer;
		createWordDatamartDirectory(generaldatamartDirectory);
	}

	private void createWordDatamartDirectory(String generalDatamartDirectory) {
		this.wordsDatamartDirectory = new File(generalDatamartDirectory, "words");
		if (!this.wordsDatamartDirectory.exists()) {
			this.wordsDatamartDirectory.mkdirs();
		}

		for (char letter = 'a'; letter <= 'z'; letter++) {
			File subdirectory = new File(this.wordsDatamartDirectory, String.valueOf(letter));
			if (!subdirectory.exists()) {
				subdirectory.mkdirs();
			}
		}
	}

	@Override
	public void update(Set<Word> newWordSet) {

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		for (Word newWord : newWordSet) {
			forkJoinPool.submit(() -> {
				String inicialLetter = String.valueOf(newWord.getText().charAt(0));
				Path datamartFilePath = Paths.get(
						this.wordsDatamartDirectory.toString(),
						inicialLetter,
						newWord.getText());

				if (!lockedDocuments.add(datamartFilePath.toAbsolutePath().toString())) {
					return;
				}

				try {

					wordSerializer.serialize(datamartFilePath.toFile(), newWord.getOccurrences());

				} finally {
					lockedDocuments.remove(datamartFilePath.toAbsolutePath().toString());
				}
			});
		}
		forkJoinPool.shutdown();


	}
}

