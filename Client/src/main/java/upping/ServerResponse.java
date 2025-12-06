package upping;

import enums.Responses;

import java.io.Serializable;

public class ServerResponse implements Serializable
{

    private Responses status;
    private String message;
    private Object data;


    public ServerResponse(){

    }

    public ServerResponse(Responses status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getResponseMessage() {
        return message;
    }

    public void setResponseMessage(String message) {
        this.message = message;
    }

    public Responses getResponseStatus() {
        return status;
    }

    public void setResponseStatus(Responses status) {
        this.status = status;
    }

    public Object getResponseData() {
        return data;
    }

    public void setResponseData(Object data) {
        this.data = data;
    }

}
