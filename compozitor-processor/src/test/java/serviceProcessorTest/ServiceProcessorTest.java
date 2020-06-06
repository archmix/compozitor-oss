package serviceProcessorTest;

import com.google.common.collect.Sets;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.ServiceProcessor;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

public class ServiceProcessorTest {
  private final TestResources resources = TestResources.create(this.getClass());

  @Test
  public void givenServiceInterfaceWhenCompileThenGenerateServiceFile() {
    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new ProcessorTest())
        .withJavaSource(
          resources.testFile("ServiceInterfaceTest.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(2);
    compilation.serviceFileAssertion(ServiceInterface.class)
      .assertContains(resources.packageClass("ServiceInterfaceTest"));
  }

  @Test
  public void givenServiceClassWhenCompileThenGenerateServiceFile() {
    ProcessorTest componentProcessor = new ProcessorTest();
    componentProcessor.traverseAncestors();

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(componentProcessor)
        .withJavaSource(
          resources.testFile("ServiceClassTest.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(3);
    compilation.serviceFileAssertion(ServiceInterface.class)
      .assertContains(resources.packageClass("ServiceClassTest"));
    compilation.serviceFileAssertion(ServiceClass.class)
      .assertContains(resources.packageClass("ServiceClassTest"));
  }

  public class ProcessorTest extends ServiceProcessor {

    @Override
    protected Iterable<Class<?>> serviceClasses() {
      return Arrays.asList(ServiceInterface.class, ServiceClass.class);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      return Sets.newHashSet(ServiceTest.class.getName());
    }
  }
}
