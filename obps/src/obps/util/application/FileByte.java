/*@author Avijit Debnath*/

package obps.util.application;

import org.springframework.web.multipart.MultipartFile;

public class FileByte
{
    private byte[] file;
    private MultipartFile[] multipartfile;
    private String appcode;
    
    public FileByte() {
    }

    public byte[] getFile() {
        return file;
    }
    public void setFile(byte[] file) {
        this.file = file;
    }

    public MultipartFile[] getMultipartfile() {
        return multipartfile;
    }
    public void setMultipartfile(MultipartFile multipartfile[]) {
        this.multipartfile = multipartfile;
    }

    public String getAppcode() {
        return appcode;
    }
    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    
}


