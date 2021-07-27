package obps.util.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import obps.util.application.ServiceUtilInterface;

@Component
public class PlanInfoDetails {
	@Autowired
	private ServiceUtilInterface SUI;

	public HashMap getPlanInfoDetails(String permitnumber) {
		HashMap params = new HashMap();
		JSONObject edcrJson = new JSONObject();

		try {

			Map<String, Object> planinfo = SUI.getPlanInfo(permitnumber);

			JSONParser parser = new JSONParser();
			edcrJson = (JSONObject) parser.parse((String) planinfo.get("planinfoobject"));
			DocumentContext edcrContext = JsonPath.parse(edcrJson.toString());

			// get values from planinfo object

			JSONArray blockTotalFloors = edcrContext.read("planDetail.blocks.*.building.totalFloors"); // total floors

			Integer totalFloor = 0;
			String blocktotalfloor = "";
			if (blockTotalFloors != null && !blockTotalFloors.isEmpty()) {
				int blockno = 0;
				for (Object obj : blockTotalFloors) { // for multiple blocks
					blockno++;
					totalFloor = (Integer) obj;
					if (blockno > 1) {
						blocktotalfloor += " : " + totalFloor;
					} else {
						blocktotalfloor = "" + totalFloor;
					}

//					blockwisefloor += "Block"+ blockno + ":" + totalFloor + " ";

				}
			}

			Double plotArea = ((Double) edcrContext.read("planDetail.planInformation.plotArea")); // plotarea

			String occupancy = edcrContext.read("planDetail.planInformation.occupancy");
			String khasarano = edcrContext.read("planDetail.planInfoProperties.KHASARA_NO");
			String khatianno = edcrContext.read("planDetail.planInfoProperties.KHATIAN_NO");
			String wardno = edcrContext.read("planDetail.planInfoProperties.WARD_NO");
			String wardname = edcrContext.read("planDetail.planInfoProperties.WARD");
			String district = edcrContext.read("planDetail.planInfoProperties.DISTRICT");
			Double buildingheight = (Double) edcrContext.read("planDetail.virtualBuilding.buildingHeight");
//			String far = edcrContext.read("planDetail.farDetails.providedFar");

			params.put("blocktotalfloor", blocktotalfloor);
			params.put("plotarea", plotArea);
			params.put("occupancy", occupancy);
			params.put("khasarano", khasarano);
			params.put("khatianno", khatianno);
			params.put("wardno", wardno);
			params.put("wardname", wardname);
			params.put("district", district);
			params.put("buildingheight", buildingheight);
			
//			JSONArray jarr=edcrContext.read("planDetail.blocks.*.building.floors.*.occupancies.*.typeHelper.type"); 

		} catch (Exception e) {
			
			System.out.println("Exception in getPlanInfoDetails :" + e);
			e.printStackTrace();
		}

		return params;
	}

}
