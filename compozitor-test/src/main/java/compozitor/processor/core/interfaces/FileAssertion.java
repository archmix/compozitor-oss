package compozitor.processor.core.interfaces;

import com.google.common.io.CharStreams;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileAssertion {
  private final String content;

  public static FileAssertion withResourceFile(String path){
    String resourcePath = path;
    if(!path.startsWith("/")){
      resourcePath = new StringBuilder("/").append(path).toString();
    }

    try (InputStream resourceFile = FileAssertion.class.getResourceAsStream(resourcePath)) {
      String content = CharStreams.toString(new InputStreamReader(resourceFile));
      return new FileAssertion(content);
    } catch (IOException e){
      throw new RuntimeException("Resource file not found at " + path);
    }
  }

  public void assertEquals(JavaFileObject javaFile){
    Assert.assertEquals(this.content, FileObjectStringfy.toString(javaFile));
  }

  public void assertEquals(String content){
    Assert.assertEquals(this.content, content);
  }
}