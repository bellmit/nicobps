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

	public EdcrScrutiny fetch(String edcrnumber) {
		 
		EdcrScrutiny resp = DaoedcrScrutinyInterface.fetchEdcr(edcrnumber);
		return resp;
	}

	public String Scrutinize(String edcrRequest, MultipartFile planFile,String usercode) {
		String resp = null;
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
			String edcrdetails = (new ObjectMapper())
					.writeValueAsString(((List<Map<String, Object>>) json.get("edcrDetail")).get(0));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usercode", "1");
			map.put("edcrnumber", (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("edcrNumber")));
			map.put("response", edcrdetails);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			map.put("log_date", dateFormat.format(date));
			DaoedcrScrutinyInterface.createEdcrScrutiny(map);
			// ---------------------------------
		} catch (Exception e) {
			resp = e.getMessage();
			e.printStackTrace();
		}
		return resp;
	}

}
