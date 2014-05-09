package shade.pixel.gpsoclient;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class QuestFilter extends Filter {
    private QuestAdapter questAdapter;
    public QuestFilter(QuestAdapter questAdapter){
        this.questAdapter = questAdapter;
    }
    private boolean showAvailable = true;
    private boolean showActive = true;
    private boolean showComplete = true;

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        constraint = constraint.toString().toLowerCase();
        FilterResults result = new FilterResults();
        ArrayList<Quest> filterList = new ArrayList<Quest>();
//        if (constraint != null && constraint.toString().length() > 0) {
            ArrayList<Quest> orginalList = new ArrayList<Quest>(questAdapter.getQuests());
        Log.i("DEBUG", orginalList.toString());


            for (Quest q : orginalList) {
               if(q.isActive()){
                   if(showActive) filterList.add(q);
                   if(q.isCompleted()){
                       if(showComplete) filterList.add(q);
                   }
               } else {
                   if(showAvailable){
                       filterList.add(q);
                   }
               }
            }
            Log.i("DEBUG", filterList.toString());
            result.values = filterList;
            result.count = filterList.size();

//        } else {
//            result.values = questAdapter.getQuests();
//            result.count = questAdapter.getQuests().size();
//        }
        return result;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        List<Quest> filteredQuests = (List<Quest>) results.values;
        questAdapter.notifyDataSetChanged();
        questAdapter.clear();
        int count = filteredQuests.size();
        for (int i = 0; i < count; i++) {
            questAdapter.add(filteredQuests.get(i));
            questAdapter.notifyDataSetInvalidated();
        }
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
