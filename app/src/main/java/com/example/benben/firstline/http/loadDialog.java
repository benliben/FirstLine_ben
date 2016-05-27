package com.example.benben.firstline.http;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.benben.firstline.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by beneben on 2016/5/27.
 * 异步提示对话框
 */
public class LoadDialog {
    private static MaterialDialog diag;
    private static AtomicInteger count = new AtomicInteger(0);

    public static void Show(Context c, String title, String ds, int icon,
                            final EventFinish event) {
        count.getAndIncrement();
        if(diag!=null)
            return;

//		diag = new ProgressDialog(c, AlertDialog.THEME_HOLO_LIGHT);

        if (title == null)
            title = "提示";
//		diag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		diag.setTitle(title);
        if (icon <= 0)
            icon = android.R.drawable.ic_dialog_info;
//		diag.setIcon(icon);
        if (ds == null)
            ds = "正在加载数据，请稍候...";
//		diag.setMessage(ds);
//		diag.setIndeterminate(false);
//		diag.setCancelable(false);
//		diag.setOnKeyListener(new DialogInterface.OnKeyListener() {
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//				return true;
//			}
//		});
        if (c instanceof Activity) {
            Activity activity = (Activity) c;
            if (activity.isFinishing()) {
                diag = null;
                return;
            }
        }

//		diag.show();
        diag =  new MaterialDialog.Builder(c).theme(Theme.LIGHT).backgroundColorRes(R.color.dialog_bg)
                .title(title).titleColorRes(R.color.dialog_title)
                .content(ds).contentColorRes(R.color.dialog_message)
                .progress(true, 0)
                .show();
//        diag.setContentView(R.layout.layout_progressbar);
    }

    public static void Show(Context c) {
        Show(c, null, null, 0, null);
    }

    public static void Show(Context c,String msg) {
        Show(c, null, msg, 0, null);
    }

    public static void Hide() {
        try {
            count.getAndDecrement();
            if (count.get()<=0) {
                count.set(0);
                if (diag != null) {
                    diag.dismiss();
                    diag = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
