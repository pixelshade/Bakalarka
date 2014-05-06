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


public class QuestAdapter extends ArrayAdapter<Quest> {

    Context context;

    public QuestAdapter(Context context, int resourceId, List<Quest> quests) {
        super(context, resourceId, quests);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Quest rowQuest = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_quest, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.listItemNameTextView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.listItemImageView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        ImageView questCompletedImage  = (ImageView) convertView.findViewById(R.id.questCompletedImage);
        if(rowQuest.isCompleted()){
            questCompletedImage.setVisibility(View.VISIBLE);
        } else {
            questCompletedImage.setVisibility(View.GONE);
        }

        holder.txtTitle.setText(rowQuest.getName());
        if (rowQuest.getImage().length() != 0) {
            String filePath = Settings.getContentFileDir() + rowQuest.getImage();
            File imageFile = new File(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }
}
