package cn.edu.gdmec.android.boxuegu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.Bean.UserBean;
import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

/**
 * Created by apple on 18/4/10.
 */

public class ActivityUserInfoActivity extends Activity implements View.OnClickListener {
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private ImageView iv_head_icon;
    private RelativeLayout rl_head;
    private TextView tv_user_name;
    private RelativeLayout rl_account;
    private TextView tv_sex;
    private TextView tv_nickName;
    private RelativeLayout rl_nickName;
    private RelativeLayout rl_sex;
    private TextView tv_signature;
    private RelativeLayout rl_signature;
    private String spUserName;
    private String new_info;
    private static final int CHANGE_NICKNAME = 1;
    private static final int CHANGE_SIGNATURE = 2;
    private static final int CHANGE_QQ = 3;

    private TextView tv_QQ;
    private RelativeLayout rl_QQ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName = AnalysisUtils.readLoginUserName(this);
        initView();
        initData();
    }

    private void initData() {
        UserBean bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        if (bean == null) {
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            bean.QQ="未添加";
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        satValue(bean);
    }

    private void satValue(UserBean bean) {
        tv_user_name.setText(bean.userName);
        tv_nickName.setText(bean.nickName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
        tv_QQ.setText(bean.QQ);
    }
    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        title_bar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        rl_head.setOnClickListener(this);
        rl_account = (RelativeLayout) findViewById(R.id.rl_account);
        rl_account.setOnClickListener(this);
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        rl_nickName.setOnClickListener(this);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_sex.setOnClickListener(this);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
        rl_signature.setOnClickListener(this);
        tv_QQ = (TextView) findViewById(R.id.tv_QQ);
        tv_QQ.setOnClickListener(this);
        rl_QQ = (RelativeLayout) findViewById(R.id.rl_QQ);
        rl_QQ.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_nickName:
                //昵称
                String name = tv_nickName.getText().toString();
                Bundle bdName = new Bundle();
                bdName.putString("content", name);
                bdName.putString("title", "昵称");
                bdName.putInt("flag", 1);
                enterActivityForResult(ActivityChangeUserInfoActivity.class, CHANGE_NICKNAME, bdName);
                break;
            case R.id.rl_sex:
                String sex = tv_sex.getText().toString();
                sexDiolag(sex);

                break;
            case R.id.rl_signature:
                //signature
                String signature = tv_signature.getText().toString();
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content", signature);
                bdSignature.putString("title", "签名");
                bdSignature.putInt("flag", 2);
                enterActivityForResult(ActivityChangeUserInfoActivity.class, CHANGE_SIGNATURE, bdSignature);
                break;
            case R.id.rl_QQ:
                //QQ号
                String QQ = tv_QQ.getText().toString();
                Bundle bdQQ = new Bundle();
                bdQQ.putString("content", QQ);
                bdQQ.putString("title", "QQ号");
                bdQQ.putInt("flag", 3);
                enterActivityForResult(ActivityChangeUserInfoActivity.class, CHANGE_QQ, bdQQ);
                break;
        }
    }
    private void sexDiolag(String sex) {
        int sexFlag = 0;
        if ("男".equals(sex)) {
            sexFlag = 0;
        } else if ("女".equals(sex)) {
            sexFlag = 1;
        }
        final String item[] = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(item, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ActivityUserInfoActivity.this, item[which], Toast.LENGTH_SHORT).show();
                setSex(item[which]);
            }
        });
        builder.create().show();

    }

    private void setSex(String s) {
        tv_sex.setText(s);
        DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("sex", s, spUserName);
    }

    public void enterActivityForResult(Class<?> to, int requestcode, Bundle b) {
        Intent i = new Intent(this, to);
        i.putExtras(b);
        startActivityForResult(i, requestcode);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHANGE_NICKNAME:
                if (data != null) {
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    Log.i("nickname",new_info);
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("nickName", new_info, spUserName);
                }
                break;
            case CHANGE_SIGNATURE:
                if (data != null) {
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_signature.setText(new_info);
                    DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("signature", new_info, spUserName);
                }
                break;
            case CHANGE_QQ:
                if (data != null) {
                    new_info = data.getStringExtra("QQ");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_QQ.setText(new_info);
                    Log.i("dsa","QQ"+new_info);
                    DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("QQ", new_info, spUserName);
                }
                break;
        }
    }
}
