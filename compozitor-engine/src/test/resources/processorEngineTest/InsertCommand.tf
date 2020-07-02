package $Code.Namespace;

public class ${Code.ResourceName} {
  private final String command;

  private ${Code.ResourceName}() {
    this.command = "#insertSQL($Table)";
  }

  public static ${Code.ResourceName} create() {
    return new ${Code.ResourceName}();
  }

  public String command() {
    return this.command;
  }
}