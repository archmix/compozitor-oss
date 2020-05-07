package methodProcessorTest;

import annotations.MethodAnnotation;
import annotations.MethodProcessor;
import com.google.common.collect.Sets;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compilation.Status;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TestResources;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MethodProcessorTest {
  @Test
  public void givenMethodAnnotationWhenCompileThenProcessMethod() {
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new MethodProcessor())
        .withJavaSource(
          resources.testFile("MethodTest.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }
}
