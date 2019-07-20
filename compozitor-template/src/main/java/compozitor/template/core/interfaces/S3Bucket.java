package compozitor.template.core.interfaces;

import org.apache.commons.collections.ExtendedProperties;

import compozitor.template.core.infra.S3Resource;

public class S3Bucket {
	private final String name;
	
	private S3Bucket(String name) {
		this.name = name;
	}
	
	public static S3Bucket withName(ExtendedProperties properties) {
		return new S3Bucket(properties.getString(S3Resource.bucket.name()));
	}
	
	public static S3Bucket withName(String name) {
		return new S3Bucket(name);
	}

	public String name() {
		return name;
	}
}
