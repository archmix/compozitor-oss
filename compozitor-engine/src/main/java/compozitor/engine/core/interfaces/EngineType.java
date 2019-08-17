package compozitor.engine.core.interfaces;

public interface EngineType {
  String value();
  
  public static EngineType adapter(String value) {
    return new EngineType() {
      @Override
      public String value() {
        return value;
      }
    };
  }
}
