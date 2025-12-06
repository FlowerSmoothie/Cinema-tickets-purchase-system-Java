package upping;

import enums.Requests;

import java.io.Serializable;

public class ClientRequest implements Serializable
{

    private Requests request;
    private String message;
    private Object data;

    public ClientRequest()
    {

    }

    public ClientRequest(Requests request, String message, Object data)
    {
        this.request = request;
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Requests getRequest() { return request; }
    public void setRequest(Requests request) { this.request = request; }

    public Object getObject() { return data; }
    public void setObject(Object data) { this.data = data; }

}
