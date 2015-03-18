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
package com.popbill.api.message;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.popbill.api.BaseServiceImp;
import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;

/**
 * Implementation of Popbill MessageService Interface
 * 
 * @author KimSeongjun
 * @version 1.0.0
 * @see com.popbill.api.MessageService
 */
public class MessageServiceImp extends BaseServiceImp implements MessageService {

	@Override
	protected List<String> getScopes() {
		return Arrays.asList("150", "151", "152");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#getUnitCost
	 */
	@Override
	public float getUnitCost(String CorpNum, MessageType MsgType)
			throws PopbillException {
		if (MsgType == null)
			throw new PopbillException(-99999999, "메시지 유형이 입력되지 않았습니다.");

		UnitCostResponse response = httpget(
				"/Message/UnitCost?Type=" + MsgType.name(), CorpNum, null,
				UnitCostResponse.class);

		return response.unitCost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#getURL
	 */
	@Override
	public String getURL(String CorpNum, String UserID, String TOGO)
			throws PopbillException {

		URLResponse response = httpget("/Message/?TG=" + TOGO, CorpNum, UserID,
				URLResponse.class);

		return response.url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendSMS
	 */
	@Override
	public String sendSMS(String CorpNum, String sender, String receiver,
			String receiverName, String content, Date reserveDT, String UserID)
			throws PopbillException {
		Message message = new Message();

		message.setSender(sender);
		message.setReceiver(receiver);
		message.setReceiverName(receiverName);
		message.setContent(content);

		return sendSMS(CorpNum, new Message[] { message }, reserveDT, UserID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendSMS
	 */
	@Override
	public String sendSMS(String CorpNum, Message[] Messages, Date reserveDT,
			String UserID) throws PopbillException {
		return sendSMS(CorpNum, null, null, Messages, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendSMS
	 */
	@Override
	public String sendSMS(String CorpNum, String sender, String content,
			Message[] Messages, Date reserveDT, String UserID)
			throws PopbillException {
		return sendMessage(MessageType.SMS, CorpNum, sender, null, content,
				Messages, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendLMS
	 */
	@Override
	public String sendLMS(String CorpNum, String sender, String receiver,
			String receiverName, String subject, String content,
			Date reserveDT, String UserID) throws PopbillException {
		Message message = new Message();

		message.setSender(sender);
		message.setReceiver(receiver);
		message.setReceiverName(receiverName);
		message.setContent(content);
		message.setSubject(subject);

		return sendLMS(CorpNum, new Message[] { message }, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendLMS
	 */
	@Override
	public String sendLMS(String CorpNum, Message[] Messages, Date reserveDT,
			String UserID) throws PopbillException {
		return sendLMS(CorpNum, null, null, null, Messages, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendLMS
	 */
	@Override
	public String sendLMS(String CorpNum, String sender, String subject,
			String content, Message[] Messages, Date reserveDT, String UserID)
			throws PopbillException {
		return sendMessage(MessageType.LMS, CorpNum, sender, subject, content,
				Messages, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendXMS
	 */
	@Override
	public String sendXMS(String CorpNum, String sender, String receiver,
			String receiverName, String subject, String content,
			Date reserveDT, String UserID) throws PopbillException {
		Message message = new Message();

		message.setSender(sender);
		message.setReceiver(receiver);
		message.setReceiverName(receiverName);
		message.setContent(content);
		message.setSubject(subject);

		return sendXMS(CorpNum, new Message[] { message }, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendXMS
	 */
	@Override
	public String sendXMS(String CorpNum, Message[] Messages, Date reserveDT,
			String UserID) throws PopbillException {
		return sendXMS(CorpNum, null, null, null, Messages, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#sendXMS
	 */
	@Override
	public String sendXMS(String CorpNum, String sender, String subject,
			String content, Message[] Messages, Date reserveDT, String UserID)
			throws PopbillException {
		return sendMessage(MessageType.XMS, CorpNum, sender, subject, content,
				Messages, reserveDT, UserID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#getMessages
	 */
	@Override
	public SentMessage[] getMessages(String CorpNum, String receiptNum)
			throws PopbillException {
		if (receiptNum == null)
			throw new PopbillException(-99999999, "접수번호가 입력되지 않았습니다.");

		return httpget("/Message/" + receiptNum, CorpNum, null,
				SentMessage[].class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.popbill.api.MessageService#cancelReserve
	 */
	@Override
	public Response cancelReserve(String CorpNum, String receiptNum,
			String UserID) throws PopbillException {
		if (receiptNum == null)
			throw new PopbillException(-99999999, "접수번호가 입력되지 않았습니다.");

		return httpget("/Message/" + receiptNum + "/Cancel", CorpNum, UserID,
				Response.class);
	}

	private String sendMessage(MessageType MsgType, String CorpNum,
			String sender, String subject, String content, Message[] Messages,
			Date reserveDT, String UserID) throws PopbillException {
		if (MsgType == null)
			throw new PopbillException(-99999999, "메시지 유형이 입력되지 않았습니다.");
		if (CorpNum == null || CorpNum.isEmpty())
			throw new PopbillException(-99999999, "회원 사업자번호가 입력되지 않았습니다.");

		if (Messages == null || Messages.length == 0)
			throw new PopbillException(-99999999, "전송할 메시지가 입력되지 않았습니다.");

		SendRequest request = new SendRequest();
		request.snd = sender;
		request.content = content;
		request.subject = subject;

		if (reserveDT != null)
			request.sndDT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA)
					.format(reserveDT);

		request.msgs = Messages;

		String PostData = toJsonString(request);

		ReceiptResponse response = httppost("/" + MsgType.name(), CorpNum,
				PostData, UserID, ReceiptResponse.class);

		return response.receiptNum;
	}

	protected class SendRequest {
		public String snd;
		public String content;
		public String subject;
		public String sndDT;

		public Message[] msgs;
	}

	protected class ReceiptResponse {
		public String receiptNum;
	}
}