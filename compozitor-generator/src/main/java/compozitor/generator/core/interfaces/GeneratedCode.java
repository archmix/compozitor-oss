package compozitor.generator.core.interfaces;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratedCode {
	private InputStream content;

	private String fileName;

	private String path;
	
	private String namespace;

	private String qualifiedName;
	
	private String simpleName;
	
	private Boolean resource;
	
	private Boolean testArtifact;
}