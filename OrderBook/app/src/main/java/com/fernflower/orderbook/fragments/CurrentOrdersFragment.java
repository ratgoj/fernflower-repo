package com.fernflower.orderbook.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.fernflower.orderbook.R;
import com.fernflower.orderbook.SwipeListView.SwipeListView;
import com.fernflower.orderbook.SwipeListView.BaseSwipeListViewListener;
import com.fernflower.orderbook.adapters.AdapterSwipeCurrentOrders;
import com.fernflower.orderbook.datebase.DbLayer;
import com.fernflower.orderbook.entities.OrderListInfo;
import com.fernflower.orderbook.entities.OrderListInfoComparator;
import com.fernflower.orderbook.enums.Priority;
import com.fernflower.orderbook.helpers.DateHelper;
import com.fernflower.orderbook.helpers.ListsLayerHelper;
import com.fernflower.orderbook.helpers.StringHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Фрамггмент отображающий текущие и не выполненые списки заказов.
 */
public class CurrentOrdersFragment extends Fragment {

    //константа для голосового ввода
    protected static final int RESULT_SPEACH=1;
    private EditText voiceResult;

    private boolean isSortedByLow = false;

    private OrderListInfo order=null;
    private ArrayList<OrderListInfo> currentOrders=ListsLayerHelper.getInstance().getCurrentOrders();
    SwipeListView swipelistview;
    AdapterSwipeCurrentOrders adapter;

    private Fragment fragmentScreen;
    private FragmentManager fragmentManager;
    private Bundle args;


    //компоратор по умолчанию (сортировка оп приоритету).
    private static Comparator<OrderListInfo> comparator= OrderListInfoComparator.getPriorityComparator();

    @Override
    public void onCreate(Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        currentOrders=ListsLayerHelper.getInstance().getCurrentOrders();
        fragmentManager=getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.current_orders_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view){
        swipelistview=(SwipeListView)view.findViewById(R.id.swipe_current_orders);
        adapter=new AdapterSwipeCurrentOrders(getActivity(), R.layout.order_row, currentOrders);
        swipelistview.setSwipeListViewListener(new BaseSwipeListViewListener() {
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
                //StringHelper.getInstance().showHint(getActivity(),"onMove pos:"+position+" x:"+x);

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
                // Передача данных(имени заказа) в другой фрагмент.
                goToFragmentWithNameArgs(new ViewOrderFragment(), currentOrders.get(position).getListName(), StringHelper.VIEW_ORDER_NAME_TAG);
            }

            @Override
            public void onClickBackView(int position) {
                swipelistview.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }
        });

        //setting as your requrement
        swipelistview.setSwipeMode(SwipeListView.SWIPE_MODE_DEFAULT); // there are five swiping modes
        swipelistview.setOffsetLeft(ListsLayerHelper.getInstance().convertDpToPixel(getActivity(), 30f)); // left side offset
        swipelistview.setOffsetRight(ListsLayerHelper.getInstance().convertDpToPixel(getActivity(), 30f)); // right side offset
        swipelistview.setAnimationTime(200); // animarion time
        swipelistview.setSwipeOpenOnLongPress(true); // en
        swipelistview.setDrawSelectorOnTop(true);

