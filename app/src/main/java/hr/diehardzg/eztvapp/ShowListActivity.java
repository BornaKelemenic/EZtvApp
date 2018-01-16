package hr.diehardzg.eztvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import hr.diehardzg.eztvapp.adapter.ShowListAdapter;
import hr.diehardzg.eztvapp.serija.Serija;

public class ShowListActivity extends Activity
{
    private static final String TAG = "ShowListActivity";

    ListView listView;
    EditText filter;
    ProgressBar progressBar;
    ShowListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        listView = (ListView) findViewById(R.id.listView);
        filter = (EditText) findViewById(R.id.filterView);
        progressBar = (ProgressBar) findViewById(R.id.progresBAR);

        GetHTMLdata getData = new GetHTMLdata();
        getData.execute("https://eztv.ag/showlist/");
    }


    private class GetHTMLdata extends AsyncTask<String, Void, ArrayList<Serija>>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(final ArrayList<Serija> serije)
        {
            super.onPostExecute(serije);
            Log.d(TAG, "onPostExecute");
            if(serije == null)
            {
                Toast.makeText(ShowListActivity.this, "EZTV not responding, please try again", Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.GONE);
            }
            else
            {
                adapter = new ShowListAdapter(ShowListActivity.this, R.layout.adapter_serija_layout, serije);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Log.d(TAG, "onItemClick: " + adapter.getFilteredData().get(position).toString());

                        Intent intent = new Intent(ShowListActivity.this, SerijaDetailsActivity.class);
                        intent.putExtra("serijaLink", adapter.getFilteredData().get(position).getLink());
                        startActivity(intent);
                    }
                });
                progressBar.setVisibility(View.GONE);

                filter.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        //Log.d(TAG, "onTextChanged: CALLED");
                        (ShowListActivity.this).adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }

        }

        @Override
        protected ArrayList<Serija> doInBackground(String... params)
        {
            Log.d(TAG, "doInBackground");
            return getHTMLdata(params[0]);
        }
    }

    private ArrayList<Serija> getHTMLdata(String url)
    {
        ArrayList<Serija> listaSerija = new ArrayList<>();
        try
        {
            Document doc = Jsoup.connect(url).get();

            Elements tablice = doc.select("table tbody");

            for(int i = 2; i < tablice.size(); i++)
            {
                Element table = tablice.get(i);
                Elements rows = table.select("tr");

                for(int j = 3; j < rows.size(); j++)
                {
                    Element row = rows.get(j);
                    Serija tmp = new Serija();
                    Elements tds = row.select("td.forum_thread_post");

                    tmp.setName(tds.get(0).text());
                    tmp.setLink(tds.get(0).select("a").attr("href"));
                    tmp.setStatus(tds.get(1).text());
                    tmp.setRating(tds.get(2).text());

                    listaSerija.add(tmp);
                }
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, "getHTMLdata: " + e.getMessage());
            return null;
        }
        return listaSerija;
    }
}
