package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.EngineCategory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public abstract class TargetAnnotations<T extends TargetAnnotations<T>> implements EngineCategory {
    private final Set<String> annotations;

    TargetAnnotations() {
        this.annotations = new HashSet<>();
    }

    protected T add(Class<? extends Annotation> targetAnnotation){
        this.annotations.add(targetAnnotation.getName());
        return (T) this;
    }

    public Set<String> targetAnnotations() {
        return annotations;
    }
}
