package compozitor.processor.core.interfaces;

import com.google.common.collect.ImmutableList;
import com.google.testing.compile.Compilation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor(staticName = "create", access = AccessLevel.PACKAGE)
public class CompileAssertion {
  private final Compilation compilation;

  public CompileAssertion assertSuccess(){
    Assert.assertEquals(Compilation.Status.SUCCESS, this.compilation.status());
    return this;
  }

  public CompileAssertion assertFailure(){
    Assert.assertEquals(Compilation.Status.FAILURE, this.compilation.status());
    return this;
  }

  public FailureAssertion failureAssertion(String message) {
    return new FailureAssertion();
  }

  public CompileAssertion assertGeneratedFiles(Integer expected){
    Assert.assertEquals(expected, (Integer) this.compilation.generatedFiles().size());
    return this;
  }

  public CompileAssertion assertGeneratedSourceFile(String filename){
    try {
      new GeneratedSourceAssertion(filename);
    } catch (Exception e) {
      Assert.fail(message("Source file {0} was not generated.", filename));
    }
    return this;
  }

  public CompileAssertion assertGeneratedResourceFile(String filename){
    try {
      new GeneratedResourceAssertion(filename);
    } catch (Exception e) {
      Assert.fail(message("Resource file {0} was not generated.", filename));
    }
    return this;
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

  public class FailureAssertion {
    private final ImmutableList<Diagnostic<? extends JavaFileObject>> errors;

    FailureAssertion(){
      this.errors = compilation.errors();
    }

    public FailureAssertion assertErrors(int numberOfErrors){
      Assert.assertEquals(numberOfErrors, this.errors.size());
      return this;
    }

    public FailureAssertion assertMessage(String message){
      Optional<String> foundMessage = this.errors.stream().map(diagnostic -> diagnostic.getMessage(Locale.getDefault()))
        .filter(errorMessage -> errorMessage.equals(message)).findFirst();

      Assert.assertTrue(foundMessage.isPresent());
      return this;
    }
  }

  public class GeneratedResourceAssertion {
    private final String content;

    public GeneratedResourceAssertion(String resourceFile) {
      this.content = FileObjectStringfy.create(compilation).resourceToString(resourceFile);
    }

    public GeneratedResourceAssertion assertEquals(String resourceFile){
      FileAssertion.withResourceFile(resourceFile).assertEquals(this.content);
      return this;
    }
  }

  public class GeneratedSourceAssertion {
    private final String sourceFile;
    private final String content;

    public GeneratedSourceAssertion(String sourceFile) {
      this.sourceFile = sourceFile;
      this.content = FileObjectStringfy.create(compilation).sourceToString(sourceFile);
    }

    public GeneratedSourceAssertion assertEquals(){
      this.assertEquals(this.sourceFile);
      return this;
    }

    public GeneratedSourceAssertion assertEquals(String sourceFile){
      FileAssertion.withResourceFile(sourceFile).assertEquals(this.content);
      return this;
    }
  }

  public class ServiceFileAssertion {
    private final String content;

    ServiceFileAssertion(Class<?> targetService) {
      this.content = FileObjectStringfy.create(compilation).serviceFile(targetService);
    }

    public ServiceFileAssertion assertEmpty(){
      this.assertEquals("");
      return this;
    }

    public ServiceFileAssertion assertEquals(String serviceName) {
      Assert.assertTrue(this.content.equals(serviceName));
      return this;
    }

    public ServiceFileAssertion assertContains(String serviceName){
      Assert.assertTrue(this.content.contains(serviceName));
      return this;
    }
  }

  private String message(String format, Object... args){
    return MessageFormat.format(format, args);
  }
}
