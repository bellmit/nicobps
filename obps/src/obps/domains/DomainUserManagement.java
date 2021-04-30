package obps.domains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import obps.models.Pageurls;
import obps.models.Urls;

@Component
public class DomainUserManagement implements DomainUserManagementInterface {

	@Override
	public List<Urls> processUrls(List<Pageurls> urls) {

		List<Urls> arrParent = new ArrayList<Urls>();
		Urls objParent = null;
		Urls objSubmenu = null;
		Urls objSubsubmenu = null;
		List<Urls> arrSubmenu = null;
		List<Urls> arrSubSubmenu = null;
		List<String> parent = new LinkedList<String>();
		List<String> submenu = new LinkedList<String>();
		for (Pageurls url : urls) {

			if (!parent.contains(url.getParent())) {
				parent.add(url.getParent());
				submenu = new LinkedList<String>();
				if (objSubmenu != null) {
					objSubmenu.setSuburls(((arrSubSubmenu != null) ? arrSubSubmenu : null));
					arrSubmenu.add(objSubmenu);
				}
				if (objParent != null) {
					objParent.setSuburls(arrSubmenu);
					arrParent.add(objParent);
				}
				objParent = new Urls();
				objParent.setName(url.getParent());
				objParent.setIcon((url.getParenticon() != null) ? url.getParenticon() : "");
				objParent.setPageurl((url.getSubmenu() == null) ? url.getPageurl() : "");

				arrSubmenu = new ArrayList<Urls>();
				objSubmenu = new Urls();
				submenu.add(url.getSubmenu());
				objSubmenu.setName(url.getSubmenu());
				objSubmenu.setIcon((url.getSubmenuicon() != null) ? url.getSubmenuicon() : "");
				objSubmenu.setPageurl((url.getSubsubmenu() == null) ? url.getPageurl() : "");
				//
				arrSubSubmenu = new ArrayList<Urls>();
				objSubsubmenu = new Urls();
				objSubsubmenu.setName((url.getSubsubmenu() != null) ? url.getSubsubmenu() : "");
				objSubsubmenu.setIcon((url.getSubsubmenuicon() != null) ? url.getSubsubmenuicon() : "");
				objSubsubmenu.setPageurl((url.getSubsubmenu() != null) ? url.getPageurl() : "");
				arrSubSubmenu.add(objSubsubmenu);
				objSubmenu.setSuburls(arrSubSubmenu);
				//
				arrSubmenu.add(objSubmenu);
				objSubmenu = null;
			} else {
				if (!submenu.contains(url.getSubmenu())) {
					submenu.add(url.getSubmenu());
					if (objSubmenu != null) {
						objSubmenu.setSuburls((arrSubSubmenu != null) ? arrSubSubmenu : null);
						arrSubmenu.add(objSubmenu);
					}
					objSubmenu = new Urls();
					objSubmenu.setName(url.getSubmenu());
					objSubmenu.setIcon((url.getSubmenuicon() != null) ? url.getSubmenuicon() : "");
					objSubmenu.setPageurl((url.getSubsubmenu() == null) ? url.getPageurl() : "");
					//
					arrSubSubmenu = new ArrayList<Urls>();
					objSubsubmenu = new Urls();
					objSubsubmenu.setName((url.getSubsubmenu() != null) ? url.getSubsubmenu() : "");
					objSubsubmenu.setIcon((url.getSubsubmenuicon() != null) ? url.getSubsubmenuicon() : "");
					objSubsubmenu.setPageurl((url.getSubsubmenu() != null) ? url.getPageurl() : "");
					arrSubSubmenu.add(objSubsubmenu);
					objSubsubmenu = null;
					//
				} else {
					objSubsubmenu = new Urls();
					objSubsubmenu.setName((url.getSubsubmenu() != null) ? url.getSubsubmenu() : "");
					objSubsubmenu.setIcon((url.getSubsubmenuicon() != null) ? url.getSubsubmenuicon() : "");
					objSubsubmenu.setPageurl((url.getSubsubmenu() != null) ? url.getPageurl() : "");
					arrSubSubmenu.add(objSubsubmenu);
				}
			}
		}
		if (objSubmenu != null) {
			objSubmenu.setSuburls((arrSubSubmenu != null) ? arrSubSubmenu : null);
			arrSubmenu.add(objSubmenu);
		}
		if (objParent != null) {
			objParent.setSuburls(arrSubmenu);
			arrParent.add(objParent);
		}
		return arrParent;
	}

}
