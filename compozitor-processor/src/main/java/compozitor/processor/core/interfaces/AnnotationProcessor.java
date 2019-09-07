package compozitor.processor.core.interfaces;

import java.util.Collections;
import java.util.Set;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public abstract class AnnotationProcessor implements Processor {
  protected ProcessingContext context;

  protected JavaModel javaModel;

  protected JavaTypes javaTypes;

  private final RunOnce runOnce;

  public AnnotationProcessor() {
    this.runOnce = RunOnce.create();
  }

  @Override
  public synchronized final void init(ProcessingEnvironment environment) {
    this.context = ProcessingContext.create(environment);
    this.javaModel = JavaModel.create(context);
    this.javaTypes = JavaTypes.create(this.javaModel);
  }

  @Override
  public final boolean process(Set<? extends TypeElement> annotations,
      RoundEnvironment roundEnvironment) {

    if (roundEnvironment.processingOver()) {
      return true;
    }

    this.runOnce.run(() -> {
      this.context.info("Running processor {0}", this.getClass().getName());

      try {
        this.preProcess();

        annotations.forEach(annotation -> {
          this.context.info("Processing elements for annotation {0}", annotation);
          roundEnvironment.getElementsAnnotatedWith(annotation).forEach(element -> {
            this.context.info("Found type {0} with annotation {1}", element, annotation);
            this.process(annotation, element);
          });
          this.context.info("All elements processed for annotation {0}", annotation);
        });

        this.postProcess();
      } catch (RuntimeException ex) {
        ex.printStackTrace();
        this.context.error(ex.getMessage());
      }
    });

    return true;
  }

  private void process(TypeElement annotation, Element element) {
    if (element.getKind().equals(ElementKind.CLASS)) {
      this.context.info("Processing Element is a class", element);
      TypeModel model = javaModel.getClass(element);
      this.process(model);
      return;
    }

    if (element.getKind().equals(ElementKind.INTERFACE)) {
      this.context.info("Processing Element is an interface", element);
      TypeModel model = javaModel.getInterface(element);
      this.process(model);
      return;
    }

    if (element.getKind().equals(ElementKind.FIELD)) {
      this.context.info("Processing Element is a field", element);
      FieldModel model = javaModel.getField(element);
      this.process(model);
      return;
    }

    if (element.getKind().equals(ElementKind.METHOD)) {
      this.context.info("Processing Element is a method", element);
      MethodModel model = javaModel.getMethod(element);
      this.process(model);
      return;
    }
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Collections.emptySet();
  }

  @Override
  public final SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation,
      ExecutableElement member, String userText) {
    return Collections.emptyList();
  }

  @Override
  public Set<String> getSupportedOptions() {
    return Collections.emptySet();
  }

  protected void preProcess() {
    return;
  }

  protected void postProcess() {
    return;
  }

  protected void process(TypeModel model) {
    return;
  }

  protected void process(FieldModel model) {
    return;
  }

  protected void process(MethodModel model) {
    return;
  }
}
