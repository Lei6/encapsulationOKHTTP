package com.demo.encapsulationokhttp.okhttp;

public class HttpResult<T> {

    /**
     * info :
     * code : 100
     * object : {}
     * refrsh : true
     */
    private String SUCCESS = "1";
    private String FAIL = "0";
    private String OVERTIME = "102";
    public String msg;
    public String code;
    public T data;

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public boolean isFail(){
        return FAIL.equals(code);
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "SUCCESS='" + SUCCESS + '\'' +
                ", FAIL='" + FAIL + '\'' +
                ", OVERTIME='" + OVERTIME + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
