package obps.models;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class AppEnclosures {
	
	private Short enclosurecode;    
    private byte[] enclosure;    
    private CommonsMultipartFile fileContent;      
    
	public AppEnclosures() {
		super();
	}
    
    public Short getEnclosurecode() {
        return enclosurecode;
    }
    public void setEnclosurecode(Short enclosurecode) {
        this.enclosurecode = enclosurecode;
    }
    
    public byte[] getEnclosure() {
        return enclosure;
    }
    public void setEnclosure(byte[] enclosure) {
        this.enclosure = enclosure;
    }    
    
    public CommonsMultipartFile getFileContent() {
        return fileContent;
    }
    public void setFileContent(CommonsMultipartFile fileContent) {
        this.fileContent = fileContent;
    }     
    

    
	
}
