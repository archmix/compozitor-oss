package compozitor.processor.core.interfaces;

import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "create")
public class RunOnce {
  private State state = State.INITIAL;

  public void run(Runnable runnable) {
    this.state.run(runnable);
    this.state = State.DONE;
  }

  static enum State {
    INITIAL {
      @Override
      void run(Runnable runnable) {
        runnable.run();
      }
    },
    DONE {
      @Override
      void run(Runnable runnable) {
      }
    };

    abstract void run(Runnable runnable);
  }
}