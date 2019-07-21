package compozitor.engine.core.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaModel {
	protected final ProcessingEnvironment environment;
	
	public static JavaModel create(ProcessingEnvironment environment) {
		return new JavaModel(environment);
	}
	
	public ClassModel getClass(Element element) {
		if(!element.getKind().equals(ElementKind.CLASS)) {
			return null;
		}
		
		Elements elements = environment.getElementUtils();
		
		TypeElement type = (TypeElement) element;
		
		Optional<PackageElement> packageElement = Optional.ofNullable(elements.getPackageOf(element));
		PackageModel packageModel = packageElement.map(PackageModel::valueOf).orElse(null);
		
		Name className = type.getSimpleName();
		
		List<FieldModel> fields = ElementFilter.fieldsIn(type.getEnclosedElements()).stream().map(FieldModel::valueOf).collect(Collectors.toList());
	
		return null;
	}
}
