package com.sethy;

public interface Table {
    final static SqlConnection connection = SqlConnection.getInstance();


    //Always override this.
    public boolean submit();
}
