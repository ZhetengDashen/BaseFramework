package com.baseeasy.baseframework.demoactivity;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.baseframework.demoactivity.adapter.RecyclerDemoAdapter;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_RECYCLER_ACTIVITY;

@Route(path = DEMO_RECYCLER_ACTIVITY)
public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerDemoAdapter recyclerDemoAdapter;
    private List<RVDemoEntity> rvDemoEntities = new ArrayList<>();
    private String  images[]={"https://imgsa.baidu.com/forum/w%3D580/sign=adafd9b02da446237ecaa56aa8237246/edbb5cd0f703918f60619e005c3d26975beec4e1.jpg",
            "https://imgsa.baidu.com/forum/w%3D580/sign=c47e413076cb0a4685228b315b62f63e/d772036d55fbb2fb8b9bfbd8424a20a44423dce1.jpg",
            "https://imgsa.baidu.com/forum/w%3D580/sign=0d60bfce399b033b2c88fcd225cf3620/30955563f6246b60d57910bee6f81a4c500fa226.jpg",
            "https://imgsa.baidu.com/forum/w%3D580/sign=849d4f03da2a60595210e1121836342d/826fe9ca7bcb0a46278d28f36663f6246960afee.jpg",
            "https://imgsa.baidu.com/forum/w%3D580/sign=2512f3c201d79123e0e0947c9d365917/5ba588003af33a8706c2a3d0cb5c10385143b5ee.jpg",
            "https://imgsa.baidu.com/forum/w%3D580/sign=a1b4f523ffd3572c66e29cd4ba126352/10b3390828381f3042412f63a4014c086f06f039.jpg"};
    private String msg[]={"Live your life and come here on the road",
                 "Deliberately avoid, is really care",
                 "Nothing is the reason to work hard",
                 "In this world, most of the winners are lovers",
                 "May the world turn into a sea, and may you and I return to the first sight",
                 "Your name was so common that I was shocked to hear it"
    };
    private String title[]={"Live your",
            "Deliberately",
            "Nothing",
            "World",
            "May",
            "Your"
    };
    int  mCurrentCounter=0;
    int  TOTAL_COUNTER=30;
    boolean isErr=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initDate();
        initView();

    }

    private void initDate() {
        for (int i = 0; i <10 ; i++) {
            rvDemoEntities.add(new RVDemoEntity(images[(int)(Math.random()*6)],""+(int)(Math.random()*2),title[(int)(Math.random()*6)],msg[(int)(Math.random()*6)],i));
        }
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerDemoAdapter=new RecyclerDemoAdapter(rvDemoEntities);
        GridLayoutManager layoutManager = new GridLayoutManager(this ,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerDemoAdapter);
        recyclerDemoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<RVDemoEntity> rvDemoEntities= adapter.getData();
                Toast.makeText(RecyclerViewActivity.this, rvDemoEntities.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerDemoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
               if(view.getId()==R.id.rv_item_bt){
                   Toast.makeText(RecyclerViewActivity.this, "点击了：ChildView"+position, Toast.LENGTH_SHORT).show();

               }
            }
        });

        //默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        //ALPHAIN、SCALEIN、  SLIDEIN_BOTTOM、SLIDEIN_LEFT、SLIDEIN_RIGHT
        recyclerDemoAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//动画

       // recyclerDemoAdapter.addHeaderView();//添加头部View
      // recyclerDemoAdapter.addFooterView();//添加底部View

        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        recyclerDemoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            recyclerDemoAdapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                //成功获取更多数据
                                recyclerDemoAdapter.addData(rvDemoEntities);
                                mCurrentCounter = recyclerDemoAdapter.getData().size();
                                recyclerDemoAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                isErr = true;
                                Toast.makeText(RecyclerViewActivity.this, "加载更多数据失败", Toast.LENGTH_SHORT).show();
                                recyclerDemoAdapter.loadMoreFail();

                            }
                        }
                    }

                }, 500);
            }
        },recyclerView);
        //默认第一次加载会进入回调，如果不需要可以配置：
        recyclerDemoAdapter.disableLoadMoreIfNotFullPage();


    }
}
