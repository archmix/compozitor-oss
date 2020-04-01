package serviceProcessorTest;

import com.google.common.collect.Sets;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compilation.Status;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.FileObjectStringfy;
import compozitor.processor.core.interfaces.ServiceProcessor;
import compozitor.processor.core.interfaces.TestResources;
import methodProcessorTest.MethodProcessorTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

public class ServiceProcessorTest {
  @Test
  public void givenServiceInterfaceWhenCompileThenGenerateServiceFile(){
    TestResources resources = TestResources.create(this.getClass());

    Compilation compilation =
      CompilationBuilder.create()
        .withProcessors(new ComponentProcessor())
        .withJavaSource(
          resources.testFile("ComponentServiceTest.java")
        ).build();

    String serviceFile = FileObjectStringfy.create(compilation).serviceFile(ComponentServiceInterface.class);
    Assert.assertTrue(serviceFile.contains("serviceProcessorTest.ComponentServiceTest"));

    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }

  public class ComponentProcessor extends ServiceProcessor {

    @Override
    protected Iterable<Class<?>> serviceClasses() {
      return Arrays.asList(ComponentServiceInterface.class);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      return Sets.newHashSet(ComponentTest.class.getName());
    }
  }
}
