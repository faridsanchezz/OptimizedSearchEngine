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
	private final File datamartDirectory;
	private final Set<String> lockedDocuments = ConcurrentHashMap.newKeySet(); //TODO: ¿para que sirve esta variable?

	public WordStoreFilePerWord(String datamartDirectory, WordSerializerManager wordSerializer) throws IOException {
		this.wordSerializer = wordSerializer;
		this.datamartDirectory = Paths.get(datamartDirectory, "words").toFile();
		DirectoryManager.createDirectory(this.datamartDirectory);
	}

	@Override
	public void update(Set<Word> newWordSet) {

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		for (Word newWord : newWordSet) {
			forkJoinPool.submit(() -> {
				File datamartFilePath = new File(this.datamartDirectory, newWord.getText());
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
