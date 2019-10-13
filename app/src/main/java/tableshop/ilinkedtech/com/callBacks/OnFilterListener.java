package tableshop.ilinkedtech.com.callBacks;


import tableshop.ilinkedtech.com.beans.reques.ListRequestBean;

public interface OnFilterListener{
         void doCanCelAction();
         void doCommitAction(ListRequestBean requestBean);
    }