package compozitor.engine.core.interfaces;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class TargetAnnotations {
  private final Set<String> annotations;

  TargetAnnotations() {
    this.annotations = new HashSet<>();
  }

  public static TargetAnnotations create() {
    return new TargetAnnotations();
  }

  public TargetAnnotations add(TargetAnnotations targetAnnotations) {
    this.annotations.addAll(targetAnnotations.annotations);
    return this;
  }

  public TargetAnnotations add(Class<? extends Annotation> targetAnnotation) {
    this.annotations.add(targetAnnotation.getName());
    return this;
  }

  Set<String> values() {
    return annotations;
  }
}
