package compozitor.processor.core.interfaces;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compilation.Status;
import compozitor.processor.core.application.FieldProcessor;
import compozitor.processor.core.application.MethodProcessor;
import compozitor.processor.core.application.MethodProcessorWithAttributes;
import compozitor.processor.core.application.TypeProcessor;
import org.junit.Assert;
import org.junit.Test;

public class CompozitorProcessorTest {
  @Test
  public void givenTypeModelAnnotatedWithTypeAnnotationWhenCompileThenProcessType() {
    Compilation compilation =
      CompilationBuilder.create().withJavaSource("compozitor/processor/core/test/TypeModel.java")
        .withProcessors(new TypeProcessor()).build();
    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }

  @Test
  public void givenTypeModelAnnotatedWithFieldAnnotationWhenCompileThenProcessType() {
    Compilation compilation =
      CompilationBuilder.create().withJavaSource("compozitor/processor/core/test/TypeModel.java")
        .withProcessors(new FieldProcessor()).build();
    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }

  @Test
  public void givenTypeModelAnnotatedWithMethodAnnotationWhenCompileThenProcessType() {
    Compilation compilation =
      CompilationBuilder.create().withJavaSource("compozitor/processor/core/test/TypeModel.java")
        .withProcessors(new MethodProcessor()).build();
    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }

  @Test
  public void givenTypeModelAnnotatedWithMethodAnnotationWithAttributesWhenCompileThenProcessType() {
    Compilation compilation =
      CompilationBuilder.create().withJavaSource("compozitor/processor/core/test/TypeModel.java")
        .withProcessors(new MethodProcessorWithAttributes()).build();
    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }

  @Test
  public void givenMainClassWhenCompileThenProcessType() {
    Compilation compilation =
      CompilationBuilder.create().withJavaSource("compozitor/processor/core/test/MainClass.java")
        .withProcessors(new TypeProcessor()).build();
    Assert.assertEquals(Status.SUCCESS, compilation.status());
  }
}
