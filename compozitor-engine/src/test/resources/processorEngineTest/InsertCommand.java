package compozitor.engine.core.infra;

public class CityInsertCommand {
  private final String command;

  private CityInsertCommand(){
    this.command = "INSERT INTO City (id,name,state) VALUES (?,?,?)";
  }

  public static CityInsertCommand create(TransactionContext context){
    return new CityInsertCommand(context);
  }

  public String command(){
    return this.command;
  }
}