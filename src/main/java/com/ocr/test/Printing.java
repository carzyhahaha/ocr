package com.ocr.test;
/**
 * 印刷文字识别WebAPI接口调用示例接口文档(必看)：https://doc.xfyun.cn/rest_api/%E5%8D%B0%E5%88%B7%E6%96%87%E5%AD%97%E8%AF%86%E5%88%AB.html
 * 上传图片base64编码后进行urlencode要求base64编码和urlencode后大小不超过4M最短边至少15px，最长边最大4096px支持jpg/png/bmp格式
 * (Very Important)创建完webapi应用添加合成服务之后一定要设置ip白名单，找到控制台--我的应用--设置ip白名单，如何设置参考：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=41891
 * 错误码链接：https://www.xfyun.cn/document/error-code (code返回错误码时必看)
 * @author iflytek
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
public class Printing {
	// OCR webapi 接口地址
	private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/general";
	// 应用ID (必须为webapi类型应用，并印刷文字识别服务，参考帖子如何创建一个webapi应用：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=36481)
	private static final String APPID = "5df8cb4b";
	// 接口密钥(webapi类型应用开通印刷文字识别服务后，控制台--我的应用---印刷文字识别---服务的apikey)
	private static final String API_KEY = "152011958af32f299c8561b543a56dcb";
	// 是否返回位置信息
	private static final String LOCATION = "true";
	// 语种(可选值：en（英文），cn|en（中文或中英混合)
	private static final String LANGUAGE = "cn|en";
	// 图片地址,图片最短边至少15px，最长边最大4096px，格式jpg、png、bmp
	private static final String PIC_PATH = "C:\\Users\\Zzz\\Desktop\\timg (3).jpg";

	/**
	 * OCR WebAPI 调用示例程序
	 * 
	 * @throws IOException
	 */
	public static String start(String filePath) {
		StringBuilder stringBuilder = null;
		try {
			Map<String, String> header = buildHttpHeader();
			byte[] imageByteArray = FileUtil.read(filePath);
			String imageBase64 = new String(Base64.encodeBase64(imageByteArray), "UTF-8");
			String result = HttpUtil.doPost1(WEBOCR_URL, header, "image=" + URLEncoder.encode(imageBase64, "UTF-8"));
			JSONObject resultBody = JSONObject.parseObject(result);
			if (!"0".equals(resultBody.getString("code"))) {
				return resultBody.getString("desc");
			}
			JSONObject data = resultBody.getJSONObject("data");
			JSONArray textArray = data.getJSONArray("block");
			stringBuilder = new StringBuilder();
			for (int i = 0; i < textArray.size(); i++) {
				JSONObject item = textArray.getJSONObject(i);
				JSONArray textLines =  item.getJSONArray("line");
				for (int i1 = 0; i1 < textLines.size(); i1++) {
					JSONObject line = textLines.getJSONObject(i1);
					JSONArray words = line.getJSONArray("word");
					for (int i2 = 0; i2 < words.size(); i2++) {
						JSONObject word = words.getJSONObject(i2);
						stringBuilder.append(word.get("content"));
						if (isEnglish(word.getString("content"))) {
							stringBuilder.append(" ");
						}
					}
					stringBuilder.append("\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * 组装http请求头
	 */
	private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
		String curTime = System.currentTimeMillis() / 1000L + "";
		String param = "{\"location\":\"" + LOCATION + "\",\"language\":\"" + LANGUAGE + "\"}";
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

	private static boolean isEnglish(String string) {
		for (int i=0; i<string.length(); i++) {
			char thisChar = string.charAt(i);
			if( (thisChar >= 'a' && thisChar <= 'z') || (thisChar >= 'A' && thisChar <= 'Z') ) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}