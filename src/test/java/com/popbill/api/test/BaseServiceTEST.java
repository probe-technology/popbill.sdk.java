package com.popbill.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import kr.co.linkhub.auth.LinkhubException;
import kr.co.linkhub.auth.Token;
import kr.co.linkhub.auth.TokenBuilder;

import org.junit.Test;

import com.popbill.api.ContactInfo;
import com.popbill.api.CorpInfo;
import com.popbill.api.JoinForm;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.TaxinvoiceServiceImp;

public class BaseServiceTEST {

	private final String testLinkID = "TESTER";
	private final String testSecretKey = "SwWxqU+0TErBXy/9TVjIPEnI0VTUMMSQZtJf3Ed8q3I=";

	private TaxinvoiceService taxinvoiceService;
	
	public BaseServiceTEST() {
		TaxinvoiceServiceImp service = new TaxinvoiceServiceImp();

		service.setLinkID(testLinkID);
		service.setSecretKey(testSecretKey);
		service.setTest(true);
		
		taxinvoiceService = service;
	}
	
	@Test
	public void getPopbillURL_TEST() throws PopbillException {
		
		String url = taxinvoiceService.getPopbillURL("1234567890", "testkorea",	"LOGIN");

		assertNotNull(url);

		System.out.println(url);

	}

	@Test
	public void joinMember_TEST() throws PopbillException {

		JoinForm joinInfo = new JoinForm();
		joinInfo.setLinkID(testLinkID);
		joinInfo.setCorpNum("1231212312"); // 사업자번호 "-" 제외
		joinInfo.setCEOName("대표자성명");
		joinInfo.setCorpName("상호");
		joinInfo.setAddr("주소");
		joinInfo.setZipCode("500-100");
		joinInfo.setBizType("업태");
		joinInfo.setBizClass("업종");
		joinInfo.setID("userid"); // 6자 이상 20자 미만
		joinInfo.setPWD("pwd_must_be_long_enough"); // 6자 이상 20자 미만
		joinInfo.setContactName("담당자명");
		joinInfo.setContactTEL("02-999-9999");
		joinInfo.setContactHP("010-1234-5678");
		joinInfo.setContactFAX("02-999-9998");
		joinInfo.setContactEmail("test@test.com");

		Response response = taxinvoiceService.joinMember(joinInfo);

		assertNotNull(response);

		System.out.println(response.getMessage());
	}

	@Test
	public void getBalance_TEST() throws PopbillException {

		
		double balance = taxinvoiceService.getBalance("1234567890");

		System.out.println(balance);
	}

	@Test
	public void getPartnerBalance_TEST() throws PopbillException {

		double balance = taxinvoiceService.getPartnerBalance("1234567890");

		System.out.println(balance);
	}
	
	@Test
	public void getListContact_TEST() throws PopbillException {
		ContactInfo[] contactList = taxinvoiceService.listContact("1234567890","testkorea");
		
		System.out.println(contactList.length);
	}
	
