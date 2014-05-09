package shade.pixel.gpsoclient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixelshade on 29.3.2014.
 */


public class QuestAdapter extends ArrayAdapter<Quest> {

    private Context context;
    private List<Quest> quests;
    private QuestFilter mFilter;

    private boolean showAvailable = true;
    private boolean showActive = true;
    private boolean showComplete = true;


    public QuestAdapter(Context context, int resourceId, List<Quest> quests) {
        super(context, resourceId, quests);
        this.context = context;
        this.quests = quests;
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

        ImageView questCompletedImage  = (ImageView) convertView.findViewById(R.id.questStatusImage);
        if(rowQuest.isCompleted()){
            questCompletedImage.setImageResource(R.drawable.icon_completed);
            questCompletedImage.setVisibility(View.VISIBLE);
        } else {
            if (rowQuest.isActive()) {
                questCompletedImage.setImageResource(R.drawable.icon_active);
                questCompletedImage.setVisibility(View.VISIBLE);
            } else {
                questCompletedImage.setVisibility(View.GONE);
            }
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

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new QuestFilter(this);
        }
        mFilter.setShowActive(showActive);
        mFilter.setShowAvailable(showAvailable);
        mFilter.setShowComplete(showComplete);
        return mFilter;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public boolean isShowAvailable() {
        return showAvailable;
    }

    public void setShowAvailable(boolean showAvailable) {
        this.showAvailable = showAvailable;
    }

    public boolean isShowActive() {
        return showActive;
    }

    public void setShowActive(boolean showActive) {
        this.showActive = showActive;
    }

    public boolean isShowComplete() {
        return showComplete;
    }

    public void setShowComplete(boolean showComplete) {
        this.showComplete = showComplete;
    }
}


