package com.example.fabricebenimana.a7weather.bus.event;

public abstract class NetworkEvent<T extends BaseResponse> extends BaseEvent {
    private boolean isSuccess;
    private T responseBoody;


    public NetworkEvent(final T responseBody, boolean isSuccess) {
        this.responseBoody = responseBody;
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getResponseBoody() {
        return responseBoody;
    }
}