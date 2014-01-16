package com.samir;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 
 * @ClassName: MyApplication
 * @Description: TODO
 * @author: hbwang
 * @date: 2014-1-2 上午10:42:03
 */
public class MyApplication extends Application{
	private static MyApplication instance;
	/**
	 * Thread to execute tasks in background..
	 */
	private final ExecutorService backgroundExecutor;

	/**
	 * Handler to execute runnable in UI thread.
	 */
	private final Handler handler;
	
	public static MyApplication getInstance() {
		if (instance == null)
			throw new IllegalStateException();
		return instance;
	}
	
	public MyApplication() {
		// TODO Auto-generated constructor stub
		instance = this;
		handler = new Handler();
		backgroundExecutor = Executors
				.newSingleThreadExecutor(new ThreadFactory() {
					@Override
					public Thread newThread(Runnable runnable) {
						Thread thread = new Thread(runnable,"Background executor service");
						thread.setPriority(Thread.MIN_PRIORITY);
						thread.setDaemon(true);
						return thread;
					}
				});
	}
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//初始化图片加载库
		initImageLoader(getApplicationContext());
	}
	
	
	/**
	 * 
	 * @Title: initImageLoader
	 * @Description: 初始化图片加载库
	 * @return: void
	 */
	public static void initImageLoader(Context context) {
         // This configuration tuning is custom. You can tune every option, you may tune some of them,
         // or you can create default configuration by
         //  ImageLoaderConfiguration.createDefault(this);
         // method.
         ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                         .threadPriority(Thread.NORM_PRIORITY - 2)
                         .denyCacheImageMultipleSizesInMemory()
                         .discCacheFileNameGenerator(new Md5FileNameGenerator())
                         .tasksProcessingOrder(QueueProcessingType.LIFO)
                         .writeDebugLogs() // Remove for release app
                         .build();
         // Initialize ImageLoader with configuration.
         ImageLoader.getInstance().init(config);
  }
	
   /**
	 * Submits request to be executed in background.
	 * 
	 * @param runnable
	 */
	public void runInBackground(final Runnable runnable) {
		backgroundExecutor.submit(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Exception e) {
//					LogManager.exception(runnable, e);
				}
			}
		});
	}

	/**
	 * Submits request to be executed in UI thread.
	 * 
	 * @param runnable
	 */
	public void runOnUiThread(final Runnable runnable) {
		handler.post(runnable);
	}

	/**
	 * Submits request to be executed in UI thread.
	 * 
	 * @param runnable
	 * @param delayMillis
	 */
	public void runOnUiThreadDelay(final Runnable runnable, long delayMillis) {
		handler.postDelayed(runnable, delayMillis);
	}

}
