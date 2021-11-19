package com.liferay.webhooks.sender;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Component(service = WebhookSender.class)
public class WebhookSender {
	public void sendEventInformation(Object o) {
		byte[] out = JSONFactoryUtil.serialize(o).getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		try {
			URL url = new URL("https://webhook.site/d7165e7d-8455-401d-8f84-dc8fc469be8f");
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
				os.write(out);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}


//		Http.Options options = new Http.Options();
//
//		options.addHeader(
//			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_TEXT);
//		options.addHeader(
//			"x-api-key", "");
//		options.setBody(
//			o.toString(), ContentTypes.APPLICATION_JSON,
//			StringPool.UTF8);
//		options.setLocation("https://webhook.site/d7165e7d-8455-401d-8f84-dc8fc469be8f");
//		options.setPost(true);
//
//		try {
//			_http.URLtoString(options);
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Reference
	private Http _http;
}
