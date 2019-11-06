package compozitor.processor.core.interfaces;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.VariableElement;
import lombok.RequiredArgsConstructor;

public class Arguments extends ModelIterable<ArgumentModel> {
  
  Arguments(List<? extends VariableElement> arguments, JavaModel javaModel) {
    super(new ArgumentsSupplier(arguments, javaModel));
  }

  @RequiredArgsConstructor
  static class ArgumentsSupplier implements ListSupplier<ArgumentModel>{
    private final List<? extends VariableElement> arguments;
    
    private final JavaModel javaModel;
    
    @Override
    public List<ArgumentModel> get() {
      List<ArgumentModel> models = new ArrayList<>();
      this.arguments.forEach(argument ->{
        models.add(this.javaModel.getArgument(argument));
      });
      return models;
    }
  }
}
