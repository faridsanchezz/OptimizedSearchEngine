package control;

import control.interfaces.MetadataSerializerManager;
import model.Metadata;

import java.io.*;


public class MetadataSerializer implements MetadataSerializerManager {

	@Override
	public void serialize(File metadataDatamartFile, Metadata metadata) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(metadataDatamartFile, true))) {
			for(String data: metadata.toList()){
				writer.write(data);
				writer.newLine();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
