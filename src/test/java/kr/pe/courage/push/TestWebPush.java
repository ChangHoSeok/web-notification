
package kr.pe.courage.push;

import java.nio.charset.StandardCharsets;
import java.security.Security;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import junit.framework.TestCase;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;

public class TestWebPush extends TestCase {

	@Before
	protected void setUp() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	@Test
	public void testPushChromeVapid() throws Exception {
		String endpoint = "https://fcm.googleapis.com/fcm/send/dNHeVyGENsY:APA91bG0Lh0CjWAXW1lFlQnBtcmv16Ay1EclEwebEyTzX24gAvI-543jCprHkI7gmMqJGzw22SgbMQcvlpDO8spKMbRddcByg46d2FRI-Ozz6SRJ6X8M-qcehckhMVHmnhvcfXrU5Fa_";

		// Base64 string user public key/auth
		String userPublicKey = "BMpM_94CJ6vWWBd-rn0Ib0XN4ck9cS93Ksqt1GO9PoYHCn2u20YJ-v2q2CCYwBxOapwP7gcWsQRgt9tMoG1kbvg=";
		String userAuth = "XsLGSatUKg0J0Waf9D1_2g==";

		// Base64 string server public/private key
		String vapidPublicKey = "BL4xu-6iTVE-ZyaFxdFnHb7_bG3Py6HYeBcSthB8TWy3764_Ig3aewiiAvXCBUHOIHTWkUchfx5m1i4-8gQGRCA";
		String vapidPrivateKey = "gsV2vaSHizyMLXvmEF0vq4FuovwdlAQ8BsFsY_QX03k";

		// Construct notification
		Notification notification = new Notification(endpoint, userPublicKey, userAuth, getPayload());

		// Construct push service
		PushService pushService = new PushService();
//		pushService.setGcmApiKey("AIzaSyDH_-u8Bxa5FPTX-y_gDux-x5KDYwL2xzw");
		pushService.setSubject("mailto:courage@courage.pe.kr");
		pushService.setPublicKey(Utils.loadPublicKey(vapidPublicKey));
		pushService.setPrivateKey(Utils.loadPrivateKey(vapidPrivateKey));

		// Send notification!
		HttpResponse httpResponse = pushService.send(notification);
		
		System.out.println(httpResponse.getStatusLine().getStatusCode());
		System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
		
		assertEquals(httpResponse.getStatusLine().getStatusCode(), "200");
	}

//	@Test
//	public void testPushChrome() throws Exception {
//		String endpoint = "https://fcm.googleapis.com/fcm/send/esSV0f2aScU:APA91bGZzo81X0FCpK2-UiJPLks5D4XY_wwZKDVJYW87NAWxyC3FWBdhDmo4TPajOiFz4rV0zW2qPKk4LKvcbVlCLpLt2q-i2h3btnWA2B3VeVwSY6NRGlD36IdU_31L1G9cfoQ8kPHI";
//
//		// Base64 string user public key/auth
//		String userPublicKey = "BIJ8aTrtA9OW7CY-AqQbDILB4FmjKVs9gabdjOICyruRm1-LhlG11wEIfBZWkj8LEWei2z6DSCXaxjHcLMhgWGg=";
//		String userAuth = "Sd7Fe1Ib4jQHXjtuBkxcLw==";
//
//		// Construct notification
//		Notification notification = new Notification(endpoint, userPublicKey, userAuth, getPayload());
//
//		// Construct push service
//		PushService pushService = new PushService();
//		pushService.setGcmApiKey("AAAA3foNDwA:APA91bH67ntKCPVkz5J21wK5AsyHmCYY2zrnCGQN58Trz82HspmmxrpZ6ldvdzEl1_RB6EYvYI8u8ohsYFZ7VYqmjHXbXGIit9mRuXiiY1U3QRGqCL6E41-aDXXmSGnowjPuShWkFR3J");
//
//		// Send notification!
//		HttpResponse httpResponse = pushService.send(notification);
//
//		System.out.println(httpResponse.getStatusLine().getStatusCode());
//		System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
//	}

	private byte[] getPayload() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("title", "Hello");
		jsonObject.addProperty("message", "World");

		return jsonObject.toString().getBytes();
	}
}
