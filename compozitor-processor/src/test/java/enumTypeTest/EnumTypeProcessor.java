package enumTypeTest;

import annotations.TypeProcessor;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;

public class EnumTypeProcessor extends TypeProcessor {
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
