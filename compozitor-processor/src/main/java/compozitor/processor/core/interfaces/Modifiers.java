package compozitor.processor.core.interfaces;

import java.util.Iterator;
import java.util.Set;

import javax.lang.model.element.Modifier;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Modifiers implements Iterable<Modifier> {
	private final Set<Modifier> modifiers;
	
	@Override
	public Iterator<Modifier> iterator() {
		return this.modifiers.iterator();
	}
	
	public boolean isAbstract() {
		return this.contains(Modifier.ABSTRACT);
	}
	
	public boolean isDefault() {
		return this.contains(Modifier.DEFAULT);
	}
	
	public boolean isFinal() {
		return this.contains(Modifier.FINAL);
	}
	
	public boolean isNative() {
		return this.contains(Modifier.NATIVE);
	}
	
	public boolean isPrivate() {
		return this.contains(Modifier.PRIVATE);
	}
	
	public boolean isProtected() {
		return this.contains(Modifier.PROTECTED);
	}
	
	public boolean isPublic() {
		return this.contains(Modifier.PUBLIC);
	}
	
	public boolean isStatic() {
		return this.contains(Modifier.STATIC);
	}
	
	public boolean isStrict() {
		return this.contains(Modifier.STRICTFP);
	}
	
	public boolean isSynchronized() {
		return this.contains(Modifier.SYNCHRONIZED);
	}
	
	public boolean isTransient() {
		return this.contains(Modifier.TRANSIENT);
	}
	
	public boolean isVolatile() {
		return this.contains(Modifier.VOLATILE);
	}
	
	private boolean contains(Modifier modifier) {
		return this.modifiers.contains(modifier);
	}
}
