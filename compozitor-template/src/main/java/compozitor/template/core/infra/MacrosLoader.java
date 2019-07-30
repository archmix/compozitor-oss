package compozitor.template.core.infra;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MacrosLoader {
	private JoinableClassLoader classLoader;

	MacrosLoader() {
		this.classLoader = new JoinableClassLoader().join(this.getClass().getClassLoader())
				.join(Thread.currentThread().getContextClassLoader());
	}
	
	public static MacrosLoader create() {
		return new MacrosLoader();
	}

	public Stream<String> list(Path path) {
		File directory = path.toFile();
		if(directory.exists()) {
			return listFromDirectory(path).map(it -> it.getFileName().toString());
		}
		
		List<String> resources = new ArrayList<String>();
		
		try (InputStream classPath = this.classLoader.getResourceAsStream(path.toString());
				BufferedReader reader = new BufferedReader(new InputStreamReader(classPath))) {

			String resource = null;
			while ((resource = reader.readLine()) != null) {
				resources.add(new File(path.toString(), resource).toString());
			}
			
			return resources.stream();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Stream<Path> listFromDirectory(Path path) {
		try {
			return Files.list(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
