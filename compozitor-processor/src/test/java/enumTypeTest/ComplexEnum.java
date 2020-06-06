package enumTypeTest;

public enum ComplexEnum {
  ONE(1),
  TWO(2);

  private Integer value;

  private ComplexEnum(Integer value) {
    this.value = value;
  }

  public Integer value() {
    return this.value;
  }
}
