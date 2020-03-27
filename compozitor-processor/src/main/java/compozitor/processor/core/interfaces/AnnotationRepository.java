package compozitor.processor.core.interfaces;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "create")
public class AnnotationRepository {
  private final RoundEnvironment roundEnvironment;

  public <Annotation extends TypeElement> AnnotatedElements elementsAnnotatedWith(Annotation annotation) {
    return this.elementsAnnotatedWith(Sets.newHashSet(annotation));
  }

  public AnnotatedElements elementsAnnotatedWith(Set<? extends TypeElement> annotations) {
    AnnotatedElements elements = new AnnotatedElements();

    for (TypeElement annotation : annotations) {
      elements.set(annotation, this.roundEnvironment.getElementsAnnotatedWith(annotation));
    }

    return elements;
  }
}
