package tableshop.ilinkedtech.com.ishop;

/*
 *  @文件名:   RegisterActivity
 *  @创建者:   Wilson
 *  @创建时间:  2017/11/27 11:20
 *  @描述：    TODO
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

import tableshop.ilinkedtech.com.MainActivity;
import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.base.BaseActionBarActivity;
import tableshop.ilinkedtech.com.base.IShopBaseFragment;
import tableshop.ilinkedtech.com.consts.KeyConst;
import tableshop.ilinkedtech.com.db.SharedStorage;
import tableshop.ilinkedtech.com.fragments.actfragments.ActRegisterFragment2;
import tableshop.ilinkedtech.com.others.photos.PictureUtil;
import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.StringUtils;
import tableshop.ilinkedtech.com.utils.ToastUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;
import tableshop.ilinkedtech.com.utils.imag.ImageLoadUtils;

public class RegisterActivity extends BaseActionBarActivity {

    private ActRegisterFragment2 mRegisterFragment2;

    @Override
    protected IShopBaseFragment getShowFragment() {
        String customerInfo = SharedStorage.getInstance(this)
                                           .getCustomerInfo();
        if (!StringUtils.isEmpty(customerInfo)){
            mTvToolbarTitle.setText(UIUtils.getString(R.string.个人资料));
        }
        mRegisterFragment2 = new ActRegisterFragment2();
        return mRegisterFragment2;
    }

    @Override
    public void doNavigationAction() {
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
        UIUtils.activityBackToMain(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            switch (requestCode) {
                case KeyConst.RequestCode.SELETED_AVATAR://单选
                    if (data.hasExtra(PhotoPickActivity.SELECT_PHOTO_LIST)) {
                        List<String> list1 = data.getStringArrayListExtra(PhotoPickActivity.SELECT_PHOTO_LIST);
                        if (list1 != null && list1.size() > 0) {
                            PictureUtil.cropPhotoRetrunBitmap(this,
                                                              true,
                                                              list1.get(0),
                                                              KeyConst.FilePath.UPLOAD_PICTURE_SIZE,
                                                              KeyConst.RequestCode.SELETED_AVATAR_NEXT);
                        }
                    }

                    break;
                case KeyConst.RequestCode.SELETED_AVATAR_NEXT://单选回传
                    Bitmap data1 = data.getParcelableExtra("data");
                    String filPath = FileUtil.getLeadAvatarPath(SharedStorage.userName);
                    ImageLoadUtils.saveBitmap(data1, filPath);
                    File file = new File(filPath);
                    //                ToastUtil.toast((mRegisterFragment2==null)+"==file:"+(file==null)+"===filPath:"+filPath);
                    if (mRegisterFragment2 != null && data1 != null) {
                        mRegisterFragment2.setAvatarFilePath(data1, filPath);
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==KeyConst.RequestCode.REQUEST_READ_EXTERNAL_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                if (mRegisterFragment2!=null){
                    mRegisterFragment2.doUpLoadAvatar();
                }
            }else {
                ToastUtil.toast("申请读取手机存储权限被拒绝");
            }
        }

    }
}
