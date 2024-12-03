package control.interfaces;

import model.Metadata;

import java.io.File;
import java.util.Set;

public interface MetadataSerializerManager {

	void serialize(File filePath, Metadata metadata);

}
