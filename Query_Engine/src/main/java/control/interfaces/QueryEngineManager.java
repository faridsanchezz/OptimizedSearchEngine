package control.interfaces;

import model.Word;

import java.util.List;
import java.util.Map;

public interface QueryEngineManager {

	public Map<String, Object> printResultsAsMap(String wordsDatamartPath, String datalakePath, String metadataFilePath, String word);

}



