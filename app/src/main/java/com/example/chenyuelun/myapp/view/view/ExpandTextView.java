package com.example.chenyuelun.myapp.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;


public class ExpandTextView extends FrameLayout {
    TextView contentView;
    TextView openView;

    protected boolean isExpand = false;
    private int defaultLine = 5;

    public ExpandTextView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.expandtextview_layout, this);
        contentView = (TextView) findViewById(R.id.content_text);
        openView = (TextView) findViewById(R.id.open_view);

        setClickListener();
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.expandtextview_layout, this);
        contentView = (TextView) findViewById(R.id.content_text);
        openView = (TextView) findViewById(R.id.open_view);

        setClickListener();
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.expandtextview_layout, this);
        contentView = (TextView) findViewById(R.id.content_text);
        openView = (TextView) findViewById(R.id.open_view);

        setClickListener();
    }


    public void setClickListener(){

        openView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
//                if (onStateChangeListener != null) {
//                    onStateChangeListener.onStateChange(isExpand);
//                }
                if (isExpand) {
                    contentView.setLines(contentView.getLineCount());
                    openView.setText("隐藏");
                } else {
                    if(contentView.getLineCount()<defaultLine){
                        contentView.setLines(contentView.getLineCount());
                    }else
                    contentView.setLines(defaultLine);
                    openView.setText("展开");
                }
            }
        });
    }

    public void setText(String str) {
        contentView.setText(str);
        int count = contentView.getLayout() == null ? getLineNumber()
                : contentView.getLineCount();
        if (count > defaultLine) {
            contentView.setLines(defaultLine);
            openView.setVisibility(View.VISIBLE);
        } else {
            openView.setVisibility(View.GONE);
        }
    }

    private int getLineNumber() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        contentView.measure(widthMeasureSpec, heightMeasureSpec);
        int lineHeight = contentView.getLineHeight();
        int lineNumber = contentView.getMeasuredHeight() / lineHeight;
        return lineNumber;
    }
//
//    // �ı�״̬�Ľӿ�
//    public interface OnStateChangeListener {
//        void onStateChange(boolean isExpand);
//    }
//
//    public OnStateChangeListener onStateChangeListener;
//
//    public void setOnStateChangeListener(
//            OnStateChangeListener onStateChangeListener) {
//        this.onStateChangeListener = onStateChangeListener;
//    }
//
//    // �ı䵱ǰ��ǵ�ֵ�����жϵ�ǰ���ں���״̬
//    public void setIsExpand(boolean isExpand) {
//        this.isExpand = isExpand;
//        if (isExpand) {
//            contentView.setLines(contentView.getLineCount());
//            openView.setText("����");
//        } else {
//            contentView.setLines(defaultLine);
//            openView.setText("չ��");
//        }
//    }
}

