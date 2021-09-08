package obps.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ext.Java7Handlers;

import obps.daos.DaoEdcrScrutinyInterface;
import obps.models.EdcrScrutiny;
import obps.models.Userlogin;
import obps.util.application.ServiceUtilInterface;

@Service("ServiceEdcrScrutiny")
public class ServiceEdcrScrutiny {

	@Autowired
	DaoEdcrScrutinyInterface DaoedcrScrutinyInterface;

	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@Autowired
	private Environment env;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}
	
	public List<EdcrScrutiny> fetch_usercd(String usercd) {
		List<EdcrScrutiny> resp = null;
		resp = DaoedcrScrutinyInterface.fetchEdcr_usercd(usercd);

		return resp;
	}

	public EdcrScrutiny fetch(String edcrnumber) {

		EdcrScrutiny resp = DaoedcrScrutinyInterface.fetchEdcr(edcrnumber);
		return resp;
	}

	public JSONObject Scrutinize(MultipartFile planFile, String usercode, String OfficeCode, String stateid,
			String tenantid) {
		String resp = null;
		JSONObject respJson = null;
		byte[] binaryPlanReport=null;
		byte[] binaryDxfFile=null;
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		JSONObject userInfo = new JSONObject();
		userInfo.put("uuid", "1c79f77e-e847-4663-98a7-5aee31f185c5");
		userInfo.put("tenantId", "0003");
		userInfo.put("id", "12345");
		JSONObject RequestInfo = new JSONObject();
		RequestInfo.put("userInfo", userInfo);
		JSONObject edcrRequest = new JSONObject();
		edcrRequest.put("transactionNumber", uuid);
		edcrRequest.put("applicationSubType", "NEW_CONSTRUCTION");
		edcrRequest.put("appliactionType", "BUILDING_PLAN_SCRUTINY");
		
		Userlogin user=(Userlogin)session().getAttribute("user");
		edcrRequest.put("applicantName", user.getFullname());
		
		edcrRequest.put("tenantId", stateid + "." + tenantid);
		edcrRequest.put("RequestInfo", RequestInfo);
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
//			System.out.println("planfile.getBytes()::" + valueMap.get("planFile"));
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(valueMap, headers);
			String serverUrl = env.getProperty("edcr.scrutitny.url").trim() + stateid.trim() + "." + tenantid.trim();
			RestTemplate restTemplate = new RestTemplate();
			System.out.println(serverUrl);
			resp = restTemplate.postForObject(serverUrl, valueMap, String.class);
			// --------------save to db------------
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(resp);
			String edcrnumber = null;
			if (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("edcrNumber") == null) {
				String Applno = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("applicationNumber"))
						.toString();
				edcrnumber = "DCR" + Applno;
			} else {
				edcrnumber = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("edcrNumber")).toString();
			}
			String status = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("status")).toString();
			String planReport = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("planReport")).toString();
			if (planReport != null || planReport != "") {
				URL url = new URL(planReport);
				binaryPlanReport=readfile(url);
			 
			}
			String dxfFile = (((List<Map<String, Object>>) json.get("edcrDetail")).get(0).get("dxfFile")).toString();
			System.out.println(dxfFile);
			if (dxfFile != null || dxfFile != "") {
				URL url = new URL(dxfFile);
				binaryDxfFile=readfile(url);
			 
			}
			String edcrdetails = (new ObjectMapper())
					.writeValueAsString(((List<Map<String, Object>>) json.get("edcrDetail")).get(0));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usercode", usercode);
			map.put("useroffice", OfficeCode);
			map.put("edcrnumber", edcrnumber);
			map.put("status", status);
			map.put("planreport", binaryPlanReport);
			map.put("dxffile", binaryDxfFile);
			map.put("response", edcrdetails);
			boolean doaresp = DaoedcrScrutinyInterface.createEdcrScrutiny(map);
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
			System.out.println("error at scrutinize---" + e.getMessage());
			respJson = new JSONObject();
			respJson.put("status", "error");
			respJson.put("edcrnumber", "NA");
			respJson.put("planReport", "NA");
			respJson.put("msg", "System Error: Please contact System Admin!");
		}

		return respJson;
	}

	private String StringGenerator() {
		final String uuid = UUID.randomUUID().toString().replace("-", "");

		uuid.substring(9);
		return uuid;
	}

	private byte[] readfile(URL url) throws IOException {
		System.out.println("readfile------------");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		byte[] binaryFile = null;
		try {
			is = url.openStream();
			byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
			int n;

			while ((n = is.read(byteChunk)) > 0) {
				baos.write(byteChunk, 0, n);
			}
			binaryFile = baos.toByteArray();
			System.out.println("binaryFile-------"+binaryFile);
		} catch (IOException e) {
			System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
			e.printStackTrace();
			// Perform any other exception handling that's appropriate.
		} finally {
			if (is != null) {
				is.close();
			}
		}
//		return (binaryFile != null) ? Base64.getEncoder().encodeToString(binaryFile) : null;
		return binaryFile;
	}
}
