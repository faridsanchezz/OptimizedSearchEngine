package control;


import control.interfaces.WordSerializerManager;
import control.interfaces.WordStoreManager;
import model.Word;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;


public class WordStoreFilePerWord implements WordStoreManager {
	private final WordSerializerManager wordSerializer;
	private File wordsDatamartDirectory;
	private final Set<String> lockedDocuments = ConcurrentHashMap.newKeySet();

	public WordStoreFilePerWord(String generaldatamartDirectory, WordSerializerManager wordSerializer) throws IOException {
		this.wordSerializer = wordSerializer;
		createWordDatamartDirectory(generaldatamartDirectory);
	}

	private void createWordDatamartDirectory(String generalDatamartDirectory){
		this.wordsDatamartDirectory = new File(generalDatamartDirectory, "words");
		if (!this.wordsDatamartDirectory.exists()) {this.wordsDatamartDirectory.mkdirs();}

	}

	@Override
	public void update(Set<Word> newWordSet) {

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		for (Word newWord : newWordSet) {
			forkJoinPool.submit(() -> {
				File datamartFilePath = new File(this.wordsDatamartDirectory, newWord.getText());
				String filePath = datamartFilePath.getAbsolutePath();

				if (!lockedDocuments.add(filePath)) {
					return;
				}

				try {

					wordSerializer.serialize(datamartFilePath, newWord.getOccurrences());

				} finally {
					lockedDocuments.remove(filePath);
				}
			});
		}
		forkJoinPool.shutdown();


	}
}
