package obps.services;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ext.Java7Handlers;

import obps.daos.DaoEdcrScrutinyInterface;
import obps.models.EdcrScrutiny;

@Service("ServiceEdcrScrutiny")
public class ServiceEdcrScrutiny {

	@Autowired
	DaoEdcrScrutinyInterface DaoedcrScrutinyInterface;

	public List<EdcrScrutiny> fetch_usercd(String usercd) {
		List<EdcrScrutiny> resp = null;
		resp = DaoedcrScrutinyInterface.fetchEdcr_usercd(usercd);
		return resp;
	}

	public EdcrScrutiny fetch(String edcrnumber) {

		EdcrScrutiny resp = DaoedcrScrutinyInterface.fetchEdcr(edcrnumber);
		return resp;
	}

	public JSONObject Scrutinize(MultipartFile planFile, String usercode) {
		String resp = null;
		JSONObject respJson = null;
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		String edcrRequest = "{\r\n" + "\"transactionNumber\": \"" + uuid + "\",\r\n"
				+ "\"applicationSubType\": \"NEW_CONSTRUCTION\",\r\n"
				+ "\"appliactionType\": \"BUILDING_PLAN_SCRUTINY\",\r\n" + "\"applicantName\": \"Suraj\",\r\n"
				+ "\"tenantId\": \"jk.jammu\",\r\n" + "\"RequestInfo\": {\r\n" + "\"userInfo\": {\r\n"
				+ "\"id\": \"1c79f77e-e847-4663-98a7-5aee31f185c5\",\r\n" + "\"tenantId\": \"0003\"\r\n" + "}\r\n"
				+ "}\r\n" + "}\r\n" + "";

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
			ByteArrayResource contentsAsResource = new ByteArrayResource(planFile.getBytes()) {
				@Override
				public String getFilename() {
					return "plan.dxf";
				}
			};
			valueMap.add("planFile", contentsAsResource);
			valueMap.add("edcrRequest", edcrRequest);
			System.out.println("planfile.getBytes()::" + valueMap.get("planFile"));
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(valueMap, headers);
			String serverUrl = "http://uatobps.jkhudd.gov.in/edcr/rest/dcr/scrutinize?tenantId=jk.jammu";
			RestTemplate restTemplate = new RestTemplate();
			resp = restTemplate.postForObject(serverUrl, valueMap, String.class);
			// --------------save to db------------
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(resp);
			String edcrnumber = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("edcrNumber"))
					.toString();
			String status = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("status")).toString();
			String planReport = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("planReport"))
					.toString();
			String edcrdetails = (new ObjectMapper())
					.writeValueAsString(((List<Map<String, Object>>) json.get("edcrDetail")).get(0));
//			String planReport =(new ObjectMapper()).writeValueAsString(((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("planReport")).trim() ;
//			String planPDF= (new ObjectMapper()).writeValueAsString(((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("planPdfs"));

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usercode", usercode);
			map.put("useroffice", DaoedcrScrutinyInterface.GetOfficeCode(usercode));
			map.put("edcrnumber", edcrnumber);
			map.put("status", status);
			map.put("response", edcrdetails);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			map.put("log_date", dateFormat.format(date));
			boolean doaresp = DaoedcrScrutinyInterface.createEdcrScrutiny(map);
			// ---------------------------------
			if (doaresp) {
				respJson = new JSONObject();
				respJson.put("status", status);
				respJson.put("edcrnumber", edcrnumber);
				respJson.put("planReport", planReport);
			} else {
				respJson = new JSONObject();
				respJson.put("status", "error");
				respJson.put("edcrnumber", "NA");
				respJson.put("planReport", "NA");
				respJson.put("msg", "System Error: Please contact System Admin!");

			}

		} catch (Exception e) {
			resp = e.getMessage();
			e.printStackTrace();
		}
		return respJson;
	}

}