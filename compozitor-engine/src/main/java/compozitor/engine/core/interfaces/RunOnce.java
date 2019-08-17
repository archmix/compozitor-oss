package compozitor.engine.core.interfaces;

public class RunOnce {
  private State state;
  
  public RunOnce() {
    this.state = State.INITIAL;
  }
  
  public void run(Runnable runnable) {
    this.state.run(runnable);
    this.state = State.RAN;
  }
  
  enum State {
    INITIAL{
      @Override
      void run(Runnable runnable) {
        runnable.run();
      }
    },
    RAN{
      @Override
      void run(Runnable runnable) {
        //do nothing
      }
    };
    
    abstract void run(Runnable runnable);
  }
}
