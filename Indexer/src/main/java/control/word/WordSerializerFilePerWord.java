package control.word;


import control.interfaces.WordSerializerManager;
import model.Word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class WordSerializerFilePerWord implements WordSerializerManager {

	@Override
	public void serialize(File datamartFile, Set<Word.WordOccurrence> occurrences) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(datamartFile, true))) {

			for (Word.WordOccurrence ocurrence : occurrences) {
				writer.write(ocurrence.getBookID() + " ");
				writer.write(String.join(" ", ocurrence.getLineOccurrences().stream()
						.map(String::valueOf)
						.toArray(String[]::new)));
				writer.newLine();
			}

		} catch (IOException e) {
			throw new RuntimeException("Error serializing occurrences", e);
		}
	}
}
