package com.ocr.test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *营业执照识别WebAPI接口调用示例接口文档(必看)：https://doc.xfyun.cn/rest_api/%E8%90%A5%E4%B8%9A%E6%89%A7%E7%85%A7%E8%AF%86%E5%88%AB.html
 *图片属性：仅支持jpg格式,建议最短边大于1200像素，图像质量75以上，位深度24,编码后大小不超过4M
 *webapi OCR服务参考帖子(必看)：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=39111&highlight=OCR
 *(Very Important)创建完webapi应用添加服务之后一定要设置ip白名单，找到控制台--我的应用--设置ip白名单，如何设置参考：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=41891
 *错误码链接：https://www.xfyun.cn/document/error-code (code返回错误码时必看)400开头错误码请在接口文档底部查看
 *@author iflytek
*/
public class BusinessLicense {
	    // OCR webapi 接口地址
		private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/business_license";
		// 应用APPID(必须为webapi类型应用,并开通营业执照识别服务,参考帖子如何创建一个webapi应用：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=36481)
		private static final String APPID = "5df8cb4b";
		// 接口密钥(webapi类型应用开通营业执照识别服务后,控制台--我的应用---营业执照识别---相应服务的apikey)
		private static final String API_KEY = "152011958af32f299c8561b543a56dcb";
		// 引擎类型
		private static final String ENGINE_TYPE = "business_license";	
		// 图片地址
		private static final String AUDIO_PATH = "E://1.jpg";
		/**
		 * OCR WebAPI 调用示例程序
		 * 
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
			return dataFormat(result);
		}

		/**
		 * 组装http请求头
		 */
		private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
			String curTime = System.currentTimeMillis() / 1000L + "";
			String param = "{\"engine_type\":\"" + ENGINE_TYPE + "\"}";
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


		private static String dataFormat(String result) {
			JSONObject resultBody = JSONObject.parseObject(result);
			String code = resultBody.getString("code");
			StringBuilder sb = new StringBuilder();
			if (!"0".equals(resultBody.getString("code"))) {
				return resultBody.getString("desc");
			}
			JSONObject data = resultBody.getJSONObject("data");

			Map<String, String> keyNameMap = keyNameMap();
			for (Map.Entry<String, String> item : keyNameMap.entrySet()) {
				String key = item.getKey();
				String name = item.getValue();
				String value = data.getString(key);
				sb.append(name);
				sb.append(":");
				sb.append(value);
				sb.append("\n");
			}

			return sb.toString();
		}

		private static Map<String, String> keyNameMap() {
			String[] lines = keyNameStr.split("\n");
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				String[] words = line.split("\\s");
				if (words.length >= 2) {
					map.put(words[0], words[1]);
				}
			}
			return map;
		}

		private static String keyNameStr = "type\t营业执照\n" +
				"biz_license_company_name\t名称\n" +
				"biz_license_company_type\t类型/公司类型/主体类型\n" +
				"biz_license_address\t住所/经营场所/主要经营场所/营业场所\n" +
				"biz_license_registration_code\t注册号\n" +
				"biz_license_serial_number\t证照编号\n" +
				"biz_license_owner_name\t法定代表人/负责人/经营者/经营者姓名\n" +
				"biz_license_reg_capital\t注册资本\n" +
				"biz_license_paidin_capital\t实收资本\n" +
				"biz_license_scope\t经营范围\n" +
				"biz_license_start_time\t成立日期/注册日期\n" +
				"biz_license_composing_form\t组成形式\n" +
				"biz_license_operating_period\t营业期限\n" +
				"biz_license_credit_code\t统一社会信用代码";
}
