package tomerbu.edu.contextmenusdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.tvContextShow)
    TextView tvContextShow;

    @Bind(R.id.tvPopUpListShow)
    TextView tvPopUpListShow;

    @Bind(R.id.tvPopUpMenuShow)
    TextView tvPopUpShow;

    private PopupMenu popupMenu;
    private ListPopupWindow listPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        registerForContextMenu(tvContextShow);

        setUpPopMenu();

        setUpListPopupWindow();
    }

    private void setUpListPopupWindow() {
        String[] items = {"One", "Two", "Three", "Four"};
        listPopupWindow = new ListPopupWindow(getApplicationContext());
        //A custom adapter that extends BaseAdapter (Like ListView)
        PopUpListAdapter adapter = new PopUpListAdapter(getApplicationContext(), Arrays.asList(items));
        listPopupWindow.setAdapter(adapter);

        listPopupWindow.setOnItemClickListener(adapter);

        listPopupWindow.setHeight(180 * 3);
        listPopupWindow.setAnchorView(tvPopUpListShow);

        //toggles the click to show/hide the listPopUpWindow
        listPopupWindow.setModal(true);
    }

    private void setUpPopMenu() {
        popupMenu = new PopupMenu(MainActivity.this, tvPopUpShow);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.pop, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.one) {
                    Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Action 1");
        menu.add(0, v.getId(), 0, "Action 2");
        menu.add(0, v.getId(), 0, "Action 3");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Action 1") {
            Toast.makeText(this, "Action 1 invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Action 2") {
            Toast.makeText(this, "Action 2 invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Action 3") {
            Toast.makeText(this, "Action 3 invoked", Toast.LENGTH_SHORT).show();
            new AsyncTask<String, Integer, String>() {
                @Override
                protected String doInBackground(String... params) {
                    String url = "https://yapi.herokuapp.com/api/upload";

                    try {
                        File file = new File(getFilesDir(), "1.txt");
                        FileWriter writer = new FileWriter(file);
                        writer.append("Hello ,Server");
                        writer.flush();writer.close();

                        MultipartUtility multipart = new MultipartUtility(url, "UTF-8");

                        multipart.addHeaderField("User-Agent", "Java");
                        multipart.addHeaderField("Accept-Language", "en-US,en;q=0.5");

                        multipart.addFormField("Image", "My Cool Photo");
                        multipart.addFormField("secret", "Hash, Hash");
                        multipart.addFormField("user", "TomerBu");

                        multipart.addFilePart("fileUpload", file);

                        String response = multipart.finish();
                        return response;
                    } catch (IOException e) {
                        return e.getMessage();
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }.execute("");
        } else {
            return false;
        }
        return true;
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

    @OnClick(R.id.tvPopUpMenuShow)
    public void onPopUpMenuShow() {
        popupMenu.show();
    }

    @OnClick(R.id.tvPopUpListShow)
    public void onPopListWindowShow() {
        listPopupWindow.show();
    }
}