	@Test
	public void getUTCTime_TEST() throws PopbillException, LinkhubException{
		TokenBuilder tokenBuilder;
		Token token = null;
		Boolean expired;
		
		tokenBuilder = TokenBuilder
				.getInstance(testLinkID, testSecretKey)
				.ServiceID("POPBILL_TEST")
				.addScope("member")
				.addScope("110");
		
			
		try {
			token = tokenBuilder.build("1234567890", "");
		} catch (LinkhubException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		Date expiration = null;
		
		Date UTCTime = null;
		
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		SimpleDateFormat format2 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		format2.setTimeZone(TimeZone.getTimeZone("UTC"));
					
		try {
			UTCTime = format2.parse("2016-01-18T14:05:37Z");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			expiration = format.parse(token.getExpiration());
			expired = expiration.before(UTCTime);
			System.out.println(token.getExpiration()+ " "+UTCTime+ " "+ expired);
			assertNotNull(expiration);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void httpGET_GZIP_TEST() throws PopbillException, LinkhubException{
		TokenBuilder tokenBuilder;
		Token token = null;
		HttpURLConnection httpURLConnection;
		
		String corpNum = "1234567890";
		
		tokenBuilder = TokenBuilder
				.getInstance(testLinkID, testSecretKey)
				.ServiceID("POPBILL_TEST")
				.addScope("member")
				.addScope("110");
		
		token = tokenBuilder.build(corpNum, "");
		
		try {
			URL uri = new URL("https://popbill_test.linkhub.co.kr/Taxinvoice/EmailPublicKeys");
			httpURLConnection = (HttpURLConnection) uri.openConnection();
		} catch (Exception e) {
			throw new PopbillException(-99999999, "팝빌 API 서버 접속 실패", e);
		}


		httpURLConnection.setRequestProperty("Authorization", "Bearer "
					+ token.getSession_token());


		httpURLConnection.setRequestProperty("x-pb-version".toLowerCase(),
				"1.0");

		httpURLConnection.setRequestProperty("x-pb-userid", "testkorea");
		
		httpURLConnection.setRequestProperty("Accept-Encoding",
				"gzip");
		
		assertEquals("gzip",httpURLConnection.getContentEncoding());
	}
	
	@Test
	public void UpdateContact_TEST() throws PopbillException {
		ContactInfo contInfo = new ContactInfo();
		
		contInfo.setPersonName("링크허브 담당자");
		contInfo.setTel("02-1234-1234");
		contInfo.setHp("010-1234-1234");
		contInfo.setEmail("test@test.com");
		contInfo.setFax("02-6442-9700");
		contInfo.setSearchAllAllowYN(true);
		contInfo.setMgrYN(true);
		
		Response response = taxinvoiceService.updateContact("1234567890", contInfo, "testkorea");

		assertNotNull(response);

		System.out.println(response.getMessage());
	}
	
	@Test
	public void RegistContact_TEST() throws PopbillException{
		ContactInfo contInfo = new ContactInfo();
		
		contInfo.setId("testkorea01");
		contInfo.setPwd("innopost");
		contInfo.setPersonName("정요한");
		contInfo.setTel("02-1234-1234");
		contInfo.setHp("010-1234-1234");
		contInfo.setFax("070-7510-3710");
		contInfo.setEmail("test1234@test.com");
		contInfo.setSearchAllAllowYN(true);
		contInfo.setMgrYN(false);
		
		Response response = taxinvoiceService.registContact("1234567890", contInfo, "testkorea");
		
		assertNotNull(response);
		
		System.out.println(response.getMessage());
	}
	
	@Test
	public void CheckID_TEST() throws PopbillException{
		Response response = taxinvoiceService.checkID("testkorea");
		assertNotNull(response);
		System.out.println("["+response.getCode() + "] " + response.getMessage());
	}
	

	@Test
	public void UpdateCorpInfo_TEST() throws PopbillException{
		CorpInfo corpInfo = new CorpInfo();
		corpInfo.setAddr("광주광역시 서구 천변좌로 268");
		corpInfo.setBizClass("업종테스트");
		corpInfo.setBizType("업태테스트");
		corpInfo.setCeoname("대표자성명 설정 테스트");
		corpInfo.setCorpName("상호 테스트");
		
		Response response = taxinvoiceService.updateCorpInfo("1234567890", corpInfo, "testkorea");
		assertNotNull(response);
		System.out.println("["+response.getCode()+"] "+response.getMessage());
	}
	
	
	@Test
	public void GetCorpInfo_TEST() throws PopbillException{
		CorpInfo corpInfo = taxinvoiceService.getCorpInfo("1234567890","testkorea");
		
		assertNotNull(corpInfo);
		
		System.out.println(corpInfo.getBizClass());
		System.out.println(corpInfo.getAddr());
		System.out.println(corpInfo.getBizType());
		System.out.println(corpInfo.getCeoname());
		System.out.println(corpInfo.getCorpName());
		
	}
	
}
















