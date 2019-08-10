package compozitor.template.core.infra;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
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
    if (directory.exists()) {
      return listFromDirectory(path).map(it -> it.getFileName().toString());
    }

    Set<String> resources = new HashSet<>();

    try {
      String resourceName = path.toString();
      if (resourceName.startsWith("/")) {
        resourceName = resourceName.replaceFirst("/", "");
      }

      Enumeration<URL> urls = this.classLoader.getResources(resourceName);
      while (urls.hasMoreElements()) {
        try (InputStream classPath = urls.nextElement().openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(classPath))) {

          String resource = null;
          while ((resource = reader.readLine()) != null) {
            resources.add(new File(path.toString(), resource).toString());
          }
        }
      }
      return resources.stream();
    } catch (IOException e) {
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
