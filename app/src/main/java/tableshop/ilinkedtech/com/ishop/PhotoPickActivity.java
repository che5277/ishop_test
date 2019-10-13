package tableshop.ilinkedtech.com.ishop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.others.photos.AlbumController;
import tableshop.ilinkedtech.com.others.photos.DisplayImageViewAdapter;
import tableshop.ilinkedtech.com.others.photos.ImageLoader;
import tableshop.ilinkedtech.com.others.photos.PhotoGalleyAdapter;
import tableshop.ilinkedtech.com.others.photos.PhotoSelectorHelper;
import tableshop.ilinkedtech.com.others.photos.PictureUtil;
import tableshop.ilinkedtech.com.others.photos.UriUtil;
import tableshop.ilinkedtech.com.utils.UIUtils;

/*
 *  @项目名：  ICan2--kuangjia
 *  @包名：    com.ilinkedtech.ican
 *  @文件名:   PhotoPickActivity
 *  @创建者:   Wilson
 *  @描述：    TODO 图片预览类 最近照片
 */
public class PhotoPickActivity
        extends AppCompatActivity
        implements PhotoSelectorHelper.OnLoadPhotoListener, AdapterView.OnItemClickListener {
    private PhotoSelectorHelper mHelper;
    private GridView            mGridView;
    private PhotoGalleyAdapter  mGalleyAdapter;
    private View                mPickAlbumView;
    private TextView            mCountText;
    public static final  String MAX_PICK_COUNT    ="max_pick_count";
    public static final  String IS_SHOW_CAMERA    ="is_show_camera";
    public static final  String SELECT_PHOTO_LIST ="select_photo_list";
    private static final int    TO_PICK_ALBUM     =1;
    private static final int    TO_PRIVIEW_PHOTO  =2;
    private static final int    TO_TAKE_PHOTO     =3;

    private boolean isShowCamera;
    private int     maxPickCount;
    private String  mLastAlbumName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pick);
        ActionBar actionBar =getSupportActionBar();//todo 0
        if(actionBar!=null){
            actionBar.setTitle(UIUtils.getString(R.string.最近照片));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        List<String> list =getIntent().getStringArrayListExtra(SELECT_PHOTO_LIST);//delet1
        if(list!=null){
            for(String s:list){
                PhotoGalleyAdapter.mSelectedImage.add(s);
            }
        }
        maxPickCount=getIntent().getIntExtra(MAX_PICK_COUNT,1);
        isShowCamera=getIntent().getBooleanExtra(IS_SHOW_CAMERA,false);
        mGridView= (GridView) this.findViewById(R.id.mp_galley_gridView);
        mGridView.setOnItemClickListener(this);
        mCountText= (TextView) this.findViewById(R.id.tv_to_confirm);
        mPickAlbumView=this.findViewById(R.id.tv_to_album);
        mLastAlbumName= AlbumController.RECENT_PHOTO;
        mHelper=new PhotoSelectorHelper(this);
        mHelper.getReccentPhotoList(this);
        mGridView.setAdapter(mGalleyAdapter=new PhotoGalleyAdapter(this,null,isShowCamera,maxPickCount));

        if(maxPickCount>1){
            mCountText.setVisibility(View.VISIBLE);
        }else{
            mCountText.setVisibility(View.GONE);
        }

        mPickAlbumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(PhotoPickActivity.this, PhotoAlbumActivity.class);//todo 跳到相册列表
                startActivityForResult(intent,TO_PICK_ALBUM);
                UIUtils.activityAnimInt(PhotoPickActivity.this);

            }
        });


        mGalleyAdapter.setOnDisplayImageAdapter(new DisplayImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String path) {
                ImageLoader.getInstance().loadImage(path, imageView);//todo 2
         }

            @Override
            public void onItemImageClick(Context context, int index, List<String> list) {
//                Intent intent =new Intent(PhotoPickActivity.this, PhotoPreviewActivity.class);
//                intent.putExtra(PhotoPreviewActivity.PHOTO_INDEX_IN_ALBUM,index);
//                intent.putExtra(MAX_PICK_COUNT,maxPickCount);
//                intent.putExtra(AlbumPickActivity.ALBUM_NAME,mLastAlbumName);
//                startActivityForResult(intent,TO_PRIVIEW_PHOTO);
            }

            @Override
            public void onImageCheckL(String path, boolean isChecked) {
                updateCountView();
            }
        });


        mCountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDone();
            }
        });
        updateCountView();


    }


    /**
     * 图片加载完成
     * @param photos
     */
    @Override
    public void onPhotoLoaded(List<String> photos) {
        mGalleyAdapter.notifyDataSetChanged(photos,true);//todo 1
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TO_PRIVIEW_PHOTO){//
            updateCountView();
            mGalleyAdapter.notifyDataSetChanged();
        }
        if(resultCode!=RESULT_OK){
            return;
        }

        switch (requestCode){
            case TO_PICK_ALBUM://todo 1选择其中一个相册文件夹执行
                String name=data.getStringExtra(PhotoAlbumActivity.ALBUM_NAME);//todo 2
                if(mLastAlbumName.equals(name)){
                    return;
                }
                if(getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(name);
                }
                mLastAlbumName=name;
                if(name.equals(AlbumController.RECENT_PHOTO)){
                    mHelper.getReccentPhotoList(this);
                }else{
                    mHelper.getAlbumPhotoList(name,this);
                }
                break;

            case TO_PRIVIEW_PHOTO:
                selectDone();
                break;

            case TO_TAKE_PHOTO:
                String url= UriUtil.getAbsolutePathFromUri(this, mUri);
                PictureUtil.notifyGallery(this, url);
                PhotoGalleyAdapter.mSelectedImage.add(url);
                selectDone();
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id ==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Uri mUri;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path =mGalleyAdapter.getItem(position);//todo 3
        if(TextUtils.isEmpty(path)){
            mUri=PictureUtil.takePhoto(this,TO_TAKE_PHOTO);
        }else{
            PhotoGalleyAdapter.mSelectedImage.add(path);//todo 4
            selectDone();
        }
    }


    /**
     * 设置结果返回
     */
    private void selectDone(){
        ArrayList<String> list =new ArrayList<String>();//todo 5
        for(String s:PhotoGalleyAdapter.mSelectedImage){
            list.add(s);//todo 6
        }
        PhotoGalleyAdapter.mSelectedImage.clear();
        Intent intent =new Intent();
        intent.putStringArrayListExtra(SELECT_PHOTO_LIST,list);
        setResult(RESULT_OK,intent);
        finish();
    }


    private void updateCountView(){
        if(PhotoGalleyAdapter.mSelectedImage.size()==0){
            mCountText.setEnabled(false);
        }else{
            mCountText.setEnabled(true);
        }
        mCountText.setText("确定("+PhotoGalleyAdapter.mSelectedImage.size()+"/"+maxPickCount+")");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PhotoGalleyAdapter.mSelectedImage.clear();
    }
}
