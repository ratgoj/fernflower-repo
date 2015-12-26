package com.fernflower.orderbook.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.entities.CatalogItem;
import com.fernflower.orderbook.settings.AppSettings;


/**
 * Created by SONY on 22.09.2015.
 */
public class DialogHelper {

    public static DialogHelper instance;


    private DialogHelper(){

    }

    public static DialogHelper getInstance() {
        if( instance==null ){
            instance = new DialogHelper();
        }
        return instance;
    }

    public boolean checkEditOnText(EditText... editTexts){
        boolean isAllContentText=false;
        if(editTexts==null){
            isAllContentText=false;
        }else{
            for(EditText current : editTexts){
                if(current.getText().toString().isEmpty()){
                    isAllContentText=false;
                    break;
                }else{
                    isAllContentText=true;
                }
            }
        }
        return isAllContentText;
    }

    public void setTextInChosen(EditText currentEdit, String text){
        if(text.isEmpty() || text==null){
            text="Sorry text is null";
        }
        if(currentEdit.getText().toString().isEmpty()){
            currentEdit.setText(text);
        }else{
            currentEdit.getText().clear();
            currentEdit.setText(text);
        }
    }

    public int returnItemCode(EditText code){
        int res=0;
        if(AppSettings.getInstance().isItemAutocode()){
            res=ListsLayerHelper.getInstance().getCatalogItems().size()+1;
            //code.setEnabled(true);
            code.setText(String.valueOf(res));
            //code.setEnabled(false);
        }else{
            if(!code.getText().toString().isEmpty()){
                res=Integer.parseInt(code.getText().toString());
            }
        }
        return res;
    }

}
