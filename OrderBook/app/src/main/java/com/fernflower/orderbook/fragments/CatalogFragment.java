package com.fernflower.orderbook.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.SwipeListView.BaseSwipeListViewListener;
import com.fernflower.orderbook.SwipeListView.SwipeListView;
import com.fernflower.orderbook.adapters.AdapterSwipeCatalogItems;
import com.fernflower.orderbook.entities.CatalogItem;
import com.fernflower.orderbook.helpers.DialogHelper;
import com.fernflower.orderbook.helpers.ListsLayerHelper;
import com.fernflower.orderbook.helpers.StringHelper;
import com.fernflower.orderbook.settings.AppSettings;

import java.util.ArrayList;

/**
 * Created by SONY on 06.07.2015.
 */
public class CatalogFragment extends Fragment {
    SwipeListView swipeItemsList;
    AdapterSwipeCatalogItems adapter;

    SearchView searchItems;
    private ArrayList<CatalogItem> catalogItems ;
    private CatalogItem item;

    private Context context;
    private Dialog addItem;

    private EditText itemCode;
    private EditText itemName;
    private EditText itemPrice;
    private EditText itemDescribe;
    protected static final int RESULT_SPEACH=1;
    private String voiceText;
    private String tempRes="";

    public ArrayList<EditText> currentEdits;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        catalogItems = ListsLayerHelper.getInstance().getCatalogItems();
        currentEdits = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.catalog_fragment, container, false);
        init(rootView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_catalog, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.catalog_add_item :
                addItemDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;
    }

    private void init(View view){
        swipeItemsList=(SwipeListView)view.findViewById(R.id.swipe_catalog_items);
        adapter=new AdapterSwipeCatalogItems(context, R.layout.catalog_item_row, catalogItems, this);

        swipeItemsList.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {

            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                //swipelistview.openAnimate(position); //when you touch front view it will open
                StringHelper.getInstance().showDescription(context, String.valueOf(catalogItems.get(position).getItemDescribe()));
            }

            @Override
            public void onClickBackView(int position) {
                swipeItemsList.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                /*for(int position : reverseSortedPositions){
                    catalogItems.remove(position);
                }
                adapter.notifyDataSetChanged();*/
            }
        });

        //setting as your requrement
        swipeItemsList.setSwipeMode(SwipeListView.SWIPE_MODE_DEFAULT); // there are five swiping modes
        swipeItemsList.setOffsetLeft(ListsLayerHelper.getInstance().convertDpToPixel(context, 30f)); // left side offset
        swipeItemsList.setOffsetRight(ListsLayerHelper.getInstance().convertDpToPixel(context, 30f)); // right side offset
        swipeItemsList.setAnimationTime(200); // animarion time
        swipeItemsList.setSwipeOpenOnLongPress(true); // en
        swipeItemsList.setTextFilterEnabled(true);

        //swipeItemsList.setEmptyView();
        swipeItemsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchItems=(SearchView)view.findViewById(R.id.catalog_search_item_name);
        setupSearchView();
    }

    private void setupSearchView(){
        searchItems.setIconifiedByDefault(false);
        searchItems.setSubmitButtonEnabled(true);
        searchItems.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StringHelper.getInstance().showHint(context, "onQueryTextSubmit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StringHelper.getInstance().showHint(context, "onQueryTextChange:" + newText);
                if (TextUtils.isEmpty(newText)) {
                    adapter.getFilter().filter("");
                } else {
                    adapter.getFilter().filter(newText.toString());
                }
                return true;
            }
        });
    }



    public void addItemDialog(){
        addItem = new Dialog(context);
        addItem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addItem.setContentView(R.layout.dialog_add_edit_catalog_item);
        //Инициализируем все поля диалового окна
        itemCode = (EditText) addItem.findViewById(R.id.ci_dialog_item_code);
        itemName = (EditText) addItem.findViewById(R.id.ci_dialog_item_name);
        itemPrice = (EditText) addItem.findViewById(R.id.ci_dialog_item_price);
        itemDescribe = (EditText) addItem.findViewById(R.id.ci_dialog_item_describe);

        setCurrentEdits(itemCode, itemName, itemPrice, itemDescribe);

        ImageButton microphone = (ImageButton) addItem.findViewById(R.id.ci_dialog_microphone_button);
        ImageButton cancel = (ImageButton) addItem.findViewById(R.id.ci_dialog_cancel_button);
        ImageButton ok = (ImageButton) addItem.findViewById(R.id.ci_dialog_ok_button);

        if(AppSettings.getInstance().isItemAutocode()){
            itemName.requestFocus();
        }else{
            itemCode.setEnabled(true);
            itemCode.requestFocus();
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem.cancel();
            }
        });

        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                if(itemCode.isFocused() || itemName.isFocused() || itemDescribe.isFocused() || itemPrice.isFocused() ){
                    try{
                        startActivityForResult(intent, RESULT_SPEACH);

                    }catch (ActivityNotFoundException e){
                        StringHelper.getInstance().showError(context,context.getResources().getString(R.string.error_voice_not_supported));
                    }
                }else{
                    StringHelper.getInstance().showError(context,context.getResources().getString(R.string.voice_no_choose_input));
                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code=DialogHelper.getInstance().returnItemCode(itemCode);
                boolean isAllFilled=DialogHelper.getInstance().checkEditOnText(itemCode, itemName, itemPrice, itemDescribe);
                if(isAllFilled){
                    String name=itemName.getText().toString();
                    float price=Float.parseFloat(itemPrice.getText().toString());
                    String description=itemDescribe.getText().toString();
                    item=new CatalogItem(code,name,price,description);
                    catalogItems.add(item);
                    adapter.changeArrayData(catalogItems);
                    adapter.notifyDataSetChanged();
                    ListsLayerHelper.getInstance().setCatalogItems(catalogItems);
                    addItem.dismiss();
                }else{
                    StringHelper.getInstance().showHint(context, context.getString(R.string.no_data_from_inputs));
                }
            }
        });

        addItem.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEACH:
                try {
                    if (resultCode == getActivity().RESULT_OK) {
                        ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        for(EditText edit: currentEdits){
                            if(edit.isEnabled()) {
                                if (edit.isFocused()) {
                                    DialogHelper.getInstance().setTextInChosen(edit, text.get(0).toString());
                                }
                            }
                        }

                        text.clear();
                    }
                }catch (Exception e){
                    StringHelper.getInstance().showHint(context,e.toString());
                }
                break;

            default:
                break;
        }
    }



    public void setCurrentEdits(EditText... edits){
        clearCurrentEdits();
        for(EditText e : edits){
            currentEdits.add(e);
        }
    }

    public void clearCurrentEdits(){
        if(!currentEdits.isEmpty()){
            currentEdits.clear();
        }
    }

    public void editItemdata(int position, CatalogItem item){
        if(!catalogItems.isEmpty()){
            catalogItems.remove(position);
            catalogItems.add(position, item);
            adapter.changeArrayData(catalogItems);
            adapter.notifyDataSetChanged();
            ListsLayerHelper.getInstance().setCatalogItems(catalogItems);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
