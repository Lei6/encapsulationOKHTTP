package com.demo.encapsulationokhttp.okhttp;

import android.util.Log;

import com.demo.encapsulationokhttp.utils.CommonUtil;
import com.demo.encapsulationokhttp.utils.GsonUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 默认
 * @param <T>
 */

public abstract class CallBackDefault<T> extends CallBackUtil<HttpResult<T>>{
        @Override
        public HttpResult onParseResponse(Call call, Response response) throws IOException {

            HttpResult result = new HttpResult();
            String body = response.body().string();
            JSONObject jsonObj;
            try {
                jsonObj = new JSONObject(body);
                String object = jsonObj.optString("object");
                if (!CommonUtil.isEmpty(object)) {
                    if(object.startsWith("{")){
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        result = (HttpResult<T>) GsonUtils.fromJsonObject(new StringReader(body), (Class) params[0]);
                    }else if(object.startsWith("[")){
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        result = GsonUtils.fromJsonArray(new StringReader(body), params[0]);
                    }
                }else{
                    Type genType = getClass().getGenericSuperclass();
                    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                    result = (HttpResult<T>) GsonUtils.fromJsonObject(new StringReader(body), (Class) params[0]);
                    Log.e("okhttp", "-------error--------->: "+result.toString() );
                }

            } catch (Exception e) {
                e.printStackTrace();
                result.data = null;
                Log.e("Exception", "onParseResponse: "+e.getMessage() );
//                throw new RuntimeException(e.getMessage());
            }
            return result;
        }

    @Override
    public void onFailure(Call call, Exception e) {

    }
}