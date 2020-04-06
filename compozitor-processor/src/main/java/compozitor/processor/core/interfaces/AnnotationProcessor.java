package compozitor.processor.core.interfaces;

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
import java.util.Collections;
import java.util.Set;

public abstract class AnnotationProcessor implements Processor {
  private static final Boolean ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS = Boolean.FALSE;
  private final RunOnce runOnce;
  protected ProcessingContext context;
  protected AnnotationRepository repository;

  public AnnotationProcessor() {
    this.runOnce = RunOnce.create();
  }

  @Override
  public synchronized final void init(ProcessingEnvironment environment) {
    this.context = ProcessingContext.create(environment, this.getClass());
    this.context.info("Initializing processor {0}", this.getClass().getCanonicalName());
    this.init(context);
  }

  protected void init(ProcessingContext context) {
    return;
  }

  @Override
  public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
    try {
      this.context.info("Running processor {0}", this.getClass().getName());

      this.repository = AnnotationRepository.create(this.context, roundEnvironment);
      AnnotatedElements annotatedElements = repository.elementsAnnotatedWith(annotations);
      if (annotatedElements.isEmpty()) {
        context.info("Releasing resources for this processor.");
        this.releaseResources();
        return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
      }

      this.preProcess(repository);

      annotatedElements.forEach((annotation, elements) -> {
        elements.forEach(element -> {
          this.process(element);
        });

      });

      this.runOnce.run(() -> {
        this.context.info("Generating resources for {0}", this.getClass().getName());
        this.postProcess();
      });
    } catch (RuntimeException ex) {
      ex.printStackTrace();
      this.context.error(ex.getMessage());
    }

    return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
  }

  protected void releaseResources(){
    return;
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

  private final void process(Element element) {
    JavaModel javaModel = this.context.getJavaModel();

    if (element.getKind().equals(ElementKind.CLASS)) {
      TypeModel model = javaModel.getClass(element);
      this.context.info("Processing class {0}", model.getQualifiedName());
      this.process(model);
      return;
    }

    if (element.getKind().equals(ElementKind.INTERFACE)) {
      TypeModel model = javaModel.getInterface(element);
      this.context.info("Processing interface {0}", model.getQualifiedName());
      this.process(model);
      return;
    }

    if (element.getKind().equals(ElementKind.FIELD)) {
      this.context.info("Processing field {0} from class {1}", element.getSimpleName(), element.getEnclosingElement().getSimpleName());
      FieldModel model = javaModel.getField(element);
      this.process(model);
      return;
    }

    if (element.getKind().equals(ElementKind.METHOD)) {
      this.context.info("Processing method {0} from class {1}", element.getSimpleName(), element.getEnclosingElement().getSimpleName());
      MethodModel model = javaModel.getMethod(element);
      this.process(model);
      return;
    }
  }

  protected void preProcess(AnnotationRepository annotationRepository) {
    return;
  }

  protected void process(TypeModel typeModel) {
    return;
  }

  protected void process(FieldModel fieldModel) {
    return;
  }

  protected void process(MethodModel methodModel) {
    return;
  }

  protected void postProcess() {
    return;
  }
}
