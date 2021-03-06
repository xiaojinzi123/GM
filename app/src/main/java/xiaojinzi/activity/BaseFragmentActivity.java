package xiaojinzi.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import xiaojinzi.autolayout.AutoLayoutFragmentActivity;

import org.greenrobot.eventbus.EventBus;

import xiaojinzi.annotation.ViewInjectionUtil;


/**
 * 对activity的进行的简易封装
 * 依赖于Ebus和注解框架
 *
 * @author xiaojinzi
 */
public abstract class BaseFragmentActivity extends AutoLayoutFragmentActivity {

    public static String TAG = null;

    // 上下文对象
    protected Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        if (!isShowTitleBar()) {
            // 设置没有标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (isFullScreen()) {
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        context = this;

        if (isRegisterEvent()) {
            //注册自己可以接受事件
            EventBus.getDefault().register(this);
        }

        // 尝试注入属性
        ViewInjectionUtil.injectView(this);

        // 初始化控件
        initView();

        // 初始化数据
        initData();

        // 设置各种监听
        setOnlistener();

    }

    /**
     * 是否显示标题栏
     *
     * @return
     */
    public boolean isShowTitleBar() {
        return false;
    }

    /**
     * 是否全屏显示
     *
     * @return
     */
    public boolean isFullScreen() {
        return true;
    }

    /**
     * 默认会注册事件
     *
     * @return
     */
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 初始化控件
     */
    public void initView() {
    }

    ;

    /**
     * 初始化数据
     */
    public void initData() {
    }

    ;

    /**
     * 设置各种监听
     */
    public void setOnlistener() {
    }

    ;

    /**
     * 简化findViewById方法
     *
     * @param <T>
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T f(int viewId) {
        return (T) findViewById(viewId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册自己
        EventBus.getDefault().unregister(this);
    }
}
