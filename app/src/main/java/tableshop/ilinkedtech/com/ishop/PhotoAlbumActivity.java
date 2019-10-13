package tableshop.ilinkedtech.com.ishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import tableshop.ilinkedtech.com.R;
import tableshop.ilinkedtech.com.others.photos.AlbumListAdapter;
import tableshop.ilinkedtech.com.others.photos.AlbumModel;
import tableshop.ilinkedtech.com.others.photos.PhotoSelectorHelper;
import tableshop.ilinkedtech.com.utils.UIUtils;

/*
 *  @项目名：  ICan2--kuangjia
 *  @包名：    com.ilinkedtech.ican
 *  @文件名:   PhotoAlbumActivity
 *  @创建者:   Wilson
 *  @描述：    TODO 相册列表选择类
 */
public class PhotoAlbumActivity
        extends AppCompatActivity
        implements PhotoSelectorHelper.OnLoadAlbumListener, AdapterView.OnItemClickListener {
    private PhotoSelectorHelper mHelper;
    private ListView            mListView;
    private AlbumListAdapter    mAdapter;
    public static final String ALBUM_NAME ="album_name";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        ActionBar actionBar =getSupportActionBar();//todo 0
        if(actionBar!=null){
            actionBar.setTitle(UIUtils.getString(R.string.选择相册));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mListView= (ListView) this.findViewById(R.id.lv_show_album);
        mListView.setAdapter(mAdapter=new AlbumListAdapter(this) );
        mListView.setOnItemClickListener(this);
        mHelper=new PhotoSelectorHelper(this);
        mHelper.getAlbumList(this);
    }

    @Override
    public void onAlbumLoaded(List<AlbumModel> albums) {
        mAdapter.notifyDataSetChanged(albums,true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent =new Intent();
        intent.putExtra(ALBUM_NAME,mAdapter.getItem(position).getName());
        setResult(RESULT_OK,intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
