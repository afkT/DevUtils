package afkt.project.ui.activity;

import android.view.LayoutInflater;
import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityViewAssistBinding;
import afkt.project.model.item.ButtonValue;
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
public class ViewAssistActivity extends BaseActivity<ActivityViewAssistBinding> {

    ViewAssist viewAssist;

    @Override
    public int baseLayoutId() {
        return R.layout.activity_view_assist;
    }

    @Override
    public void initValue() {
        super.initValue();

        viewAssist = ViewAssist.wrap(binding.vidAvaFrame);

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
        ViewUtils.setPadding(binding.vidAvaFrame, SizeUtils.dipConvertPx(50));

        viewAssist.register(ViewAssist.TYPE_ING, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_loading, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
                boolean isContent = (assist.getTag() == null);
                HandlerUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (isContent) {
                            assist.showType(100);
                        } else {
                            assist.showType(200);
                        }
                    }
                }, isContent ? 3000 : 2000);
            }
        }).register(100, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_error, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
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
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_content, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
            }
        }).showIng();
    }

    private void emptyType() {
        viewAssist.register(ViewAssist.TYPE_ING, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_loading2, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
                HandlerUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        assist.showType(Integer.MAX_VALUE);
                    }
                }, 3000);
            }
        }).register(Integer.MAX_VALUE, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_empty, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
            }
        }).showIng();
    }

    private void customType() {
        viewAssist.register(ViewAssist.TYPE_ING, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_loading3, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
                HandlerUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        assist.showType(159);
                    }
                }, 3000);
            }
        }).register(159, new ViewAssist.Adapter() {
            @Override
            public View onCreateView(
                    ViewAssist assist,
                    LayoutInflater inflater
            ) {
                return inflater.inflate(R.layout.view_assist_custom, null);
            }

            @Override
            public void onBindView(
                    ViewAssist assist,
                    View view,
                    int type
            ) {
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