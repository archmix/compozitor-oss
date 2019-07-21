package compozitor.engine.core.interfaces;

import java.util.List;

import javax.lang.model.element.Name;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class ClassModel {
	private final PackageModel packageModel;
	
	private final Name name;
	
	private final List<FieldModel> fields;	
	

	
	public PackageModel getPackage() {
		return this.packageModel;
	}
}
