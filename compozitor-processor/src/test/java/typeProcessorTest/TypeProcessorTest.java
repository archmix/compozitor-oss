package typeProcessorTest;

import annotations.TypeAnnotation;
import annotations.TypeProcessor;
import com.google.common.collect.Sets;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TestResources;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TypeProcessorTest {

  @Test
  public void givenTypeAnnotationWhenCompileThenProcessType(){
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new TypeProcessor())
        .withJavaSource(resources.testFile("TypeTest.java"))
        .build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }
}
