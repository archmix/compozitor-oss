package compozitor.processor.core.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public enum TraversalStrategy {
  ONE {
    @Override
    public Iterable<TypeModel> superClasses(TypeModel typeModel) {
      return Arrays.asList(typeModel.getSuperType());
    }

    @Override
    public Iterable<TypeModel> interfaces(TypeModel typeModel) {
      return typeModel.getInterfaces();
    }
  },
  ALL{
    @Override
    public Iterable<TypeModel> superClasses(TypeModel typeModel) {
      Collection<TypeModel> superClasses = new ArrayList<>();
      this.addSuperClass(typeModel, superClasses);
      return superClasses;
    }

    private void addSuperClass(TypeModel typeModel, Collection<TypeModel> superClasses){
      TypeModel superType = typeModel.getSuperType();
      if(superType == null){
        return;
      }
      superClasses.add(superType);
      this.addSuperClass(superType, superClasses);
    }

    @Override
    public Iterable<TypeModel> interfaces(TypeModel typeModel) {
      Collection<TypeModel> interfaces = new ArrayList<>();
      interfaces.addAll((Collection) typeModel.getInterfaces());
      this.addInterfaces(typeModel, interfaces);
      return interfaces;
    }

    private void addInterfaces(TypeModel typeModel, Collection<TypeModel> interfaces){
      TypeModel superType = typeModel.getSuperType();
      if(superType == null){
        return;
      }
      interfaces.addAll((Collection) superType.getInterfaces());
      this.addInterfaces(superType, interfaces);
    }
  };

  public abstract Iterable<TypeModel> superClasses(TypeModel typeModel);

  public abstract Iterable<TypeModel> interfaces(TypeModel typeModel);
}
