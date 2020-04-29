package classNameTest;

import compozitor.processor.core.interfaces.JavaFileName;
import org.junit.Assert;
import org.junit.Test;

public class JavaFileNameTest {
  @Test
  public void givenAClassNameWhenCreateThenRemoveIllegalChars(){
    assertClassName("Test Class", "TestClass");
    assertClassName("Acentuação é normalizada", "Acentuacaoenormalizada");
  }

  private void assertClassName(String classNameToBeCreated, String expectedClassName){
    String actualClassName = JavaFileName.create(classNameToBeCreated).toString();
    Assert.assertEquals(expectedClassName, actualClassName);
  }
}
