package com.Campus.Utility;

/**
 * Interface for callbacks for the CampusMessaging.
 * Methods are {@link #OnMessageSent()}, {@link #OnMessageRecieved(String)}, and {@link #OnMessageDataChange()}
 * @author Johnil
 *
 */

public interface MessengerCallBack {
	
	/**
	 * Callback for when a message has been sent successfully
	 */
	
	public void OnMessageSent();
	
	/**
	 * Call back for when a message has been received.
	 * @param s The message
	 */
	
	public void OnMessageRecieved(String s);
	
	/**
	 * Call back for when any kind of data regarding messages or messaged users is changed
	 */
	
	public void OnMessageDataChange();

}
