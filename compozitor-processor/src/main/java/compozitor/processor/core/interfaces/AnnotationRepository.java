package compozitor.processor.core.interfaces;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE, staticName = "create")
public class AnnotationRepository {
  private final ProcessingContext context;
  private final RoundEnvironment environment;

  public Set<? extends Element> elementsAnnotatedWith(Class<? extends Annotation> annotationClass) {
    TypeElement annotation = this.context.getTypeElement(annotationClass.getName());
    return this.environment.getElementsAnnotatedWith(annotation);
  }

  public AnnotatedElements elementsAnnotatedWith(Class<? extends Annotation>... annotationClasses) {
    return this.elementsAnnotatedWith(
      Arrays.asList(annotationClasses).stream().map(this::classToElement).collect(Collectors.toSet())
    );
  }

  private TypeElement classToElement(Class<?> annotationClass) {
    return this.context.getTypeElement(annotationClass.getName());
  }

  public <Annotation extends TypeElement> AnnotatedElements elementsAnnotatedWith(Annotation annotation) {
    return this.elementsAnnotatedWith(Sets.newHashSet(annotation));
  }

  public AnnotatedElements elementsAnnotatedWith(Set<? extends TypeElement> annotations) {
    AnnotatedElements elements = new AnnotatedElements();

    for (TypeElement annotation : annotations) {
      elements.set(annotation, this.environment.getElementsAnnotatedWith(annotation));
    }

    return elements;
  }
}
