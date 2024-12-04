package control.word;


import control.interfaces.WordSerializerManager;
import control.interfaces.WordStoreManager;
import model.Word;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;


public class WordStoreOneDirectory implements WordStoreManager {
	private final WordSerializerManager wordSerializer;
	private final Set<String> lockedDocuments = ConcurrentHashMap.newKeySet();
	private File wordsDatamartDirectory;

	public WordStoreOneDirectory(String generaldatamartDirectory, WordSerializerManager wordSerializer) throws IOException {
		this.wordSerializer = wordSerializer;
		createWordDatamartDirectory(generaldatamartDirectory);
	}

	private void createWordDatamartDirectory(String generalDatamartDirectory) {
		this.wordsDatamartDirectory = new File(generalDatamartDirectory, "words");
		if (!this.wordsDatamartDirectory.exists()) {
			this.wordsDatamartDirectory.mkdirs();
		}

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
