package obps.models;

import java.io.Serializable;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author DezentcKhongstia
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppEnclosure implements Serializable {
	private static final long serialVersionUID = 8L;

	private Integer code;
	private String file;
	private byte[] fileImage;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public byte[] getFileImage() {
		return fileImage;	
	}

	public void setFileImage(byte[] fileImage) {
		this.fileImage = fileImage;
	}

	public void setFileImageByBase64String(String file) {
		try {
			this.fileImage = Base64.getDecoder().decode(file.split(",")[1]);
		} catch (Exception e) {
			this.fileImage = null;
		}
	}

	@Override
	public String toString() {
		return "AppEnclosure [code=" + code + ", file=" + file + "]";
	}

}
