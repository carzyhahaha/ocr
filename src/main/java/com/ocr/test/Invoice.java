package com.ocr.test;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.alibaba.fastjson.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class Invoice {
	  // OCR webapi 接口地址
		private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/invoice";
		// 应用ID
		private static final String APPID = "5df8cb4b";
		// 接口密钥
		private static final String API_KEY = "152011958af32f299c8561b543a56dcb";
		// 引擎类型
		private static final String ENGINE_TYPE = "invoice";
		
		
		// 图片地址
		private static final String AUDIO_PATH = "C:\\Users\\Zzz\\Desktop\\timg.jpg";
		/**
		 * OCR WebAPI 调用示例程序
		 * 
		 * @param args
		 * @throws IOException
		 */
		public static String start(String filePath) {
			String result = null;
			try {
				Map<String, String> header = buildHttpHeader();
				byte[] imageByteArray = FileUtil.read(filePath);
				String imageBase64 = new String(Base64.encodeBase64(imageByteArray), "UTF-8");
			     result = HttpUtil.doPost1(WEBOCR_URL, header, "image=" + URLEncoder.encode(imageBase64, "UTF-8"));
//
//			String result = dataFormat("{\"code\":\"0\",\"data\":{\"angle\":0,\"error_code\":0,\"error_msg\":\"OK\",\"time_cost\":{\"preprocess\":10,\"recognize\":1190},\"type\":\"增值税发票\",\"vat_invoice_cipher_field\":\"1*6\\u003c+81+574+9*2+/\\u003e0-39063/*-*0/968*624582\\u003e91*3/*1\\u003c6*422477\\u003c/42**0+7-2\\u003c/24-630-391611+\\u003e/51433*124*5-+44\\u003e\\u003c9/94\",\"vat_invoice_cipher_field_pos\":{\"height\":68,\"left\":537,\"top\":121,\"width\":270},\"vat_invoice_correct_code\":\"\",\"vat_invoice_corrent_code_print\":\"\",\"vat_invoice_dai_kai_flag\":\"否\",\"vat_invoice_daima\":\"1100094140\",\"vat_invoice_daima_pos\":{\"height\":19,\"left\":162,\"top\":41,\"width\":125},\"vat_invoice_daima_print\":\"1100094140\",\"vat_invoice_daima_print_pos\":{\"height\":19,\"left\":162,\"top\":41,\"width\":125},\"vat_invoice_daima_right_side\":\"1100094140\",\"vat_invoice_daima_right_side_pos\":{\"height\":11,\"left\":766,\"top\":50,\"width\":65},\"vat_invoice_drawer\":\"开票人\",\"vat_invoice_drawer_pos\":{\"height\":19,\"left\":463,\"top\":472,\"width\":115},\"vat_invoice_electrans_quantity\":\"1\",\"vat_invoice_electrans_quantity_pos\":{\"height\":11,\"left\":477,\"top\":215,\"width\":18},\"vat_invoice_electrans_unit\":\"台\",\"vat_invoice_electrans_unit_pos\":{\"height\":17,\"left\":369,\"top\":212,\"width\":30},\"vat_invoice_electrans_unit_price\":\"5999.00\",\"vat_invoice_electrans_unit_price_pos\":{\"height\":10,\"left\":520,\"top\":216,\"width\":47},\"vat_invoice_goods_list\":\"计算机\",\"vat_invoice_goods_list_pos\":{\"height\":13,\"left\":82,\"top\":214,\"width\":43},\"vat_invoice_haoma\":\"87654321\",\"vat_invoice_haoma_large_size\":\"87654321\",\"vat_invoice_haoma_large_size_pos\":{\"height\":19,\"left\":641,\"top\":41,\"width\":112},\"vat_invoice_haoma_pos\":{\"height\":11,\"left\":753,\"top\":67,\"width\":81},\"vat_invoice_haoma_right_side\":\"87654321\",\"vat_invoice_haoma_right_side_pos\":{\"height\":10,\"left\":753,\"top\":67,\"width\":75},\"vat_invoice_headline_page_number\":\"抵扣联\",\"vat_invoice_headline_page_number_pos\":{\"height\":17,\"left\":378,\"top\":82,\"width\":159},\"vat_invoice_issue_date\":\"2010年11月18日\",\"vat_invoice_issue_date_pos\":{\"height\":16,\"left\":669,\"top\":87,\"width\":142},\"vat_invoice_issue_date_print\":\"2010年11月18日\",\"vat_invoice_issue_date_print_pos\":{\"height\":16,\"left\":669,\"top\":87,\"width\":142},\"vat_invoice_note_correct_code\":\"\",\"vat_invoice_page_number\":\"抵扣联\",\"vat_invoice_page_number_pos\":{\"height\":17,\"left\":378,\"top\":82,\"width\":159},\"vat_invoice_payee\":\"收款人\",\"vat_invoice_payee_pos\":{\"height\":22,\"left\":91,\"top\":470,\"width\":111},\"vat_invoice_payer_addr_tell\":\"北京市海淀区知春路60号 81234567\",\"vat_invoice_payer_addr_tell_pos\":{\"height\":14,\"left\":199,\"top\":157,\"width\":188},\"vat_invoice_payer_bank_account\":\"建行-0000123456789\",\"vat_invoice_payer_bank_account_pos\":{\"height\":13,\"left\":198,\"top\":179,\"width\":115},\"vat_invoice_payer_name\":\"测试购方企业\",\"vat_invoice_payer_name_pos\":{\"height\":14,\"left\":200,\"top\":118,\"width\":80},\"vat_invoice_plate_specific\":\"A6100\",\"vat_invoice_plate_specific_pos\":{\"height\":10,\"left\":279,\"top\":216,\"width\":31},\"vat_invoice_price_list\":\"5999.00\",\"vat_invoice_price_list_pos\":{\"height\":10,\"left\":629,\"top\":216,\"width\":48},\"vat_invoice_rate_payer_id\":\"410305123456789\",\"vat_invoice_rate_payer_id_pos\":{\"height\":13,\"left\":197,\"top\":138,\"width\":162},\"vat_invoice_review\":\"复核人\",\"vat_invoice_review_pos\":{\"height\":17,\"left\":303,\"top\":474,\"width\":98},\"vat_invoice_seller_addr_tell\":\"北京市海淀区知春路61号68744498\",\"vat_invoice_seller_addr_tell_pos\":{\"height\":14,\"left\":198,\"top\":439,\"width\":189},\"vat_invoice_seller_bank_account\":\"工行123455668-234222256111\",\"vat_invoice_seller_bank_account_pos\":{\"height\":14,\"left\":199,\"top\":454,\"width\":169},\"vat_invoice_seller_id\":\"410305012345678\",\"vat_invoice_seller_id_pos\":{\"height\":13,\"left\":216,\"top\":421,\"width\":152},\"vat_invoice_seller_name\":\"测试销方企业\",\"vat_invoice_seller_name_pos\":{\"height\":13,\"left\":197,\"top\":399,\"width\":82},\"vat_invoice_tax_list\":\"1019.83\",\"vat_invoice_tax_list_pos\":{\"height\":13,\"left\":781,\"top\":213,\"width\":48},\"vat_invoice_tax_rate\":\"17%\",\"vat_invoice_tax_rate_list\":\"17%\",\"vat_invoice_tax_rate_list_pos\":{\"height\":10,\"left\":690,\"top\":216,\"width\":24},\"vat_invoice_tax_rate_pos\":{\"height\":10,\"left\":690,\"top\":216,\"width\":24},\"vat_invoice_tax_total\":\"¥1019.83\",\"vat_invoice_tax_total_pos\":{\"height\":14,\"left\":751,\"top\":346,\"width\":74},\"vat_invoice_total\":\"¥5999.00\",\"vat_invoice_total_cover_tax\":\"柒仟零壹拾捌圆捌角叁分\",\"vat_invoice_total_cover_tax_digits\":\"¥7018.83\",\"vat_invoice_total_cover_tax_digits_pos\":{\"height\":14,\"left\":671,\"top\":374,\"width\":79},\"vat_invoice_total_cover_tax_pos\":{\"height\":14,\"left\":312,\"top\":371,\"width\":133},\"vat_invoice_total_note\":\"备注信息\",\"vat_invoice_total_note_pos\":{\"height\":12,\"left\":536,\"top\":401,\"width\":50},\"vat_invoice_total_pos\":{\"height\":12,\"left\":601,\"top\":347,\"width\":74},\"vat_invoice_total_print\":\"¥5999.00\",\"vat_invoice_total_print_pos\":{\"height\":12,\"left\":601,\"top\":347,\"width\":74},\"vat_invoice_type\":\"非电子发票\",\"vat_invoice_zhuan_yong_flag\":\"专票\"},\"desc\":\"success\",\"sid\":\"wcr00344b02@gz15fa1154e041460e00\"}");
				result = dataFormat(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
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
			if (!"0".equals(resultBody.getString("code"))) {
				return resultBody.getString("desc");
			}
			JSONObject dataJson = resultBody.getJSONObject("data");
			StringBuilder sb = new StringBuilder();
			Map<String,String> map = keyNameMap();
			Map<JSONObject, String> posSort = new TreeMap<JSONObject, String>((o1, o2) -> {
				Integer top1 = o1.getInteger("top");
				Integer top2 = o2.getInteger("top");
				Integer left1 = o1.getInteger("left");
				Integer left2 = o2.getInteger("left");

				if (top1 == top2) {
					return left1 - left2;
				}
				return top1 - top2;
			});

			for (Map.Entry<String, Object> item : dataJson.entrySet()) {
				if (item.getKey().endsWith("_pos")) {
					JSONObject value = (JSONObject) item.getValue();
					if (value.getInteger("top") != null) {
						posSort.put(value, item.getKey());
					}
				}
			}

			Collection<String> keySet = posSort.values();
			for (Map.Entry<String, Object> item : dataJson.entrySet()) {
				String key = item.getKey();
				if (!key.endsWith("_pos") && !keySet.contains(key +"_pos")) {
					String name = map.get(key);
					String value = item.getValue().toString();

					if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
						sb.append((name == null ? item.getKey() : name) + ":" + value);
						sb.append("\n");
					}
				}
			}
			Integer preLeft = 0;
			Integer preTop = 0;
			for (Map.Entry<JSONObject, String> item : posSort.entrySet()) {
				Integer thisLeft = item.getKey().getInteger("left");
				if (preLeft == 0) {
					preLeft = thisLeft;
				} else {
					if (thisLeft < preLeft) {
						sb.append("\n");
					}
					preLeft = thisLeft;
				}
				String key = item.getValue();
				key = key.substring(0, key.length()-4);
				String name = map.get(key);
				String value = dataJson.getString(key);
				if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
					sb.append((name == null ? key : name) + ":" + value);
					sb.append("\t");
				}
			}

			return sb.toString();
		}

		private static Map<String, String> keyNameMap() {
			String[] docLines = invoiceKeyName.split("\n");
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (int i = 0; i < docLines.length; i++) {
				String line = docLines[i];
				String[] word = line.split("\\s");
				if (word.length >= 2) {
					map.put(word[0], word[1]);
				}
			}
			return map;
		}

		private static String invoiceKeyName = "vat_invoice_correct_code 校验码\n" +
				"vat_invoice_daima 发票代码\n" +
				"vat_invoice_haoma 发票号码\n" +
				"vat_invoice_issue_date 开票日期\n" +
				"vat_invoice_rate_payer_id 纳税人识别号\n" +
				"vat_invoice_total 合计\n" +
				"vat_invoice_tax_rate 税率\n" +
				"vat_invoice_jida_haoma 机打号码\n" +
				"vat_invoice_seller_name 销售方名称\n" +
				"vat_invoice_seller_bank_account 销售方开户行及帐号\n" +
				"vat_invoice_seller_id 销售方纳税人识别号\n" +
				"vat_invoice_seller_addr_tell 销售方地址电话\n" +
				"vat_invoice_payer_name 购买方名称\n" +
				"vat_invoice_payer_bank_account 购买方开户行及账号\n" +
				"vat_invoice_payer_addr_tell 购买方地址电话\n" +
				"vat_invoice_total_cover_tax 价税合计大写\n" +
				"vat_invoice_total_cover_tax_digits 价税合计小写\n" +
				"vat_invoice_electrans_unit_price 单价\n" +
				"vat_invoice_electrans_quantity 数量\n" +
				"vat_invoice_tax_total 税额合计\n" +
				"vat_invoice_goods_list 货物或服务名称\n" +
				"vat_invoice_price_list 金额明细\n" +
				"vat_invoice_tax_rate_list 税率明细\n" +
				"vat_invoice_tax_list 税额明细\n" +
				"vat_invoice_zhuan_yong_flag 专票/普票\n" +
				"vat_invoice_dai_kai_flag 代开\n" +
				"vat_invoice_note 备注\n" +
				"vat_invoice_daima_right_side 右侧打印发票代码\n" +
				"vat_invoice_total_print 合计金额\n" +
				"vat_invoice_payee 收款人\n" +
				"vat_invoice_electrans_unit 单位\n" +
				"vat_invoice_type 发票类型\n" +
				"vat_invoice_haoma_large_size 大尺寸发票号码\n" +
				"vat_invoice_daima_print 发票代码\n" +
				"vat_invoice_headline_page_number 标题页号\n" +
				"vat_invoice_issue_date_print 开票日期\n" +
				"vat_invoice_cipher_field 密码区\n" +
				"vat_invoice_page_number 页号\n" +
				"vat_invoice_drawer 开票人\n" +
				"vat_invoice_review 复核\n" +
				"vat_invoice_plate_specific 规格型号\n" +
				"type 发票种类\n" +
				"vat_invoice_total_note 备注信息\n" +
				"vat_invoice_haoma_right_side 右侧打印发票号码";
}
