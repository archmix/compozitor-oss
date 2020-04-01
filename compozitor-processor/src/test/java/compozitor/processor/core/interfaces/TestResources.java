package compozitor.processor.core.interfaces;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.nio.file.Paths;

@RequiredArgsConstructor(staticName = "create", access = AccessLevel.PRIVATE)
public class TestResources {
  private final String testPath;

  public static TestResources create(Class<?> testClass){
    return create(uncapitalize(testClass.getSimpleName()));
  }

  private static String uncapitalize(final String value) {
    return new StringBuilder().append(Character.toLowerCase(value.charAt(0))).append(value.substring(1)).toString();
  }

  public final String testFile(String filename){
    return Paths.get(this.testPath, filename).toString();
  }
}
