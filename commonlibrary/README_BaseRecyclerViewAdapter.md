# AVLoadingIndicatorView使用说明与注意事项
> #### github地址：
> https://github.com/CymChad/BaseRecyclerViewAdapterHelper   <br/>
和原始的adapter相对，减少70%的代码量。


#### 配置与注意事项：
##### gradle 配置：
  ```
   api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.46'
  ```

## 使用说明  

> 功能目录:<br/>
> 添加Item事件、添加列表加载动画、添加头部、尾部、自动加载（上拉、下拉）、分组布局 <br/>  多布局、设置空布局（无数据的时候显示的View）、添加拖拽、滑动删除、树形列表、自定义ViewHolder
#### 基本使用   
  ```
public class RecyclerDemoAdapter extends BaseQuickAdapter<RVDemoEntity, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.rv_item_tx1,item.getTitle());//给TextView赋值
        helper.setImageResource(R.id.icon, item.getImageResource());
        // 加载网络图片
       ImageLoaderFactory.getInstance().displayImage((ImageView)helper.getView(R.id.rv_item_image),item.getImage());
       helper.addOnClickListener(R.id.rv_item_bt);//添加子View的点击事件
    }     
}
  ```   
   ```
 Item的点击事件
adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
 
 ```
  
 ```
Item的长按事件
adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemLongClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
 
 ```
  ```
注意：嵌套recycleView的情况下需要使用你使用 adapter. setOnItemClickListener 来设置点击事件,如果使用recycleView.addOnItemTouchListener会累计添加的。
Item子控件的点击事件
首先在adapter的convert方法里面通过viewHolder.addOnClickListener绑定一下的控件id
 @Override
    protected void convert(BaseViewHolder viewHolder, Status item) {
        viewHolder.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)
                .addOnClickListener(R.id.tweetName)
                .linkify(R.id.tweetText);
       
    }

然后在设置
 adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemChildClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


 ```
>Item子控件的长按事件  步骤同上使用方法不同。<br/>
>adapter中绑定方法将addOnClickListener改成addOnLongClickListener.<br/>
>设置点击事件方法setOnItemChildClickListener改成setOnItemChildLongClickListener<br/>
>注意：设置子控件的事件，如果不在adapter中绑定，点击事件无法生效，因为无法找到你需要设置的控件。<br/>
>如果需要在点击事件中获取其他子控件可以使用：<br/>
>getViewByPosition(RecyclerView recyclerView, int position, @IdRes int viewId) <br/>
>注意：如果有header的话需要处理一下position加上 headerlayoutcount。<br/>
  
 >加载更多以及 多TypeItem 请参考APP中 RecyclerViewActivity。<br/>
更多详细用法请参考：https://www.jianshu.com/p/b343fcff51b0

