package obps.models;

import java.io.Serializable;
import java.util.List;

public class Urls implements Serializable{
	private String name;
	private String icon;
	private String pageurl;
	private List<Urls> suburls;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPageurl() {
		return pageurl;
	}
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	public List<Urls> getSuburls() {
		return suburls;
	}
	public void setSuburls(List<Urls> suburls) {
		this.suburls = suburls;
	}
	
}
