package edu.gatech.seclass.grocerylistmanager;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Chithra Shetty on 10/17/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Activity context;
    private List<String> groupList;
    private Map<String, List<GroceryItem>> itemCollection;
    static  private GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
    private GroceryList currentList = listManager.getCurrentList();

    public ExpandableListAdapter(Activity context, List<String> groupList,
                                 Map<String, List<GroceryItem>> itemCollections) {
        this.context = context;
        this.itemCollection = itemCollections;
        this.groupList = groupList;
    }

    public GroceryItem getChild(int groupPosition, int childPosition) {
        return itemCollection.get(groupList.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final GroceryItem item = (GroceryItem) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        } else {
            convertView.setClickable(false);
        }

        TextView itemView = (TextView) convertView.findViewById(R.id.groceryItem);
        TextView quantityView = (TextView) convertView.findViewById(R.id.groceryItemQuantity);

        final CheckBox checkOff = (CheckBox) convertView.findViewById(R.id.checkBox);

        itemView.setText(item.getItemName());
        String quantity = Double.toString(item.getQuantityAmount())+' '+item.getQuantityUnit();
        quantityView.setText(quantity);
        if (item.getCheckOff()) {
            checkOff.setChecked(true);
        } else {
            checkOff.setChecked(false);
        }

        // Handle check off click in child view
        checkOff.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (checkOff.isChecked()) {
                    currentList.checkOffItem(item.getItemID(), true);
                } else {
                    currentList.checkOffItem(item.getItemID(), false);
                }
            }
        });

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return itemCollection.get(groupList.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    public int getGroupCount() {
        return groupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String itemTypeName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        if(convertView != null) {
            convertView.setClickable(false);
        }
        TextView item = (TextView) convertView.findViewById(R.id.itemType_group);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(itemTypeName);

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
