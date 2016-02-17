package com.wang.recycledemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.recycledemo.widget.AutoScrollViewPager;
import com.wang.recycledemo.widget.DisplayUtil;
import com.wang.recycledemo.widget.ImagePagerAdapter;
import com.wang.recycledemo.widget.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 广告轮播图+实现左右无限循环+动态添加小点标示+集成简单
 * @author fredwang 
 * 2016-01-31
 */
public class MainActivity extends Activity {

	private LinearLayout mainLayout;
	private AutoScrollViewPager viewPager;
    private TextView title;
    private int oldPosition,index;
    private ArrayList<HashMap<String, String>> arrayList=new ArrayList<HashMap<String,String>>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initData();
        initView();
    }
    
    private void initData()
	{
		HashMap<String, String> hashMap1=new HashMap<String, String>();
		hashMap1.put("title", "全面回忆高清影视");
		hashMap1.put("logo", "http://img2.cache.netease.com/ent/2012/8/3/2012080303380055dd4.jpg");
		hashMap1.put("url", "http://www.baidu.com");
		arrayList.add(hashMap1);
		
		HashMap<String, String> hashMap2=new HashMap<String, String>();
		hashMap2.put("title", "名牌大厂的美女");
		hashMap2.put("logo", "http://imgsrc.baidu.com/forum/pic/item/cf8cdb22720e0cf39b7f28510a46f21fbc09aaea.jpg");
		hashMap2.put("url", "http://www.baidu.com");
		arrayList.add(hashMap2);
		
		HashMap<String, String> hashMap3=new HashMap<String, String>();
		hashMap3.put("title", "圣诞老人的礼物");
		hashMap3.put("logo", "http://i1.sinaimg.cn/edu/2014/1203/U12216P42DT20141203165538.jpeg");
		hashMap3.put("url", "http://www.baidu.com");
		arrayList.add(hashMap3);
		
	}
    
    private void initView()
    {
        title=(TextView)findViewById(R.id.title);
        mainLayout=(LinearLayout)findViewById(R.id.mainlayout);
        initDots();
        viewPager = (AutoScrollViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(new ImagePagerAdapter(MainActivity.this, arrayList,new EventClick() {
			
			@Override
			public void eventClick() {
				// TODO Auto-generated method stub
				Log.d("TAG", index+"处理点击每张图片的点击事件："+arrayList.get(index).get("url")+"----:"+arrayList.get(index).get("title"));
			}
		}).setInfiniteLoop(true));
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
                % ListUtils.getSize(arrayList));
    }
    
    
    private void initDots()
	{
		for (int i = 0; i < arrayList.size(); i++) {
			if(i==0)
			{
				mainLayout.addView(setDaoHangText(R.drawable.dot_focused));
			}else {
				mainLayout.addView(setDaoHangText(R.drawable.dot_normal));
			}
		}
	}
    
    private View setDaoHangText(int id) {
		View text = new View(MainActivity.this);
		LinearLayout.LayoutParams Viewpar = new LinearLayout.LayoutParams(DisplayUtil.dip2px(MainActivity.this, 8), DisplayUtil.dip2px(MainActivity.this, 8));
        Viewpar.setMargins(5, 5, 5, 5);
        text.setLayoutParams(Viewpar);
		text.setBackgroundResource(id);
		return text;
	}
    
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        	index=(position) % ListUtils.getSize(arrayList);
        	title.setText(arrayList.get((position) % ListUtils.getSize(arrayList)).get("title"));
        	
        	mainLayout.getChildAt(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			mainLayout.getChildAt((position) % ListUtils.getSize(arrayList)).setBackgroundResource(R.drawable.dot_focused);
			oldPosition=(position) % ListUtils.getSize(arrayList);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        viewPager.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume
        viewPager.startAutoScroll();
    }
    
    
}
