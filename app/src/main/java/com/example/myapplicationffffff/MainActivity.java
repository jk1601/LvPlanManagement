package com.example.myapplicationffffff;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    BottomNavigationView bnView;
    ViewPager viewPager;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        bnView = findViewById(R.id.bo_na_view);
        viewPager = findViewById(R.id.view_pager);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);


//        imageView = findViewById(R.id.img_photo);

        navigationView = findViewById(R.id.navigation_view);
        final View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        imageView = headerView.findViewById(R.id.img_photo);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(headerView.getContext(),"ssss",Toast.LENGTH_SHORT).show();
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomePage_Fragment());
        fragments.add(new Plan_Fragment());
        fragments.add(new Other_Fragment());
        //fragments.add(new FouFragment());

        Fragment_Adapter adapter = new Fragment_Adapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //BottomNavigationView 点击事件监听
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.tab_one:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_two:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_three:
                        viewPager.setCurrentItem(2);
                        break;
//                    case R.id.tab_four:
//                        viewPager.setCurrentItem(3);
//                        break;
                }
                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.id_1:
                        try{
                            System.out.println("111");
                            Toast.makeText(headerView.getContext(),"111",Toast.LENGTH_SHORT).show();
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case R.id.id_2:
                        Toast.makeText(headerView.getContext(),"222",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.id_3:
                        Toast.makeText(MainActivity.this,"333",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        // ViewPager 滑动事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
                bnView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
//        {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//                View content = drawerLayout.getChildAt(0); //获得主界面View
//                if (drawerView.getTag().equals("left")) {  //判断是否是左菜单
//                    int offset = (int) (drawerView.getWidth() * slideOffset);
//                    content.setTranslationX(offset);
//                }
//            }
//        };
//        toggle.syncState();
//        drawerLayout.addDrawerListener(toggle);

    }


}
