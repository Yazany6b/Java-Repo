package y6bdevelopment.stemmer;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Hashtable;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    EditText txt1;
    EditText txt2;
    Button btn;

    final PorterStemmer stemmer = new PorterStemmer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (EditText)findViewById(R.id.edit1);
        txt2 = (EditText)findViewById(R.id.edit2);
        btn = (Button)findViewById(R.id.button1);

        txt1.setText("The network engineer informed the administrator that the connection is down");

        btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(txt1.getText() == null) return;

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt1.getWindowToken(), 0);

        String str = txt1.getText().toString();

        String [] splits = str.toString().split(" ");

        Hashtable<String,String> result = new Hashtable<>();

        for(String st : splits){
            if(!result.containsKey(st.trim())){
                result.put(st,stemmer.stem(st.trim()));
            }
        }

        for (String key : result.keySet()){
            str = str.replaceAll(key,result.get(key));
        }

        txt2.setText(str);
    }
}
