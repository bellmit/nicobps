package obps.util.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import obps.util.application.ServiceUtilInterface;

@Component
public class PlanInfoDetails {
	@Autowired
	private ServiceUtilInterface SUI;

	public HashMap getPlanInfoDetails(String permitnumber) {
		System.out.println("--------------PlanInfoDetails----------------");
		HashMap params = new HashMap();
		JSONObject edcrJson = new JSONObject();

		try {

			Map<String, Object> planinfo = SUI.getPlanInfo(permitnumber);

			JSONParser parser = new JSONParser();
			edcrJson = (JSONObject) parser.parse((String) planinfo.get("planinfoobject"));
			DocumentContext edcrContext = JsonPath.parse(edcrJson.toString());

			// get values from planinfo object

			Double plotArea = ((Double) edcrContext.read("planDetail.planInformation.plotArea")); // plotarea

			String occupancy = edcrContext.read("planDetail.planInformation.occupancy");
			String khasarano = edcrContext.read("planDetail.planInfoProperties.KHASARA_NO");
			String khatianno = edcrContext.read("planDetail.planInfoProperties.KHATIAN_NO");
			String wardno = edcrContext.read("planDetail.planInfoProperties.WARD_NO");
			String wardname = edcrContext.read("planDetail.planInfoProperties.WARD");
			String district = edcrContext.read("planDetail.planInfoProperties.DISTRICT");
			Double buildingheight = (Double) edcrContext.read("planDetail.virtualBuilding.buildingHeight");
//			String far = edcrContext.read("planDetail.farDetails.providedFar");

			params.put("plotarea", plotArea);
			params.put("occupancy", occupancy);
			params.put("khasarano", khasarano);
			params.put("khatianno", khatianno);
			params.put("wardno", wardno);
			params.put("wardname", wardname);
			params.put("district", district);
			params.put("buildingheight", buildingheight);

			ObjectMapper omp = new ObjectMapper();

			JSONArray floorDetails = edcrContext.read("planDetail.blocks.*.building.floors.*");
//			System.out.println("floorDetails=" + omp.writeValueAsString(floorDetails).length());
//			System.out.println(omp.writeValueAsString(floorDetails));
			DocumentContext floorDetailsContext = JsonPath.parse(omp.writeValueAsString(floorDetails));
//			System.out.println("floor " + floorDetailsContext.toString());
//			System.out.println("floor number" + floorDetailsContext.read("*.number"));
			JSONArray uniqueOccupancies = new JSONArray();

			for (Object item : (JSONArray) edcrContext
					.read("planDetail.blocks.*.building.floors.*.occupancies.*.typeHelper.type")) {
				if (!uniqueOccupancies.contains(item)) {
					uniqueOccupancies.add(item);
				}
			}

//			System.out.println("uniqueOccupancies= " + uniqueOccupancies.toString());

			JSONArray blockTotalFloors = edcrContext.read("planDetail.blocks.*.building.totalFloors"); // total floors

			JSONArray blocks = edcrContext.read("planDetail.blocks.*");
			DocumentContext blockdetails = JsonPath.parse(omp.writeValueAsString(blocks));

			Integer totalFloor = 0;
			String blocknum = "";
			String blocktotalfloor = "";
//			

			List<String> tHead = new LinkedList<>();
			List<List<Object>> tBody = new LinkedList<>();
			List<Object> flrDetails = new ArrayList<Object>();
			JSONObject flr = null;

			if (blockTotalFloors != null && !blockTotalFloors.isEmpty()) {
				int blockno = 1;
				System.out.println("blocktotalfloor : " + blockTotalFloors.toString());

				for (Object obj : blocks) { // for multiple blocks
					DocumentContext blockdet = JsonPath.parse(omp.writeValueAsString(obj));
					totalFloor = blockdet.read("building.totalFloors");
					blocknum = blockdet.read("number");

					for (int floorno = 0, j = 0; floorno < totalFloor; floorno++) {
//						flrDetails = new LinkedList<Map<String, Object>>();
						j = 0;

						for (Object o : uniqueOccupancies) {
							Map<String, Object> occ = (HashMap<String, Object>) o;

							flr = new JSONObject();

							if (!tHead.contains(occ.get("name").toString()))
								tHead.add(occ.get("name").toString());

							flr.put("blockno", blocknum);
							flr.put("floorno", floorno);
							flr.put("occupancy", occ.get("name").toString());

							JSONArray flrDtls = blockdet.read("building.floors.[?(@.number=='" + floorno
									+ "')].occupancies[?(@.typeHelper.type.code=='" + occ.get("code").toString()
									+ "')].builtUpArea");

							flr.put("builtuparea", (!flrDtls.isEmpty()) ? flrDtls.get(0).toString() : "0");
							flrDetails.add(flr);
						}
						tBody.add(flrDetails);
					}

					if (blockno > 1) {
						blocktotalfloor += " : " + totalFloor;
					} else {
						blocktotalfloor = "" + totalFloor;
					}
					blockno++;

				}
			}
//			System.out.println("tHead : " + tHead);
//			System.out.println("tBody : " + tBody);
//			System.out.println("flrDetails  : " + flrDetails);

			params.put("blocktotalfloor", blocktotalfloor);

			JRBeanCollectionDataSource datas = new JRBeanCollectionDataSource(flrDetails);

			params.put("table_builtuparea", datas);

		} catch (Exception e) {

			System.out.println("Exception in getPlanInfoDetails :" + e);
			e.printStackTrace();
		}

		return params;
	}

}
