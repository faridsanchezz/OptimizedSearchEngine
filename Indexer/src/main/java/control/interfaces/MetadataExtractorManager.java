package control.interfaces;

import model.Metadata;

import java.io.BufferedReader;
import java.io.IOException;


public interface MetadataExtractorManager {
	Metadata getMetadata(BufferedReader source, String identifier) throws IOException;
}
