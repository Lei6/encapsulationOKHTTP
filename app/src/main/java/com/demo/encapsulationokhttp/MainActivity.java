package com.demo.encapsulationokhttp;


import android.os.Bundle;

import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.demo.encapsulationokhttp.adapter.JokeAdapter;
import com.demo.encapsulationokhttp.bean.Joke;
import com.demo.encapsulationokhttp.okhttp.CallBackDefault;
import com.demo.encapsulationokhttp.okhttp.HttpResult;
import com.demo.encapsulationokhttp.okhttp.OkhttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.joke_recycle_view)
    RecyclerView jokeRecycleView;
    @BindView(R.id.swrefresh)
    SwipeRefreshLayout swrefresh;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }


    private void initView() {
        swrefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    private void initListener() {
        //下拉刷新
        swrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                initData();
            }
        });
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        OkhttpUtil.okHttpPost(Constant.JOKE, map, new CallBackDefault<Joke>() {
            @Override
            public void onResponse(HttpResult<Joke> response) {

                if (response.isSuccess()) {
                    //请求成功
                    initData(response.data);

                } else if (response.isFail()) {
                    //请求失败
                    Toast.makeText(MainActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {
                super.onFailure(call, e);
//                网络连接失败
            }
        });
    }

    private void initData(Joke data) {
        swrefresh.setRefreshing(false);
        if (data == null || data.getList() == null || data.getList().size() == 0) {
            return;
        }
        List<Joke.ListBean> list = data.getList();
        JokeAdapter adapter = new JokeAdapter(this, list);
        jokeRecycleView.setLayoutManager(new LinearLayoutManager(this));
        jokeRecycleView.setAdapter(adapter);
    }

}
