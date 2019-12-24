package afkt.project.ui.activity;

import android.graphics.Color;
import android.view.View;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import butterknife.OnClick;
import dev.temp.ChineseUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.RandomUtils;
import dev.widget.LineTextView;

/**
 * detail: 自动换行 View
 * @author Ttt
 */
public class LineActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_al_tv)
    BaseTextView vid_al_tv;
    @BindView(R.id.vid_al_content_tv)
    LineTextView vid_al_content_tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_line;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 设置监听
        vid_al_content_tv.setNewLineCallBack(new LineTextView.OnNewLineCallBack() {
            @Override
            public void onNewLine(boolean isNewLine, int line) {
                StringBuilder builder = new StringBuilder();
                builder.append("是否换行: " + isNewLine);
                builder.append("\n换行数量: " + line);
                vid_al_tv.setText(builder.toString());
            }
        });
        // 默认点击
        vid_al_content_tv.performClick();
    }

    @OnClick({R.id.vid_al_content_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_al_content_tv:
                // 随机字符串
                String text = ChineseUtils.getRandomWord(RandomUtils.getRandom(300)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(50));
                String randomText = RandomUtils.getRandom(text.toCharArray(), text.length());
                // 设置内容
                ViewHelper.get().setTextColor(vid_al_content_tv, Color.BLACK)
                        .setTextSizeBySp(vid_al_content_tv, RandomUtils.getRandom(13, 25))
                        .setBold(vid_al_content_tv, RandomUtils.nextBoolean())
                        .setText(vid_al_content_tv, randomText);
                break;
        }
    }
}