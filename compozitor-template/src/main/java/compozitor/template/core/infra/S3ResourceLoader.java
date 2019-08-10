package compozitor.template.core.infra;

import java.io.InputStream;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import compozitor.template.core.interfaces.S3Bucket;

public class S3ResourceLoader extends ResourceLoader {
  private S3Bucket s3Bucket;
  private AmazonS3 s3Client;

  @Override
  public void init(ExtendedProperties configuration) {
    this.s3Bucket = S3Bucket.withName(configuration);
    this.s3Client = AmazonS3ClientBuilder.defaultClient();
  }

  @Override
  public InputStream getResourceStream(String source) throws ResourceNotFoundException {
    S3Object s3Object = this.s3Client.getObject(new GetObjectRequest(this.s3Bucket.name(), source));
    return s3Object.getObjectContent();
  }

  @Override
  public long getLastModified(Resource resource) {
    ObjectMetadata metadata = this.s3Client
        .getObjectMetadata(new GetObjectMetadataRequest(this.s3Bucket.name(), resource.getName()));
    return metadata.getLastModified().getTime();
  }

  @Override
  public boolean isSourceModified(Resource resource) {
    return false;
  }
}
