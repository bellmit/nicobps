
package obps.util.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
public class UtilFile 
{
    public static boolean isValidFile(final byte[] docBytes, final String pattern)
    {
        boolean valid = false;
        try 
        {
            TikaConfig config = TikaConfig.getDefaultConfig();
            Detector detector = config.getDetector();
            TikaInputStream stream = TikaInputStream.get(docBytes);
            Metadata metadata = new Metadata();
            metadata.add(Metadata.RESOURCE_NAME_KEY, null);
            MediaType mediaType;
            mediaType = detector.detect(stream, metadata);
            if (Patterns.PatternCompileMatche(pattern,mediaType.toString())) {
                valid = true;
            }
        } catch (IOException ex) {
            System.out.println("Error in isValidFile(final byte[] docBytes, final String pattern) : "+ex);
        }
        return valid;
    }
    public static String getFileContentType(final byte[] docBytes)
    {
        String contantType = null;
        try 
        {
            TikaConfig config = TikaConfig.getDefaultConfig();
            Detector detector = config.getDetector();
            TikaInputStream stream = TikaInputStream.get(docBytes);
            Metadata metadata = new Metadata();
            metadata.add(Metadata.RESOURCE_NAME_KEY, null);
            MediaType mediaType;
            mediaType = detector.detect(stream, metadata);
            contantType = mediaType.toString();
        } catch (IOException ex) {
            System.out.println("Error in getFileContentType(final byte[] docBytes) : "+ex);
        }
        return contantType;
    }

        
}
