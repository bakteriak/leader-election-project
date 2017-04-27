package uk.ac.qub.leaderelectiongame.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.qub.leaderelectiongame.R;


/**
 * Server messages list adapter. Extends BaseAdapter.
 */
public class GameAdapter extends BaseAdapter {

    /**
     * Server messages list.
     */
    private final ArrayList<String> mListItems;

    /**
     * Layout inflater.
     */
    private final LayoutInflater mLayoutInflater;

    /**
     * Constructor.
     * @param context
     * @param arrayList
     */
    public GameAdapter(Context context, ArrayList<String> arrayList){
        mListItems = arrayList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Method to get items on the list count.
     * @return items on the list count
     */
    @Override
    public int getCount() {
        return mListItems.size();
    }

    /**
     * Item at index i getter. Not implemented.
     * @param i
     * @return item at index i
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * Position id at index i getter. Not implemented.
     * @param i
     * @return position id at index i
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Method to set item text on given position and return view.
     * @param position
     * @param view
     * @param viewGroup
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.list_item_game, null);
        }
        String stringItem = mListItems.get(position);
        if (stringItem != null) {
            TextView itemName = (TextView) view.findViewById(R.id.txtGameInfo);
            if (itemName != null) {
                itemName.setText(stringItem);
            }
        }
        return view;
    }
}