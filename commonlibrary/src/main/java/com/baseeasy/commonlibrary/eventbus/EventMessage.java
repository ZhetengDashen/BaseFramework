package com.baseeasy.commonlibrary.eventbus;

public class EventMessage<T> {
    private int code;
    private String flag;// 标识 建议使用activity 的 ARouterPath
    private T event;

    public EventMessage(int code, String flag, T event) {
        this.code = code;
        this.flag = flag;
        this.event = event;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", flag='" + flag + '\'' +
                ", event=" + event +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getFlag() {
        return flag;
    }

    public T getEvent() {
        return event;
    }

    public static class Builder<T> {
        private int code;
        private String flag;
        private T event;


        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public Builder setEvent(T event) {
            this.event = event;
            return this;
        }

        public EventMessage create() {
            return new EventMessage<T>(code, flag, event);
        }
    }
}