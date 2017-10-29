package com.alin.androidcalculator;

/**
 * Created by Alin on 10/29/2017.
 */

public interface CalculatorContract {

    //our view handles these methods
    interface PublishToView{
        void showResult(String result);
        void showToastMessage(String message);
    }

    //passes click events from our View(DisplayFragment) to the presenter
    interface ForwardDisplayInteractionToPresenter{
        void onDeleteShortClick();
        void onDeleteLongClick();
    }

    //passes click events from our View(InputFragment) to the presenter
    interface ForwardInputInteractionToPresenter{
        void onNumberClick(int number);
        void onDecimalClick();
        void onEvaluateClick();
        void onOperatorClick(String operator);
    }

}
