package hr.diehardzg.eztvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import hr.diehardzg.eztvapp.torrent.Torrent;
import hr.diehardzg.eztvapp.adapter.TorrentAdapter;

public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";

    ListView listView;
    GetHTMLdata getData;
    EditText search;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        search = (EditText) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.progresBAR);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event
                        .getAction() == KeyEvent.ACTION_DOWN)))
                {
                    String imeSerije = search.getText().toString().replaceAll("\\s+", "-");
                    Log.d(TAG, "onEditorAction: " + imeSerije);

                    getData = new GetHTMLdata();
                    getData.execute(imeSerije);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_show_list:
                startActivity(new Intent(this, ShowListActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetHTMLdata extends AsyncTask<String, Void, ArrayList<Torrent>>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final ArrayList<Torrent> torrents)
        {
            super.onPostExecute(torrents);
            Log.d(TAG, "onPostExecute");
            if(torrents == null)
            {
                Toast.makeText(MainActivity.this, "EZTV not responding, please try again", Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.GONE);
            }
            else
            {
                listView.setAdapter(new TorrentAdapter(MainActivity.this, R.layout.adapter_view_layout, torrents));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Log.d(TAG, "onItemClick: " + torrents.get(position).getName());

                        Intent intent = new Intent(MainActivity.this, TorrentDetailsActivity.class);
                        intent.putExtra("torrent", torrents.get(position));
                        startActivity(intent);
                    }
                });
                progressBar.setVisibility(View.GONE);
            }

        }

        @Override
        protected ArrayList<Torrent> doInBackground(String... params)
        {
            Log.d(TAG, "doInBackground");
            return getHTMLdata(params[0]);
        }
    }


    public ArrayList<Torrent> getHTMLdata(String serija)
    {
        ArrayList<Torrent> listaTorrenta = new ArrayList<>();
        try
        {
            Document doc = Jsoup.connect("https://eztv.ag/search/" + serija).get();

            for(Element table : doc.select("tbody"))
            {
                for(Element row : table.select("tr.forum_header_border"))
                {
                    Torrent tmp = new Torrent();
                    Elements tds = row.select("td");

                    tmp.setName(tds.get(1).text());
                    tmp.setMagnet(tds.get(2).select("a:first-child").attr("href"));
                    tmp.setTorrent(tds.get(2).select("a:last-child").attr("href"));
                    tmp.setSize(tds.get(3).text());
                    tmp.setRelesed(tds.get(4).text());
                    tmp.setSeederi(tds.get(5).text());

                    listaTorrenta.add(tmp);
                }
            }
/*
            for(Torrent torrent : listaTorrenta)
            {
                Log.d(TAG, "getHTMLdata: " + torrent.toString());
                Log.d(TAG, "getHTMLdata: --------------------------------");
            }*/
        }
        catch (IOException e)
        {
            Log.e(TAG, "getHTMLdata: " + e.getMessage());
            return null;
        }
        return listaTorrenta;
    }
}
