package afkt.project.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import butterknife.BindView;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.widget.assist.ViewAssist;

/**
 * detail: ViewAssist Activity
 * @author Ttt
 */
public class ViewAssistActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_ava_frame)
    FrameLayout vid_ava_frame;

    ViewAssist viewAssist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_assist;
    }

    @Override
    public void initValues() {
        super.initValues();

        viewAssist = ViewAssist.wrap(vid_ava_frame);

        switch (getModuleType()) {
            case ButtonValue.BTN_VIEW_ASSIST_ERROR:
                errorType();
                break;
            case ButtonValue.BTN_VIEW_ASSIST_EMPTY:
                emptyType();
                break;
            case ButtonValue.BTN_VIEW_ASSIST_CUSTOM:
                customType();
                break;
        }
    }

    private void errorType() {
        ViewUtils.setPadding(vid_ava_frame, SizeUtils.dipConvertPx(50));

        viewAssist.register(ViewAssist.TYPE_ING, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_loading, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                HandlerUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (assist.getTag() == null) {
                            assist.showType(100);
                        } else {
                            assist.showType(200);
                        }
                    }
                }, 5000);
            }
        }).register(100, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_error, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                ListenerUtils.setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastTintUtils.normal("click retry");
                        assist.setTag("").showIng();
                    }
                }, view);
            }
        }).register(200, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_content, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
            }
        }).showIng();
    }

    private void emptyType() {
        viewAssist.register(ViewAssist.TYPE_ING, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_loading2, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                HandlerUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        assist.showType(Integer.MAX_VALUE);
                    }
                }, 8000);
            }
        }).register(Integer.MAX_VALUE, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_empty, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
            }
        }).showIng();
    }

    private void customType() {
        viewAssist.register(ViewAssist.TYPE_ING, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_loading3, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                HandlerUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        assist.showType(159);
                    }
                }, 8000);
            }
        }).register(159, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_custom, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                ListenerUtils.setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastTintUtils.normal("Custom Type");
                    }
                }, view.findViewById(R.id.vid_vac_cardview));
            }
        }).showIng();
    }
}