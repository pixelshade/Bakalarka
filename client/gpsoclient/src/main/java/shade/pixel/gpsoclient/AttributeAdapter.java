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
public class AttributeAdapter extends ArrayAdapter<Attribute> {

    Context context;

    public AttributeAdapter(Context context, int resourceId, List<Attribute> attributes) {
        super(context, resourceId, attributes);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtAmount;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Attribute rowAttribute = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_attribute, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.listAttributeNameTextView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listAttributeImageView);
            holder.txtAmount = (TextView) convertView.findViewById(R.id.attributeAmountTextView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowAttribute.getName());
        holder.txtAmount.setText(""+rowAttribute.getAmount());

        if (rowAttribute.getImage().length() != 0) {
            String filePath = Settings.getContentFileDir() + rowAttribute.getImage();
            File imageFile = new File(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