        swipelistview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_current_orders, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.current_orders_add_order :
              dialogAddNewOrder();
              return true;
           case R.id.current_orders_sort_priority:
               prioritySort();
               isSortedByLow=!isSortedByLow;
               return true;
          default:
              return super.onOptionsItemSelected(item);
       }
    }

    /**Метод создания Диалога добавления нового заказа.
     * */
    private void dialogAddNewOrder(){
        final Dialog newOrder = new Dialog(getActivity());
        newOrder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newOrder.setContentView(R.layout.dialog_add_order);
        //Инициализируем все поля диалового окна
        final EditText orderName=(EditText) newOrder.findViewById(R.id.aod_input_order_name);
        final EditText customerName=(EditText) newOrder.findViewById(R.id.aod_input_customer_name);
        ImageButton okButton=(ImageButton) newOrder.findViewById(R.id.aod_ok_button);
        final RadioGroup priorityGroup=(RadioGroup)newOrder.findViewById(R.id.aod_rg_priority);
        ImageButton cancelButton=(ImageButton) newOrder.findViewById(R.id.aod_cancel_button);
        ImageButton microphoneButton=(ImageButton) newOrder.findViewById(R.id.aod_microphone_button);
        final DatePicker endOrderDate=(DatePicker) newOrder.findViewById(R.id.aod_end_order_date);
        //Показываем окно
        newOrder.show();

        //Кнопка отмены и закрытия
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrder.cancel();
            }
        });

        //Кнопка микофона для ввода голосом
        microphoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en_US");
                if(orderName.isFocused() || customerName.isFocused()){
                    try{
                        startActivityForResult(intent, RESULT_SPEACH);

                        if(orderName.isFocused()){
                            orderName.setText("");
                            voiceResult=orderName;
                        }

                        if(customerName.isFocused()){
                            customerName.setText("");
                            voiceResult=customerName;
                        }
                    }catch(ActivityNotFoundException e){
                        StringHelper.getInstance().showError(getActivity(),getResources().getString(R.string.error_voice_not_supported));
                    }
                }else{
                    StringHelper.getInstance().showError(getActivity(),getResources().getString(R.string.voice_no_choose_input));
                }
            }
        });

        //кнопка подтверждения
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //получаем выбранный приоритет
                RadioButton priority=(RadioButton)newOrder.findViewById(priorityGroup.getCheckedRadioButtonId());
                Priority p=Priority.valueOf(priority.getContentDescription().toString());
                //Получаем имя заказа и имя заказчика
                String oName=orderName.getText().toString();
                String oCustomer=customerName.getText().toString();
                //Получаем дату текущюю и дату окончания заказа
                long createTime=DateHelper.getInstance().getCurrentDateInMillis();
                long endDate=endOrderDate.getCalendarView().getDate();

                double zeroTotalPrice=0;


                if(oName.length()!=0 & oCustomer.length()!=0) {
                    String vOrderName=StringHelper.getInstance().varificationOfOrderName(oName, ListsLayerHelper.getInstance().getCurrentOrders());

                    order = new OrderListInfo(p, vOrderName, oCustomer, createTime, endDate, zeroTotalPrice);

                    currentOrders.add(order);
                    ListsLayerHelper.getInstance().setCurrentOrders(currentOrders);
                    //Temp add to dateBase
                    DbLayer.getInstance(getActivity()).insertInListsNames(order.getPriority(), order.getListName(), order.getClientName(), order.getCreateDate(), order.getEndDate(), order.getListTotalPrice());
                    //Проверяем если adapter существует то обновляем данные
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                    newOrder.dismiss();
                    //Передача данных(имени заказа) в другой фрагмент.
                   goToFragmentWithNameArgs(new EditOrderFragment(), oName, StringHelper.ORDER_NAME_TAG);



                }else{
                    StringHelper.getInstance().showHint(getActivity(), getString(R.string.no_listname_no_customername));
                }
            }
        });
    }


    /**Активность для голосового ввода.
     * Возвращает результат голосового ввода в выбраный EditText*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEACH:
                if(resultCode==getActivity().RESULT_OK && null!=data){
                    ArrayList<String> text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voiceResult.setText(text.get(0));
                }
                break;

            default:
                break;
        }
    }

    public ArrayList<OrderListInfo> getCurrentOrders() {
        return currentOrders;
    }

    public OrderListInfo getOrder() {
        return order;
    }



    private void prioritySort(){
        if(!currentOrders.isEmpty() && adapter!=null){
            if(!isSortedByLow) {
                Collections.sort(currentOrders, comparator);
            }else{
                Collections.reverse(currentOrders);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void goToFragmentWithNameArgs(Fragment fragmentScreen, String name, String tag){
        this.fragmentScreen = fragmentScreen;
        //Передача данных(имени заказа) в другой фрагмент.
        args=new Bundle();
        args.putString(tag,name);
        fragmentScreen.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScreen).commit();
    }

}
