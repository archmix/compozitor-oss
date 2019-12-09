package compozitor.engine.processor.infra;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compilation.Status;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.FileAssertion;
import compozitor.processor.core.interfaces.FileObjectStringfy;
import org.junit.Assert;
import org.junit.Test;

public class PluginsServiceProcessorTest {
  @Test
  public void givenAnnotatedPluginWhenProcessThenGenerateServiceFile(){
    Compilation compilation = CompilationBuilder.create().withJavaSource("pluginsServiceProcessorTest/PluginsServiceRegistration.java")
        .withProcessors(processor()).build();

    FileObjectStringfy files = FileObjectStringfy.create(compilation);

    compilation.generatedFiles().forEach(file -> {
      if(file.getName().contains("TypeModelPlugin")){
        FileAssertion.withResourceFile("/pluginsServiceProcessorTest/compozitor.engine.core.interfaces.TypeModelPlugin").assertEquals(file);
      }
    });

    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }

  private PluginsServiceProcessor processor(){
    return new PluginsServiceProcessor();
  }
}
