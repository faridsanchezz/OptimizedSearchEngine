package control.interfaces;

import model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

public interface WordExtractorManager {
	Set<Word> getWords(BufferedReader source, String identifier) throws IOException;
}
