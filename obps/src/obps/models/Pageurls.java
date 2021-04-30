package obps.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pageurls {

	private int urlcode;
	private String pageurl;
	private String subsubmenu;
	private String subsubmenuicon;
	private String submenu;
	private String submenuicon;
	private String parent;
	private String parenticon;
	private String checked;
	private Integer parentorder;
	private Integer submenuorder;
	private Integer subsubmenuorder;

	public Pageurls() {
	}

	public int getUrlcode() {
		return urlcode;
	}

	public void setUrlcode(int urlcode) {
		this.urlcode = urlcode;
	}

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParenticon() {
		return parenticon;
	}

	public void setParenticon(String parenticon) {
		this.parenticon = parenticon;
	}

	public String getSubmenu() {
		return submenu;
	}

	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}

	public String getSubmenuicon() {
		return submenuicon;
	}

	public void setSubmenuicon(String submenuicon) {
		this.submenuicon = submenuicon;
	}

	public String getSubsubmenu() {
		return subsubmenu;
	}

	public void setSubsubmenu(String subsubmenu) {
		this.subsubmenu = subsubmenu;
	}

	public String getSubsubmenuicon() {
		return subsubmenuicon;
	}

	public void setSubsubmenuicon(String subsubmenuicon) {
		this.subsubmenuicon = subsubmenuicon;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Integer getParentorder() {
		return parentorder;
	}

	public void setParentorder(Integer parentorder) {
		this.parentorder = parentorder;
	}

	public Integer getSubmenuorder() {
		return submenuorder;
	}

	public void setSubmenuorder(Integer submenuorder) {
		this.submenuorder = submenuorder;
	}

	public Integer getSubsubmenuorder() {
		return subsubmenuorder;
	}

	public void setSubsubmenuorder(Integer subsubmenuorder) {
		this.subsubmenuorder = subsubmenuorder;
	}

	@Override
	public String toString() {
		return "Pageurls{" + "urlcode=" + urlcode + ", pageurl=" + pageurl + ", subsubmenu=" + subsubmenu
				+ ", subsubmenuicon=" + subsubmenuicon + ", submenu=" + submenu + ", submenuicon=" + submenuicon
				+ ", parent=" + parent + ", parenticon=" + parenticon + '}';
	}

}
