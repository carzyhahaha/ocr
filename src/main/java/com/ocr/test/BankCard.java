package com.ocr.test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.time.chrono.IsoChronology;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
/**银行卡识别WebAPI接口调用示例接口文档(必看)：https://doc.xfyun.cn/rest_api/%E9%93%B6%E8%A1%8C%E5%8D%A1%E8%AF%86%E5%88%AB.html
  *图片属性：jpg/jpeg，尺寸1024×768，图像质量75以上，位深度24。建议最短边最小不低于160像素，最大不超过4000像素,编码后大小不超过4M
  *webapi OCR服务参考帖子(必看)：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=39111&highlight=OCR
  *(Very Important)创建完webapi应用添加服务之后一定要设置ip白名单，找到控制台--我的应用--设置ip白名单，如何设置参考：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=41891
  *错误码链接：https://www.xfyun.cn/document/error-code (code返回错误码时必看)
  *@author iflytek
*/
public class BankCard {
	    // OCR银行卡识别 webapi 接口地址
		private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/bankcard";
		// 应用APPID(必须为webapi类型应用，并开通银行卡识别服务，参考帖子如何创建一个webapi应用：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=36481)
		private static final String APPID = "5df8cb4b";
		// 接口密钥(webapi类型应用开通银行卡识别服务后,控制台--我的应用---银行卡识别---相应服务的apikey)
		private static final String API_KEY = "152011958af32f299c8561b543a56dcb";
		// 引擎类型
		private static final String ENGINE_TYPE = "bankcard";
		// 是否返回号码区域图片 0否 1是
		private static final String CARD_NUMBER_IMAGE = "0";		
		// 图片地址，图片格式jpg/jpeg尺寸大小1024*768
		private static final String AUDIO_PATH = "";
		/**
		 * OCR WebAPI 调用示例程序
		 * 
		 * @throws IOException
		 */
		public static String start(String filePath) {
			String result = null;
			try {
				Map<String, String> header = buildHttpHeader();
				byte[] imageByteArray = FileUtil.read(filePath);
				String imageBase64 = new String(Base64.encodeBase64(imageByteArray), "UTF-8");
				result = HttpUtil.doPost1(WEBOCR_URL, header, "image=" + URLEncoder.encode(imageBase64, "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return dateFormat(result);
		}

		/**
		 * 组装http请求头
		 */
		private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
			String curTime = System.currentTimeMillis() / 1000L + "";
			String param = "{\"engine_type\":\"" + ENGINE_TYPE + "\",\"card_number_image\":\"" + CARD_NUMBER_IMAGE + "\"}";
			String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
			String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
			Map<String, String> header = new HashMap<String, String>();
			header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			header.put("X-Param", paramBase64);
			header.put("X-CurTime", curTime);
			header.put("X-CheckSum", checkSum);
			header.put("X-Appid", APPID);
			return header;
		}


	private static String dateFormat(String result) {
		JSONObject resultBody = JSONObject.parseObject(result);
		String code = resultBody.getString("code");
		if (!"0".equals(resultBody.getString("code"))) {
			return resultBody.getString("desc");
		}
		JSONObject data = resultBody.getJSONObject("data");
		Map<String, String> keyNameMap = keyNameMap();
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> item : keyNameMap.entrySet()) {
			String value = data.getString(item.getKey());
			String name = item.getValue();
			stringBuilder.append(name);
			stringBuilder.append(":");
			stringBuilder.append(value);
			stringBuilder.append("\n");
		}

		return stringBuilder.toString();
	}


	private static Map<String, String> keyNameMap() {
		String[] lines = keyName.split("\n");
		Map<String, String> keyNameMap = new HashMap<>();
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] words = line.split("\\s");
			if (words.length >= 2) {
				keyNameMap.put(words[0], words[1]);
			}
		}
		return keyNameMap;
	}

	private static final String keyName = "type\t银行卡类型\n" +
			"card_number\t银行卡号\n" +
			"validate\t有效期\n" +
			"holder_name\t持卡人\n" +
			"issuer\t发卡机构\n" +
			"card_number_image\t卡号区域截图";
}
