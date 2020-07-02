package processorEngineTest;

import java.util.List;

@Table
public class Message<T> {

  private T content;
  private List<String> sendTo;

}
