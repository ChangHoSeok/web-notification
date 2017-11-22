
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
		String endpoint = "https://fcm.googleapis.com/fcm/send/f1Rtx6O3DSo:APA91bGNJgaFuPMOhkkGFiECpHVIT6TPB6UzJFbj7EHwkc1yF4OCSRtM8V-K-NQws3n5ZaQBTygyN2S6_Dpc8w2m3O7i450Y3guecRJt0FSe8-upYspHegW-ww08uQwmgBiMGsEogon3";

		// Base64 string user public key/auth
		String userPublicKey = "BJyGpb3EGx8IyX5N432wC-khtD0RmgKyymmGnj_QcpRalE5EWrpHUQaxrdbVVedOi9ZVyknrFMJuvOQc0WnAmds=";
		String userAuth = "aVgCs_xkjt25BykouwmxHA==";

		// Base64 string server public/private key
		String vapidPublicKey = "BEftHISxkON7u1gBJal3Za2KgyddHhllYuG3y9iqnBv4I-Lv4rKqlnUrOcpTKH3c2_7_tPobmvaOFr0X3XrbB9A=";
		String vapidPrivateKey = "ALvAkVHcFNkI-R1znx5bbGGk7mLbKjjxOFVFpnIzexzM";

		// Construct notification
		Notification notification = new Notification(endpoint, userPublicKey, userAuth, getPayload());

		// Construct push service
		PushService pushService = new PushService();
		pushService.setSubject("mailto:courage@courage.pe.kr");
		pushService.setPublicKey(Utils.loadPublicKey(vapidPublicKey));
		pushService.setPrivateKey(Utils.loadPrivateKey(vapidPrivateKey));

		// Send notification!
		HttpResponse httpResponse = pushService.send(notification);

		System.out.println(httpResponse.getStatusLine().getStatusCode());
		System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));

		assertEquals(httpResponse.getStatusLine().getStatusCode(), "201");
	}

	private byte[] getPayload() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("title", "Hello");
		jsonObject.addProperty("message", "World");
		jsonObject.addProperty("link", "https://tech.courage.pe.kr");

		return jsonObject.toString().getBytes();
	}
}
