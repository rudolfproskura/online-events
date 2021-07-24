/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package online.events.exception;

import java.util.List;



public class DogadajAppRuleException extends Exception {

    protected List<String> messages;

    public DogadajAppRuleException() {
    	super();
    }

    public DogadajAppRuleException(List<String> messages) {
		super();
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
