package com.example.tatha.project5fedcash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ResultActivity extends Activity implements ResultListFragment.ListSelectionListener{
    static ArrayList<String> left =new ArrayList<String>();
    static ArrayList<String> right =new ArrayList<String>();
private ResultDetailFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        left.add(b.getString("input"));
        right.add(b.getString("out"));

        mDetailsFragment = (ResultDetailFragment) getFragmentManager().findFragmentById(R.id.fragment2);

}


    @Override
    public void onListSelection(int index) {
        if (mDetailsFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index
            mDetailsFragment.showQuoteAtIndex(index);
        }
    }
}
