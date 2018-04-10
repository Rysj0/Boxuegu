package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * Created by apple on 18/4/10.
 */

public class ActivityChangeUserInfoActivity extends AppCompatActivity {
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private ImageView iv_delete;
    private TextView tv_save;

    private String title, content;
    private int flag;
    private EditText et_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        initView();

    }

    private void initView() {
        content = getIntent().getStringExtra("content");
        flag = getIntent().getIntExtra("flag", 0);
        title=getIntent().getStringExtra("title");
        et_content = (EditText) findViewById(R.id.et_content);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        title_bar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        tv_save = (TextView) findViewById(R.id.tv_save);

        tv_save.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(content)) {
            et_content.setText(content);
            et_content.setSelection(content.length());
        }
        if (flag==3){
            et_content.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        contentListener();


        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityChangeUserInfoActivity.this.finish();
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_content.setText("");
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String etContent = et_content.getText().toString().trim();
                switch (flag) {
                    case 1:
                        if (!TextUtils.isEmpty(etContent)) {
                            data.putExtra("nickName", etContent);
                            setResult(RESULT_OK, data);
                            Toast.makeText(ActivityChangeUserInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            ActivityChangeUserInfoActivity.this.finish();
                        } else {
                            Toast.makeText(ActivityChangeUserInfoActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(etContent)) {
                            data.putExtra("signature", etContent);
                            setResult(RESULT_OK, data);
                            Toast.makeText(ActivityChangeUserInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            ActivityChangeUserInfoActivity.this.finish();
                        } else {
                            Toast.makeText(ActivityChangeUserInfoActivity.this, "签名不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (!TextUtils.isEmpty(etContent)) {
                            data.putExtra("QQ", etContent);
                            setResult(RESULT_OK, data);
                            Toast.makeText(ActivityChangeUserInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            ActivityChangeUserInfoActivity.this.finish();
                        } else {
                            Toast.makeText(ActivityChangeUserInfoActivity.this, "QQ号不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }

    private void contentListener() {
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = et_content.getText();
                int len = editable.length();
                if (len > 0) {
                    iv_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_delete.setVisibility(View.GONE);
                }
                switch (flag) {
                    case 1:

                        if (len > 8) {
                            int selEndIndex = Selection.getSelectionEnd(editable);
                            String str = editable.toString();

                            String newStr = str.substring(0, 8);
                            et_content.setText(newStr);
                            editable = et_content.getText();
                            int newLen = editable.length();
                            if (selEndIndex > newLen) {
                                selEndIndex = editable.length();
                            }
                            Selection.setSelection(editable, selEndIndex);
                        }
                        break;
                    case 2:
                        if (len > 16) {
                            int selEndIndex = Selection.getSelectionEnd(editable);
                            String str = editable.toString();

                            String newStr = str.substring(0, 16);
                            et_content.setText(newStr);
                            editable = et_content.getText();

                            int newLen = editable.length();

                            if (selEndIndex > newLen) {
                                selEndIndex = editable.length();
                            }
                            Selection.setSelection(editable, selEndIndex);
                        }
                        break;
                    case 3:

                        if (len > 12) {
                            int selEndIndex = Selection.getSelectionEnd(editable);
                            String str = editable.toString();

                            String newStr = str.substring(0, 12);
                            et_content.setText(newStr);
                            editable = et_content.getText();

                            int newLen = editable.length();

                            if (selEndIndex > newLen) {
                                selEndIndex = editable.length();
                            }
                            Selection.setSelection(editable, selEndIndex);
                        }

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
