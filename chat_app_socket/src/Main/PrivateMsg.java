package Main;

import java.io.Serializable;

public class PrivateMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Msg;
	private String RecUidUsername;
	private String SenderUidUsername;
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
	public String getRecUidUsername() {
		return RecUidUsername;
	}
	public void setRecUidUsername(String uidUsername) {
		RecUidUsername = uidUsername;
	}
	public String getSenderUidUsername() {
		return SenderUidUsername;
	}
	public void setSenderUidUsername(String senderUidUsername) {
		SenderUidUsername = senderUidUsername;
	}	
}
