package tableshop.ilinkedtech.com.utils.imag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import tableshop.ilinkedtech.com.utils.FileUtil;
import tableshop.ilinkedtech.com.utils.MD5Util;

/**
 * Wilson 2017/03/31
 * 使用Glide的圆角图片的实现
 */
public class GlideSaveToLocalTransform
        extends BitmapTransformation
{
    private String filePath;

    public GlideSaveToLocalTransform(Context context, String url) {
        super(context);
        this.filePath = FileUtil.getImgFilePath(MD5Util.MD5(url));
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private  Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        ImageLoadUtils.saveBitmap(source,filePath);
        return source;
    }

    private  Bitmap circleCrop2(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}