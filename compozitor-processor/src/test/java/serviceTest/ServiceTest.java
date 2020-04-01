package serviceTest;

import compozitor.processor.core.infra.AutoServiceProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.ServiceProcessor;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class ServiceTest {
  private final TestResources resources = TestResources.create(this.getClass());

  @Test
  public void givenServiceAnnotationWithTargetInterfaceWhenCompileThenGenerateServiceFile(){
    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new AutoServiceProcessor())
        .withJavaSource(
          this.resources.testFile("ServiceTargetInterface.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(2);
    compilation.serviceFileAssertion(ServiceInterface.class)
      .assertContains(resources.packageClass("ServiceTargetInterface"));
  }

  @Test
  public void givenServiceAnnotationWithTargetSuperClassWhenCompileThenGenerateServiceFile(){
    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new AutoServiceProcessor())
        .withJavaSource(
          this.resources.testFile("ServiceTargetSuperClass.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(2);
    compilation.serviceFileAssertion(ServiceProcessor.class)
      .assertContains(resources.packageClass("ServiceTargetSuperClass"));
  }

  @Test
  public void givenServiceAnnotationWithTargetSuperClassAndJavaAncestorsWhenCompileThenGenerateServiceFile(){
    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new AutoServiceProcessor())
        .withJavaSource(
          this.resources.testFile("ServiceTargetSuperClassJavaAncestor.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }

  @Test
  public void givenServiceAnnotationWithTargetSuperClassAndAncestorsWhenCompileThenGenerateServiceFile(){
    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new AutoServiceProcessor())
        .withJavaSource(
          this.resources.testFile("ServiceTargetSuperClassAncestor.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(3);
    compilation.serviceFileAssertion(ServiceSuperClass.class)
      .assertContains(resources.packageClass("ServiceTargetSuperClassAncestor"));
    compilation.serviceFileAssertion(ServiceAncestor.class)
      .assertContains(resources.packageClass("ServiceTargetSuperClassAncestor"));
  }
}
