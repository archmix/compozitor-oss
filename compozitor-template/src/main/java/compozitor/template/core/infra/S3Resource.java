package compozitor.template.core.infra;

public enum S3Resource {
  loader("class"), bucket("bucket");

  private final String key;

  private S3Resource(String key) {
    this.key = key;
  }

  public String propertyKey() {
    return "s3.resource.loader." + this.key;
  }

  public String key() {
    return key;
  }

  public static String resourceName() {
    return "s3";
  }
}
