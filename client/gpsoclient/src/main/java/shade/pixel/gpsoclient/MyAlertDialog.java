package shade.pixel.gpsoclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class MyAlertDialog extends DialogFragment {
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    public static final String ARG_QUEST = "quest";
    public static final String ARG_REWARD = "reward";
    public static final String ARG_REGION = "region";
    public static final String ARG_ITEM = "item";
    public static final String ARG_RESPONSE = "response";
    public static final String ARG_TITLE = "title";
    public static final String ARG_MESSAGE = "message";

    public static MyAlertDialog newInstance(String title, String message) {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        frag.setArguments(args);
        return frag;
    }

    public static MyAlertDialog newInstance(Reward reward) {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REWARD, reward);
        frag.setArguments(args);
        return frag;
    }

    public static MyAlertDialog newInstance(Region region) {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REGION, region);
        frag.setArguments(args);
        return frag;
    }

    public static MyAlertDialog newInstance(Item item) {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM, item);
        frag.setArguments(args);
        return frag;
    }

    public static MyAlertDialog newInstance(Quest quest) {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUEST, quest);
        frag.setArguments(args);
        return frag;
    }

    public static MyAlertDialog newInstance(Response response) {
        MyAlertDialog frag = new MyAlertDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESPONSE, response);
        frag.setArguments(args);
        return frag;
    }






    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = "";
        String message = "";
        if (args != null) {
            Quest quest = (Quest) getArguments().getSerializable(ARG_QUEST);
            Reward reward = (Reward) getArguments().getSerializable(ARG_REWARD);
            Region region = (Region) getArguments().getSerializable(ARG_REGION);
            Item item = (Item) getArguments().getSerializable(ARG_ITEM);
            Response response = (Response) getArguments().getSerializable(ARG_RESPONSE);
            title = getArguments().getString(ARG_TITLE);
            message = getArguments().getString(ARG_MESSAGE);
            inflater = getActivity().getLayoutInflater();
            builder = new AlertDialog.Builder(getActivity());
            if(item != null){
                showReceivedItem(item);
            } else if (quest != null) {
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


    private void showReceivedItem(Item item){
        if(item != null) {
            if (builder != null) {
                builder.setTitle("You have received item.");

                View rootView = inflater.inflate(R.layout.fragment_item_info, null);
                ImageView dialogImageView = (ImageView) rootView.findViewById(R.id.itemImageView);
                TextView itemAmountText = (TextView) rootView.findViewById(R.id.itemInfoAmountTextView);
                TextView itemNameText = (TextView) rootView.findViewById(R.id.itemInfoNameText);
                TextView itemInfoText = (TextView) rootView.findViewById(R.id.itemInfoText);

                itemNameText.setText(item.getName());
                itemInfoText.setText(Html.fromHtml(item.getInfo()));
                itemAmountText.setText(item.getAmount()+"x");


                if (item.getImage().length() != 0) {
                    String filePath = Settings.getContentFileDir() + item.getImage();
                    File imageFile = new File(filePath);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    dialogImageView.setImageBitmap(bitmap);
                }

                builder.setView(rootView);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

            }
        }
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

    public void showReward(Reward reward) {
        if (reward != null) {
            String title = reward.getName();
            builder.setMessage(title);
            builder.setView(inflater.inflate(R.layout.fragment_reward_info, null));
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
