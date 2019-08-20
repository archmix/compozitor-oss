package compozitor.processor.core.interfaces;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.VariableElement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public class Fields extends ModelIterable<FieldModel> {

  Fields(List<VariableElement> fields, JavaModel javaModel) {
    super(new FieldsSupplier(fields, javaModel));
  }
  
  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class FieldsSupplier implements ListSupplier<FieldModel> {
    private final List<VariableElement> fields;
    
    private final JavaModel javaModel;

    @Override
    public List<FieldModel> get() {
      List<FieldModel> models = new ArrayList<>();
      
      this.fields.forEach(element ->{
        models.add(javaModel.getField(element));
      });
      
      return models;
    }
  }

}
