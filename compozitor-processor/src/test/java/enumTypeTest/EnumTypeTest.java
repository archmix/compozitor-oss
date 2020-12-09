package enumTypeTest;

import annotations.EnumConstantAnnotation;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.EnumConstantModel;
import compozitor.processor.core.interfaces.TestResources;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;
import org.junit.Test;
import processor.EnumConstantProcessor;
import processor.TypeProcessor;

public class EnumTypeTest {
  private TestResources resources = TestResources.create(this.getClass());

  @Test
  public void givenEnumWhenCompileThenGetTypeModel() {
    CompileAssertion compilation = CompilationBuilder.create()
      .withProcessors(new EnumTypeProcessorAssertion())
      .withJavaSource(
        resources.testFile("EnumType.java")
      ).build();

    compilation
      .assertSuccess()
      .assertGeneratedFiles(1);
  }

  @Test
  public void givenEnumConstantWithAnnotationThenGetModel() {
    CompileAssertion compilation = CompilationBuilder.create()
      .withProcessors(new EnumConstantProcessorAssertion())
      .withJavaSources(
        resources.testFile("EnumConstant.java"),
        resources.testFile("EnumConstantValue.java")
      ).build();

    compilation
      .assertSuccess()
      .assertGeneratedFiles(2);
  }

  class EnumConstantProcessorAssertion extends EnumConstantProcessor {
    @Override
    protected void process(EnumConstantModel enumConstant) {
      Assert.assertEquals("CONSTANT", enumConstant.getName());
      Assert.assertTrue(enumConstant.getAnnotations().getBy(EnumConstantAnnotation.class).isPresent());
    }
  }

  class EnumTypeProcessorAssertion extends TypeProcessor {
    @Override
    protected void process(TypeModel model) {
      super.process(model);

      Assert.assertTrue(1 == model.getFields().size());
      model.getFields().forEach(field -> {
        Assert.assertEquals("enumValue", field.getName());
        TypeModel fieldType = field.getType();
        Assert.assertEquals(ComplexEnum.class.getName(), fieldType.getQualifiedName());
        Assert.assertTrue(3 == fieldType.getFields().size());
        Assert.assertTrue(2 == fieldType.getConstants().size());
      });
    }
  }
}
