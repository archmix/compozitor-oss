package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.nio.file.Paths;

@RequiredArgsConstructor(staticName = "create", access = AccessLevel.PRIVATE)
public class TestResources {
  private final String testPath;

  public static TestResources create(Class<?> testClass) {
    return create(uncapitalize(testClass.getSimpleName()));
  }

  private static String uncapitalize(final String value) {
    return new StringBuilder().append(Character.toLowerCase(value.charAt(0))).append(value.substring(1)).toString();
  }

  public final String packageClass(String className) {
    return this.testFile(className).replace("/", ".");
  }

  public final String testFile(String filename) {
    return new StringBuilder(this.testPath).append("/").append(filename).toString();
  }
}
