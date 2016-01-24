package com.fernflower.orderbook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.adapters.AdapterLvEditOrder;
import com.fernflower.orderbook.entities.CatalogItem;
import com.fernflower.orderbook.entities.OrderItem;
import com.fernflower.orderbook.helpers.DialogHelper;
import com.fernflower.orderbook.helpers.ListsLayerHelper;
import com.fernflower.orderbook.helpers.StringHelper;

import java.util.ArrayList;

/**
 * Created by SONY on 08.09.2015.
 */
public class EditOrderFragment extends Fragment {
    /*Форматирование строки, в цене должно умещаться не меньше 100 000.00*/
    /*Сделать сохранение в базу Имени списка и элементов (отдельно)*/

    private Bundle args;
    private ArrayList<OrderItem> editOrderItems;
    private OrderItem currentOrderItem;
    private AdapterLvEditOrder adapterLvEditOrder;
    private Integer position = null;
    private boolean isFormVisible = false;

    ListView itemsListView;
    EditText findByCode;
    Button searchButton;
    Button editAddButton;
    TextView itemName;
    TextView percentSign;
    EditText itemAmount;
    EditText itemDiscount;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_order_fragment, container, false);
        init(rootView, inflater);
        return rootView;
    }

    private void init(View view, LayoutInflater inflater) {
        TextView orderTitle = (TextView) view.findViewById(R.id.txtEditOrderName);
        String orderName = args.getString(StringHelper.ORDER_NAME_TAG);
        orderTitle.setText(orderTitle.getText() + " " + orderName);
        findByCode = (EditText) view.findViewById(R.id.edit_find_code);
        searchButton = (Button) view.findViewById(R.id.edit_search_button);
        editAddButton = (Button) view.findViewById(R.id.edit_add_button);
        itemName = (TextView) view.findViewById(R.id.txtEditItemName);
        percentSign = (TextView) view.findViewById(R.id.edit_percent_sign);
        itemAmount = (EditText) view.findViewById(R.id.edit_item_amount);
        itemDiscount = (EditText) view.findViewById(R.id.edit_item_discount);
        initListView(view, inflater);
        initListeners();
        tempGetItems();
    }

    private void tempGetItems() {
        editOrderItems.addAll(ListsLayerHelper.getInstance().getOrderListItems());
        adapterLvEditOrder.notifyDataSetChanged();
    }


    private void initListView(View view, LayoutInflater inflater) {
        itemsListView = (ListView) view.findViewById(R.id.edit_order_lv);
        View header = inflater.inflate(R.layout.edit_order_list_header, null);
        itemsListView.addHeaderView(header);

        editOrderItems = new ArrayList<>();
        adapterLvEditOrder = new AdapterLvEditOrder(getActivity(), R.layout.edit_order_item_row, editOrderItems);
        itemsListView.setAdapter(adapterLvEditOrder);
    }

    private void initListeners() {
        editAddButton.setOnClickListener(new ButtonsListener());
        searchButton.setOnClickListener(new ButtonsListener());
        itemsListView.setOnItemClickListener(new ItemClickListener());
    }

    private void setAddFormVisible() {
        editAddButton.setVisibility(View.VISIBLE);
        itemName.setVisibility(View.VISIBLE);
        percentSign.setVisibility(View.VISIBLE);
        itemAmount.setVisibility(View.VISIBLE);
        itemDiscount.setVisibility(View.VISIBLE);
        isFormVisible = true;
    }

    private void setAddFormInvisible() {
        editAddButton.setVisibility(View.INVISIBLE);
        itemName.setVisibility(View.INVISIBLE);
        percentSign.setVisibility(View.INVISIBLE);
        itemAmount.setVisibility(View.INVISIBLE);
        itemDiscount.setVisibility(View.INVISIBLE);
        isFormVisible = false;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    private void searchItemByCode() {
        if (DialogHelper.getInstance().checkEditOnText(findByCode)) {
            Integer code = null;
            try {
                code = Integer.parseInt(findByCode.getText().toString());
            } catch (NumberFormatException ex) {
                StringHelper.getInstance().showError(getActivity(), getString(R.string.error_code_is_not_number));
            }
            if (code != null) {

                for (CatalogItem item : ListsLayerHelper.getInstance().getCatalogItems()) {
                    if(item.getItemCode() == code){
                        currentOrderItem = new OrderItem(item.getItemCode(), item.getItemName(), item.getItemPrice());
                        break;
                    }else{
                        currentOrderItem = null;
                    }
                }

                if(currentOrderItem!=null){
                    setAddFormVisible();
                    itemName.setText(currentOrderItem.getItemName());
                    itemAmount.setHint(String.valueOf(currentOrderItem.getItemAmount()));
                    itemDiscount.setHint(String.valueOf(currentOrderItem.getItemDiscount()));
                }else{
                    StringHelper.getInstance().showHint(getActivity(),getString(R.string.item_not_found));
                }
            }
        } else {
            StringHelper.getInstance().showHint(getActivity(), getString(R.string.empty_search_by_code));
        }
    }

    private void addOrEditItem(){
        if(currentOrderItem!=null){
            //Сначала сделать update в DB
            currentOrderItem.setItemAmount(Integer.parseInt(StringHelper.getInstance().getEditTextContent(itemAmount)));
            currentOrderItem.setItemDiscount(Float.parseFloat(StringHelper.getInstance().getEditTextContent(itemDiscount)));
            if(position==null){
                editOrderItems.add(currentOrderItem);
            }else{
                this.position=null;
            }
            adapterLvEditOrder.notifyDataSetChanged();
            claerEditText();
        }else{
            StringHelper.getInstance().showError(getActivity(), getString(R.string.error_current_item_is_null));
        }
    }

    private void claerEditText(){
        itemAmount.setText("");
        itemDiscount.setText("");
        if(DialogHelper.getInstance().checkEditOnText(findByCode)){findByCode.setText("");}
    }

    private class ButtonsListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_search_button:
                    searchItemByCode();
                    break;
                case R.id.edit_add_button:
                    addOrEditItem();
                    setAddFormInvisible();
                    break;
                default:
                    break;
            }
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setPosition(position);
            currentOrderItem = (OrderItem) parent.getAdapter().getItem(position);
            if (!isFormVisible) {
                setAddFormVisible();
            }
            itemName.setText(currentOrderItem.getItemName());
            itemAmount.setHint(String.valueOf(currentOrderItem.getItemAmount()));
            itemDiscount.setHint(String.valueOf(currentOrderItem.getItemDiscount()));
        }
    }
}
