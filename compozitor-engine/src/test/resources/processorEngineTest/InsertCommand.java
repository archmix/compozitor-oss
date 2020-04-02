package processorEngineTest;

public class CityInsertCommand {
  private final String command;

  private CityInsertCommand(){
    this.command = "INSERT INTO City (id,name,state) VALUES (?,?,?)";
  }

  public static CityInsertCommand create(){
    return new CityInsertCommand();
  }

  public String command(){
    return this.command;
  }
}