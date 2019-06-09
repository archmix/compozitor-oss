package compozitor.generator.engine.interfaces;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratedSource {
	private InputStream content;

	private String fileName;

	private String path;

	private Boolean resource;
	
	private Boolean testArtifact;
}