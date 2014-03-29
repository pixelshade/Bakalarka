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
 * Created by pixelshade on 29.3.2014.
 */


public class ItemAdapter extends ArrayAdapter<Item> {

    Context context;

    public ItemAdapter(Context context, int resourceId, List<Item> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Item rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.listItemNameTextView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listItemImageView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.txtTitle.setText(rowItem.getName());
        if (rowItem.getImage().length() != 0) {
            String filePath = Settings.getContentFileDir() + rowItem.getImage();
            File imageFile = new File(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }
}
