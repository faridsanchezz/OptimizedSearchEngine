package control;

import control.interfaces.MetadataSerializerManager;
import control.interfaces.MetadataStoreManager;
import model.Metadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MetadataStoreFilePerWord implements MetadataStoreManager {
	private final File datamartFilePath;
	private final MetadataSerializerManager metadataSerializer;

	public MetadataStoreFilePerWord(String datamartDirectory, MetadataSerializerManager metadataSerializer) throws IOException {
		this.metadataSerializer = metadataSerializer;
		DirectoryManager.createDirectory(Paths.get(datamartDirectory, "metadata").toFile());
		this.datamartFilePath = Paths.get(datamartDirectory, "metadata", "metadata").toFile();
	}

	@Override
	public void update(Metadata new_metadata) throws IOException {

		metadataSerializer.serialize(this.datamartFilePath, new_metadata);
	}
}

