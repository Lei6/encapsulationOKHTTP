# encapsulationOKHTTP
封装okhttp，get，post，上传，下载

更多方式请看：OkhttpUtil


回调接口可以使用  CallBackDefault（封装有gson解析）
直接在onResponse进行数据操作即可，不用再解析，适用于格式统一的json串
 如：{
       "code": 1,
       "msg": "数据返回成功",
       "data":{
             "page": 2,
             "totalCount": 9590,
             "totalPage": 959,
             "limit": 10,
             "list": [
                       {
                         "content": "儿子:“爸爸，为什么王叔叔那么喜欢吃辣”爸爸:“你怎么知道王叔叔喜欢吃辣？”儿子:“别人都叫我妈妈为辣妈，我经常看到王叔叔抱着我妈妈又亲又啃”爸爸:“尼玛”",
                          "updateTime": "2018-11-03 09:45:28"
                      },

           ]
       }
   }

或   CallBackUtil（自行解析）
需要在onResponse进行解析，开启线程进行数据展示或其他操作

post请求


           OkhttpUtil.okHttpPost(Constant.JOKE, map, new CallBackDefault<Joke>() {
                 @Override
                 public void onResponse(HttpResult<Joke> response) {

                  }

                 @Override
                 public void onFailure(Call call, Exception e) {
                 super.onFailure(call, e);
            }
        });

post请求：map请求体，tokanMap请求头


             OkhttpUtil.okHttpPost(url, map, tokenMap, new CallBackDefault<AuctionDetailsBean>() {
                    @Override
                    public void onResponse(HttpResult<AuctionDetailsBean> response) {

                    }

                    @Override
                    public void onFailure(Call call, Exception e) {
                        super.onFailure(call, e);

                    }
                });


头像上传： /**
          * post请求，上传单个文件
          * @param url：url
          * @param file：File对象
          * @param fileKey：上传参数时file对应的键
          * @param fileType：File类型，是image，video，audio，file
          * @param paramsMap：map集合，封装键值对参数
          * @param headerMap：map集合，封装请求头键值对
          * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。还可以重写onProgress方法，得到上传进度
          */

             File file = new File("图片路径");
             Map<String, String> map = new HashMap<>();
             map.put("userHead_file", file.getName());

             Map<String, String> tokenMap = new HashMap<>();
             tokenMap.put("token", token);

             OkhttpUtil.okHttpUploadFile(url, file, "userHead_file", "multipart/form-data", map, tokenMap, new CallBackUtil() {
                 @Override
                 public Object onParseResponse(Call call, final Response response) {

                     String string = response.body().string();
                     HttpResult httpResult = new Gson().fromJson(string, HttpResult.class);

                     return null;
                 }

                 @Override
                 public void onFailure(Call call, Exception e) {

                 }

                 @Override
                 public void onResponse(Object response) {

                 }
             });


多图上传1：/**
              * post请求，上传多个文件，以map集合的形式
              * @param url：url
              * @param fileMap：集合key是File对象对应的键，集合value是File对象
              * @param fileType：File类型，是image，video，audio，file
              * @param paramsMap：map集合，封装键值对参数
              * @param headerMap：map集合，封装请求头键值对
              * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
              */

               Map<String, File> map = new HashMap<>();
               map.put("imgone_file", new File(文件路径));
               map.put("imgtwo_file", new File(文件路径));

OkhttpUtil.okHttpUploadMapFile(url, selImageList, "multipart/form-data", map, tokenMap, new CallBackDefault<HttpResult>() {
                            @Override
                            public void onResponse(HttpResult<HttpResult> response) {

                            }

                            @Override
                            public void onFailure(Call call, Exception e) {
                                super.onFailure(call, e);

                            }
                        });

多图上传2：list集合上传

    Map<String, String> map = new HashMap<>();
        map.put("content", fankuiText);
        map.put("feedImg_file1", "hh");

        List<File> imageList = new ArrayList<>();
        imageList.add(new File(selImageList.get(i).path));

        OkhttpUtil.okHttpUploadListFile(url, map, imageList, "feedImg_file1", "multipart/form-data", getMap(), new CallBackUtil() {
            @Override
            public Object onParseResponse(Call call, Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toast("提交成功");
                    }
                });

            }

            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });

//SwipeRefreshLayout   api
setOnRefreshListener(OnRefreshListener listener)  设置下拉监听，当用户下拉的时候会去执行回调
setColorSchemeColors(int... colors) 设置 进度条的颜色变化，最多可以设置4种颜色
setProgressViewOffset(boolean scale, int start, int end) 调整进度条距离屏幕顶部的距离
setRefreshing(boolean refreshing) 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。