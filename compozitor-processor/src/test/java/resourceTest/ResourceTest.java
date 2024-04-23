package resourceTest;

import compozitor.processor.core.interfaces.Resource;
import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {
    @Test
    public void givenRootPathWithSlashInTheEndWhenGetPathThenReturnsProperPath() {
        String root = "path/to/root/";
        String node = "node";

        String path = Resource.path(root, node);
        Assert.assertEquals(path, "path/to/root/node");
    }


}
