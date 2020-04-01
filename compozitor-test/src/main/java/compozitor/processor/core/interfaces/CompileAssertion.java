package compozitor.processor.core.interfaces;

import com.google.testing.compile.Compilation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;

@RequiredArgsConstructor(staticName = "create", access = AccessLevel.PACKAGE)
public class CompileAssertion {
  private final Compilation compilation;

  public void assertSuccess(){
    Assert.assertEquals(Compilation.Status.SUCCESS, this.compilation.status());
  }

  public void assertFailure(){
    Assert.assertEquals(Compilation.Status.FAILURE, this.compilation.status());
  }

  public void assertGeneratedFiles(Integer expected){
    Assert.assertEquals(expected, (Integer) this.compilation.generatedFiles().size());
  }

  public ServiceFileAssertion serviceFileAssertion(Class<?> targetService) {
    return new ServiceFileAssertion(targetService);
  }

  public GeneratedSourceAssertion generatedSourceAssertion(String filename){
    return new GeneratedSourceAssertion(filename);
  }

  public GeneratedResourceAssertion generatedResourceAssertion(String filename){
    return new GeneratedResourceAssertion(filename);
  }

  public class GeneratedResourceAssertion {
    private final String content;

    public GeneratedResourceAssertion(String resourceFile) {
      this.content = FileObjectStringfy.create(compilation).resourceToString(resourceFile);
    }

    public void assertEquals(String resourceFile){
      FileAssertion.withResourceFile(resourceFile).assertEquals(this.content);
    }
  }

  public class GeneratedSourceAssertion {
    private final String content;

    public GeneratedSourceAssertion(String sourceFile) {
      this.content = FileObjectStringfy.create(compilation).sourceToString(sourceFile);
    }

    public void assertEquals(String sourceFile){
      FileAssertion.withResourceFile(sourceFile).assertEquals(this.content);
    }
  }

  public class ServiceFileAssertion {
    private final String content;

    ServiceFileAssertion(Class<?> targetService) {
      this.content = FileObjectStringfy.create(compilation).serviceFile(targetService);
    }

    public void assertEmpty(){
      this.assertEquals("");
    }

    public void assertEquals(String serviceName) { Assert.assertTrue(this.content.equals(serviceName));}

    public void assertContains(String serviceName){
      Assert.assertTrue(this.content.contains(serviceName));
    }
  }
}
