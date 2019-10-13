package tableshop.ilinkedtech.com.servies;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.utils.CommonUtils;
import tableshop.ilinkedtech.com.utils.LogUtils;

public class MyAccessibilityReceiver
        extends AccessibilityService
{
    private              int     code             = INSTALL;
    private static final int     INSTALL          = 0;
    private static final int     NEXT             = 1;
    private static final int     FINISH           = 2;
    private static final int     LEBO             = 3;
    public static        boolean isOpened         = false;
    private static final String  TYPE_INSTALL_APP = "com.android.packageinstaller";

    /**
     * 页面变化回调事件
     * @param event event.getEventType() 当前事件的类型;
     *              event.getClassName() 当前类的名称;
     *              event.getSource() 当前页面中的节点信息；
     *              event.getPackageName() 事件源所在的包名
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            // 判断事件页面所在的包名，这里是自己
            if (event.getPackageName()
                     .equals(TYPE_INSTALL_APP))
            {
                if (event.getPackageName()
                         .toString()
                         .contains("ilinkedtech.com"))
                {
                    switch (code) {
                        case INSTALL:
                            click(event,
                                  "安装",
                                  TextView.class.getName(),
                                  AccessibilityNodeInfo.ACTION_CLICK);
                            LogUtils.d("test=======", "安装");
                            code = NEXT;
                            break;
                        case NEXT:
                            click(event,
                                  "下一步",
                                  Button.class.getName(),
                                  AccessibilityNodeInfo.ACTION_CLICK);
                            LogUtils.d("test=======", "下一步");
                            code = FINISH;
                            break;
                        case FINISH:
                            click(event,
                                  "完成",
                                  TextView.class.getName(),
                                  AccessibilityNodeInfo.ACTION_CLICK);
                            LogUtils.d("test=======", "完成");
                            code = INSTALL;
                            break;
                        default:
                            break;
                    }
                }
            } else if (event.getPackageName()
                            .equals(KeyConst.HAPPY_PLAY_PAKEGE))
            {
                click(event, "PC镜像", TextView.class.getName(), AccessibilityNodeInfo.ACTION_SELECT);
                click(event,
                      "乐播手机App",
                      TextView.class.getName(),
                      AccessibilityNodeInfo.ACTION_CLICK);
                if (!isOpened) {
                    try {
                        event.getSource()
                             .performAction(AccessibilityService.GLOBAL_ACTION_BACK);
                        event.getSource()
                             .performAction(AccessibilityService.GLOBAL_ACTION_BACK);
                        CommonUtils.killProcess(getApplicationContext(),
                                                KeyConst.HAPPY_PLAY_PAKEGE);
                    } catch (Exception e) {

                    }

                }
                isOpened = true;
                //                CommonUtils.openApp(getApplicationContext(),getApplicationContext().getPackageName());

            }

        } else {
            //            Log.d("test=====", "the source = null");
        }
    }

    /**
     * 模拟点击
     * @param event 事件
     * @param text 按钮文字
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */
    private void click(AccessibilityEvent event, String text, String widgetType, int actionType) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = event.getSource()
                                                          .findAccessibilityNodeInfosByText(text);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName()
                            .equals(widgetType))
                    {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            node.performAction(actionType);
                        }
                        AccessibilityNodeInfo parent = node.getParent();
                        if (parent != null && parent.isEnabled()) {
                            parent.performAction(actionType);
                        }
                    }
                }
            }
        }
    }

    /**
     * 中断AccessibilityService的反馈时调用
     */
    @Override
    public void onInterrupt() {
        //        Log.d("test=====", "Interrupt");
    }
}