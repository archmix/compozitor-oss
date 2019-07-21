package compozitor.engine.core.interfaces;

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PackageModel {
	private final Name value;

	public static PackageModel valueOf(PackageElement element) {
		return new PackageModel(element.getQualifiedName());
	}
}
