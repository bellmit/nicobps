package obps.services.payment;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceBilldeskGateway {

	private final String MERCHANT_URL_PAY;
	private final String MERCHANT_ID;
	private final String SECURE_SECRET;
	private final String CHECK_SUM_PWD;
	private final String LOCALE;
	private final String CURRENCY;
	private final String RETURN_URL;
	private final Environment environment;

	@Autowired
	public ServiceBilldeskGateway(Environment environment) {

		this.environment = environment;
		CURRENCY = environment.getRequiredProperty("billdesk.currency");
		LOCALE = environment.getRequiredProperty("billdesk.locale");
		MERCHANT_ID = environment.getRequiredProperty("billdesk.merchant.id");
		SECURE_SECRET = environment.getRequiredProperty("billdesk.merchant.secret.key");
		CHECK_SUM_PWD = environment.getRequiredProperty("billdesk.merchant.pwd");
		MERCHANT_URL_PAY = environment.getRequiredProperty("billdesk.url.debit");
		RETURN_URL = environment.getRequiredProperty("billdesk.url.return");
	}

	public URI generateRedirectURI() {
		System.out.println("Generate URI");
		String hashSequence = "MerchantId|CustomerId|NA|Amount|NA|NA|NA|Currency|NA|R|SecureSecret|NA|NA|F|TenantId|NA|NA|NA|5|NA|NA|ReturnUrl";
		hashSequence = hashSequence.replace("MerchantId", MERCHANT_ID);
		hashSequence = hashSequence.replace("CustomerId", "TestCustomerID");
		hashSequence = hashSequence.replace("TenantId", "TestTenantID");
		hashSequence = hashSequence.replace("Amount", "1");
		hashSequence = hashSequence.replace("Currency", CURRENCY);
		hashSequence = hashSequence.replace("SecureSecret", SECURE_SECRET);
		hashSequence = hashSequence.replace("ReturnUrl", RETURN_URL);
		String hash = HmacSHA256(hashSequence, CHECK_SUM_PWD);
		hashSequence = hashSequence + "|" + hash;
		System.out.println("hashSequence=======================" + hashSequence);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("msg", hashSequence);
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(MERCHANT_URL_PAY).build();
		URI redirectUri = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
			redirectUri = restTemplate.postForLocation(uriComponents.toUriString(), entity);

			if (redirectUri.equals(null))
				System.out.println("BILLDESK_REDIRECT_URI_GEN_FAILED , Failed to generate redirect URI");
			else
				System.out.println("redirectUri-----------" + redirectUri);

		} catch (RestClientException e) {

			System.out.println("Unable to retrieve redirect URI from gateway" + e);

		}
		return redirectUri;
	}

	public static String HmacSHA256(String message, String secret) {
		try {

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);

			byte raw[] = sha256_HMAC.doFinal(message.getBytes());

			StringBuffer ls_sb = new StringBuffer();
			for (int i = 0; i < raw.length; i++)
				ls_sb.append(char2hex(raw[i]));
			return ls_sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String char2hex(byte x)

	{
		char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
		return (new String(c));
	}

	public void setProxy() {
		try {
			if (Boolean.valueOf(environment.getRequiredProperty("proxy.set"))) {
				System.out.println("------------------SET PROXY URL-----------------------------------------");
				HttpClientBuilder clientBuilder = HttpClientBuilder.create();
				clientBuilder.useSystemProperties();

				clientBuilder.setProxy(new HttpHost(environment.getRequiredProperty("proxy.url"),
						Integer.valueOf(environment.getRequiredProperty("proxy.port"))));
				CloseableHttpClient client = clientBuilder.build();
				HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
				factory.setHttpClient(client);
//				restTemplate.setRequestFactory(factory);// need to fix this
			}
		} catch (Exception e) {
			System.out.println("Proxy Set Error--------" + e);
			e.printStackTrace();
		}

	}

	public void unSetProxy() {

		try {
			if (Boolean.valueOf(environment.getRequiredProperty("proxy.set"))) {
				System.out.println("------------------UNSET PROXY URL-----------------------------------------");
				HttpClientBuilder clientBuilder = HttpClientBuilder.create();
				clientBuilder.useSystemProperties();
				CloseableHttpClient client = clientBuilder.build();
				HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
				factory.setHttpClient(client);
//				restTemplate.setRequestFactory(factory); //need to fix this
			}
		} catch (Exception e) {
			System.out.println("Proxy unset Error--------" + e);
			e.printStackTrace();
		}
	}
}
