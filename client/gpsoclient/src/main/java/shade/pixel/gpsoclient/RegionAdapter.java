package shade.pixel.gpsoclient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by pixelshade on 6.5.2014.
 */

public class RegionAdapter extends ArrayAdapter<Region> {

    Context context;

    public RegionAdapter(Context context, int resourceId, List<Region> regions) {
        super(context, resourceId, regions);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Region rowRegion = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_region, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.listRegionNameTextView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listRegionImageView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowRegion.getName());
        if (rowRegion.getImage().length() != 0) {
            String filePath = Settings.getContentFileDir() + rowRegion.getImage();
            File imageFile = new File(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }
}