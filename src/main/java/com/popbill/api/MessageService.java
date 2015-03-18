/*
 * Copyright 2006-2014 innopost.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.popbill.api;

import java.util.Date;

import com.popbill.api.message.Message;
import com.popbill.api.message.MessageType;
import com.popbill.api.message.SentMessage;

/**
 * Message Service Interface.
 * 
 * @author KimSeongjun
 * @version 1.0.0
 */
public interface MessageService extends BaseService {

	/**
	 * 회원의 문자메시지 전송단가 확인
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param MsgType
	 *            메시지 유형
	 * @return 단가 (ex. 11.0)
	 * @throws PopbillException
	 */
	public float getUnitCost(String CorpNum, MessageType MsgType)
			throws PopbillException;

	/**
	 * 팝빌 문자메시지 관련 URL 확인. 반환한 url은 30초이내에 브라우져에 표시하여야 함.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호.
	 * @param UserID
	 *            연동회원의 회원아이디
	 * @param TOGO
	 *            지정값. (BOX : 문자전송 내역 조회 팝업)
	 * @return 팝빌 URL (AccessToken값 포함. Token값은 응답후 30초까지만 유효함)
	 * @throws PopbillException
	 */
	public String getURL(String CorpNum, String UserID, String TOGO)
			throws PopbillException;

	/**
	 * 단문문자메시지 1건 전송
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param sender
	 *            발신자번호
	 * @param receiver
	 *            수신자번호
	 * @param receiverName
	 *            수신자명칭
	 * @param content
	 *            단문메시지 내용 최대 90Byte.
	 * @param reserveDT
	 *            예약전송시 예약일시.
	 * @param UserID
	 *            연동회원의 회원아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendSMS(String CorpNum, String sender, String receiver,
			String receiverName, String content, Date reserveDT, String UserID)
			throws PopbillException;

	/**
	 * 단문문자메시지 다량 전송. 1회 최대 1000건.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param Messages
	 *            메시지 배열.
	 * @param reserveDT
	 *            예약일시
	 * @param UserID
	 *            연동회원 아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendSMS(String CorpNum, Message[] Messages, Date reserveDT,
			String UserID) throws PopbillException;

	/**
	 * 단문문자메시지 다량전송. 발신번호, 내용 동보전송. 1회 최대 1000건.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param sender
	 *            동보전송 발신번호
	 * @param content
	 *            동보전송 단문문자메시지 내용.
	 * @param Messages
	 *            메시지 배열. 수신자번호, 수신자명칭을 기재. 별도 발신번호와 내용 기재시 해당건만 개별전송.
	 * @param reserveDT
	 *            예약일시
	 * @param UserID
	 *            연동회원 아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendSMS(String CorpNum, String sender, String content,
			Message[] Messages, Date reserveDT, String UserID)
			throws PopbillException;

	/**
	 * 장문문자메시지 1건 전송
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param sender
	 *            발신자번호
	 * @param receiver
	 *            수신자번호
	 * @param receiverName
	 *            수신자명칭
	 * @param subject
	 *            장문메시지 제목
	 * @param content
	 *            장문메시지 내용 최대 2000Byte.
	 * @param reserveDT
	 *            예약전송시 예약일시.
	 * @param UserID
	 *            연동회원의 회원아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendLMS(String CorpNum, String sender, String receiver,
			String receiverName, String subject, String content,
			Date reserveDT, String UserID) throws PopbillException;

	/**
	 * 장문문자메시지 다량 전송. 1회 최대 1000건.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param Messages
	 *            메시지 배열.
	 * @param reserveDT
	 *            예약일시
	 * @param UserID
	 *            연동회원 아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendLMS(String CorpNum, Message[] Messages, Date reserveDT,
			String UserID) throws PopbillException;

	/**
	 * 장문문자메시지 다량전송. 발신번호, 내용 동보전송. 1회 최대 1000건.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param sender
	 *            동보전송 발신번호
	 * @param subject
	 *            동보전송 장문메시지 제목
	 * @param content
	 *            동보전송 장문메시지 내용.
	 * @param Messages
	 *            메시지 배열. 수신자번호, 수신자명칭을 기재. 별도 발신번호와 내용 기재시 해당건만 개별전송.
	 * @param reserveDT
	 *            예약일시
	 * @param UserID
	 *            연동회원 아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendLMS(String CorpNum, String sender, String subject,
			String content, Message[] Messages, Date reserveDT, String UserID)
			throws PopbillException;

	/**
	 * 단/장문 문자메시지(메시지 길이에 따라 단문/장문을 선택하여 전송) 1건 전송
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param sender
	 *            발신자번호
	 * @param receiver
	 *            수신자번호
	 * @param receiverName
	 *            수신자명칭
	 * @param subject
	 *            메시지 제목
	 * @param content
	 *            메시지 내용
	 * @param reserveDT
	 *            예약전송시 예약일시.
	 * @param UserID
	 *            연동회원의 회원아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendXMS(String CorpNum, String sender, String receiver,
			String receiverName, String subject, String content,
			Date reserveDT, String UserID) throws PopbillException;

	/**
	 * 단/장문 문자메시지(메시지 길이에 따라 단문/장문을 선택하여 전송) 다량 전송. 1회 최대 1000건.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param Messages
	 *            메시지 배열.
	 * @param reserveDT
	 *            예약일시
	 * @param UserID
	 *            연동회원 아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendXMS(String CorpNum, Message[] Messages, Date reserveDT,
			String UserID) throws PopbillException;

	/**
	 * 단/장문 문자메시지(메시지 길이에 따라 단문/장문을 선택하여 전송) 다량전송. 발신번호, 내용 동보전송. 1회 최대 1000건.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param sender
	 *            동보전송 발신번호
	 * @param subject
	 *            동보전송 메시지 제목
	 * @param content
	 *            동보전송 메시지 내용.
	 * @param Messages
	 *            메시지 배열. 수신자번호, 수신자명칭을 기재. 별도 발신번호와 내용 기재시 해당건만 개별전송.
	 * @param reserveDT
	 *            예약일시
	 * @param UserID
	 *            연동회원 아이디
	 * @return receiptNum 접수번호.
	 * @throws PopbillException
	 */
	public String sendXMS(String CorpNum, String sender, String subject,
			String content, Message[] Messages, Date reserveDT, String UserID)
			throws PopbillException;

	/**
	 * 전송상태 확인
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param receiptNum
	 *            전송시 접수번호.
	 * @return SentMessage 배열.
	 * @throws PopbillException
	 */
	public SentMessage[] getMessages(String CorpNum, String receiptNum)
			throws PopbillException;

	/**
	 * 예약 메시지 전송 취소. 예약시간 기준 10분전의 건만 취소 가능.
	 * 
	 * @param CorpNum
	 *            연동회원 사업자번호
	 * @param receiptNum
	 *            전송시 접수번호.
	 * @param UserID
	 *            연동회원 아이디
	 * @return Response 응답
	 * @throws PopbillException
	 */
	public Response cancelReserve(String CorpNum, String receiptNum,
			String UserID) throws PopbillException;

}