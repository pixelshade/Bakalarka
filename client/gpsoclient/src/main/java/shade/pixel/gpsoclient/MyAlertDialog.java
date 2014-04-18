package shade.pixel.gpsoclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;


public class MyAlertDialog extends DialogFragment {
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = "";
        String message = "";
        if (args != null) {
            Quest quest = (Quest) getArguments().getSerializable("quest");
            Reward reward = (Reward) getArguments().getSerializable("reward");
            Region region = (Region) getArguments().getSerializable("region");
            Response response = (Response) getArguments().getSerializable("response");
            title = getArguments().getString("title");
            message = getArguments().getString("message");
            inflater = getActivity().getLayoutInflater();
            builder = new AlertDialog.Builder(getActivity());

            if (quest != null) {
                showQuest(quest);
            } else if (reward != null) {
                showReward(reward);
            } else if (region != null) {
                showRegion(region);
            } else if (response != null) {
                showResponseMessage(response);
            } else if (title != null || message != null) {
                showMessage(title, message);
            }

            return builder.create();
        }

        return null;
    }


    private void showQuest(Quest quest) {
        if (quest != null) {
            String title = quest.getName();
            builder.setMessage(title);
            builder.setView(inflater.inflate(R.layout.fragment_my_alert_dialog, null));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            });
//            builder.setNegativeButton("GG", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });
        }
    }

    private void showReward(Reward reward) {
        if (reward != null) {
            String title = reward.getName();
            builder.setMessage(title);
            builder.setView(inflater.inflate(R.layout.fragment_my_alert_dialog, null));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            });
        }
    }

    private void showRegion(Region region) {
        if (region != null) {
            String title = region.getName();
            builder.setMessage(title);
            builder.setView(inflater.inflate(R.layout.fragment_my_alert_dialog, null));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            });
        }
    }

    private void showMessage(String title, String message) {
        if (title != null && !title.equals("")) {
            builder.setTitle(title);
        }
        if (message != null && !message.equals("")) {
            builder.setMessage(message);
        }
//            builder.setView(inflater.inflate(R.layout.fragment_my_alert_dialog, null));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // FIRE ZE MISSILES!
            }
        });

    }

    private void showResponseMessage(Response response) {
        if (response != null) {
            String title = response.getType();
            String message = response.getMessage();
            builder.setMessage(title);
            builder.setView(inflater.inflate(R.layout.fragment_my_alert_dialog, null));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            });
        }
    }


}
