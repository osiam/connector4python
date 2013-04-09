package org.osiam.oauth2.client.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class UserFriendlyException extends Exception {

    private
    String
            errorCode;

    private static final
    long
            serialVersionUID =
            1L;

    public UserFriendlyException(String errorCode) {
        this.errorCode =
                errorCode;
    }

    public String toString() {
        if (errorCode.equals("409")) {
            return ("Error Code: " +
                    errorCode +
                    "<br>Message: User already exists and can't be created");
        } else if (errorCode.equals("404")) {
            return ("Error Code: " +
                    errorCode +
                    "<br>Message: User doesn't exists and can't be updated");
        }
        return "Error code:" +
                errorCode;
    }
}